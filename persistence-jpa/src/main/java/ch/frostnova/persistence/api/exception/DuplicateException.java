package ch.frostnova.persistence.api.exception;

/**
 * Exception thrown when persisting an entity would violate a 'uniqute' constraint.
 *
 * @author pwalser
 * @since 27.06.2017
 */
public class DuplicateException extends PersistenceException {

    /**
     * Constructor
     *
     * @param message message with details on the problem
     */
    public DuplicateException(String message) {
        super(message);
    }
}
