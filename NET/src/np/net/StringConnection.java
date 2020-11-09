package np.net;

import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;

import np.common.atomic.AtomicQueue;
import np.common.exceptions.NetworkException;
import np.io.core.IO;
/**
 * @author george
 */
public class StringConnection {
	private Socket socket;
	
	private AtomicQueue<String> messages = new AtomicQueue<>();
	
	protected StringConnection(String host, int port) {
			this(IO.OpenNetworkSocket(host, port));
	}
	
	protected StringConnection(Socket socket) {
		this.socket = socket;
	}
	
	public void Run() {
		AsyncPollMessages();
	}
	
	public void SendMessage(String message) {
		NET.SendStringOverSocket(message, socket);
	}
	
	public String GetNextMessageOrNull() {
		if(messages == null) return null;
		
		if(HasMessages()) {
			return messages.dequeue();
		} else {
			return null;
		}
	}
	
	private void AsyncPollMessages() {
		Thread t = new Thread(() -> {
			while(IsOpen()) {
				String message = IO.ReadStringFromSocket(socket);
				if(message == null) Close();
				messages.enqueue(message);
			}
		});
		t.setDaemon(true);
		t.start();
	}
	
	public boolean HasMessages() {
		return !messages.isEmpty();
	}
	
	public void Close() {
		try {
			socket.close();
		} catch(IOException ioex) {
			NetworkException.ThrowSocketCloseException();
		}
	}

	public boolean IsOpen() {
		return socket.isConnected();
	}
	
	public String GetID() {
		return ""+socket.getInetAddress().hashCode()+":"+socket.getPort();
	}
	
	
	
	
}
