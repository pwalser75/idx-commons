package ch.frostnova.persistence.api.exception;

/**
 * Exception thrown when persisting an entity would violate any constraints.
 *
 * @author pwalser
 * @since 27.06.2017
 */
public class ConstraintViolationException extends PersistenceException {

    /**
     * Constructor
     *
     * @param message message with details on the problem
     */
    public ConstraintViolationException(String message) {
        super(message);
    }
}
