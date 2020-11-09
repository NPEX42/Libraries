package np.common.exceptions;
/**
 * @author george
 */
public class ServerException extends NetworkException {
	private static final long serialVersionUID = 1L;
	public ServerException(String message) {
		super(message);
	}
	
	public ServerException() {
		super("A Server Exception Has Occurred");
	}
	
	public static void ThrowPortException(int port) {
		throw new ServerException("Unable To Open Server On Port "+port+"!");
	}
}
