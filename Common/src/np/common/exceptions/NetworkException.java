package np.common.exceptions;
/**
 * @author george
 */
public class NetworkException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public NetworkException(String message) {
		super(message);
	}
	public NetworkException() {
		this("A Network Exception Has Occured...");
	}
	
	public static void ThrowSocketCreationException(String host, int port) {
		throw new NetworkException("Unable to connect To "+host+":"+port);
	}
	
	public static void ThrowSocketCloseException() {
		throw new NetworkException("Unable to Close The Socket");
	}
}
