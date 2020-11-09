package np.common.exceptions;
/**
 * @author george
 */
public class ClientException extends NetworkException {
	private static final long serialVersionUID = 1L;
	public ClientException(String message) {
		super(message);
	}
	public ClientException() {
		this("A Client Exception Has Occured...");
	}
	
	public static void ThrowConnectionError(String host, int port) { 
		throw new ClientException("Unable to connect To "+host+":"+port);
	}
	
}
