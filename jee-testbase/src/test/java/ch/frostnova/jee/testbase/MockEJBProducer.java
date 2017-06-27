package ch.frostnova.jee.testbase;

import ch.frostnova.jee.testbase.ejb.TestUnavailableEJB;
import org.mockito.Mockito;

import javax.enterprise.inject.Produces;

/**
 * Producer for EJB mocks.
 *
 * @author pwalser
 * @since 28.06.2016.
 */
public class MockEJBProducer {

    @Produces
    public TestUnavailableEJB createUnavailableEJB() {
        return Mockito.mock(TestUnavailableEJB.class);
    }
}
