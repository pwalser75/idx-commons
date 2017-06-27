package ch.frostnova.jee.testbase;

import ch.frostnova.jee.testbase.ejb.TestStatelessEJB;
import ch.frostnova.jee.testbase.ejb.TestUnavailableEJB;
import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import javax.inject.Inject;
import java.time.LocalDate;

/**
 * Tests CDI/EJB functionality
 *
 * @author pwalser
 * @since 28.06.2016.
 */
@RunWith(CdiTestRunner.class)
public class CDITest {

    @Inject
    private TestStatelessEJB testEJB;

    @Inject
    private TestUnavailableEJB unavailableEJB;

    @Test
    public void testDirectEJBCall() {
        Assert.assertEquals(LocalDate.now(), testEJB.getDate());
    }

    @Test
    public void testIndirectEJBCall() {
        Assert.assertEquals(Double.valueOf(Math.PI), testEJB.getValue());
    }

    @Test
    public void testMockedUnavailableEJB() {

        Mockito.when(unavailableEJB.getMessage()).thenReturn("Aloha");
        Assert.assertEquals("Aloha", unavailableEJB.getMessage());
    }
}
