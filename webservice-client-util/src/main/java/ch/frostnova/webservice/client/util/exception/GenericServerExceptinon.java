package idx.ws.client.util.exception;

/**
 * Generic HTTP server exception (status and message)
 *
 * @author pwalser
 * @since 23.12.2016.
 */
public class GenericServerExceptinon extends BaseHTTPException {

    public GenericServerExceptinon(int status, String message) {
        super(status, message);
    }
}
