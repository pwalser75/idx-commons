package ch.frostnova.jee.testbase.ejb;

import javax.ejb.Stateless;

/**
 * @author pwalser
 * @since 28.06.2016.
 */
@Stateless
public interface TestUnavailableEJB {

    String getMessage();
}
