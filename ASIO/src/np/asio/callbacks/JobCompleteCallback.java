package np.asio.callbacks;

public interface JobCompleteCallback<T> {
	public void OnComplete(T item);
}
