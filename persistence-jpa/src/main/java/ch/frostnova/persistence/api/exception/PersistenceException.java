package ch.frostnova.persistence.api.exception;

/**
 * Base exception for any persistence related exceptions.
 *
 * @author pwalser
 * @since 27.06.2017
 */
public class PersistenceException extends Exception {

    /**
     * Constructor
     *
     * @param message message with details on the problem
     */
    public PersistenceException(String message) {
        super(message);
    }
}
