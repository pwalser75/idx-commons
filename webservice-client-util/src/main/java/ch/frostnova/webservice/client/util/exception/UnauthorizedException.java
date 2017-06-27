package idx.ws.client.util.exception;

/**
 * Exeption for HTTP 401 - UNAUTHORIZED.
 *
 * @author pwalser
 * @since 23.12.2016.
 */
public class UnauthorizedException extends BaseHTTPException {

    public UnauthorizedException(String message) {
        super(401, message);
    }
}
