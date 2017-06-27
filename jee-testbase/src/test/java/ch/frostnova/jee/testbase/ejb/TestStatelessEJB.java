package ch.frostnova.jee.testbase.ejb;

import javax.ejb.Stateless;
import java.time.LocalDate;

/**
 * @author pwalser
 * @since 28.06.2016.
 */
@Stateless
public class TestStatelessEJB {

    public LocalDate getDate() {
        return LocalDate.now();
    }

    public Double getValue() {
        return Math.PI;
    }

}
