package np.common.exceptions;

public class ThreadException extends RuntimeException {
	public ThreadException(Thread caller) {
		super("An Exception Has Occurred In Thread '"+caller.getName()+"'");
	}
	
	public ThreadException() {
		this(Thread.currentThread());
	}
}
