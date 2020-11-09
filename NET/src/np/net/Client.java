package np.net;

import java.io.IOException;
import java.net.Socket;
/**
 * @author george
 */
public abstract class Client {
	private StringConnection connection;
	
	public Client(String host, int port) {
			connection = NET.OpenConnection(host, port);
			OnConnect();
			Run();
	}
	
	public void Run() {
		connection.Run();
		AsyncPollMessages();
		while(connection.IsOpen()) {}
	}
	
	protected abstract void HandleMessage(String message);
	protected abstract void OnConnect();
	
	public void SendMessage(String message) {
		connection.SendMessage(message);
	}
	
	private void AsyncPollMessages() {
		Thread t = new Thread(() -> {
			for(;;) {
				String message = connection.GetNextMessageOrNull();
				if (message != null) {
					HandleMessage(message);
				}
				
			}
		});
		t.setDaemon(true);
		t.start();
	}
	
	public void RequestClose() {
		connection.Close();
	}
}
