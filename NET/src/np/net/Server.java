package np.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import np.common.exceptions.NetworkException;
import np.common.exceptions.ServerException;
import np.io.core.IO;
/**
 * @author george
 */
public abstract class Server {
	int id = 1000;
	private boolean isRunning = true;
	private Map<String, StringConnection> clientList = new HashMap<>();
	
	protected abstract boolean OnClientConnect(StringConnection sock);
	protected abstract void OnClientDisconnect(StringConnection sock);
	
	protected abstract void HandleMessage(StringConnection client, String message);
	
	ServerSocket server;
	public Server(int port) {
		try {
			server = new ServerSocket(port);
		} catch (IOException e) {
			ServerException.ThrowPortException(port);
		}
	}
	
	public void Start() {
		AsyncWaitForConnections();
		
		while(isRunning) {}
	}
	
	private void AsyncPollForMessages(StringConnection connection) {
		Thread t = new Thread(() -> {
			while(connection.IsOpen()) {
				if(connection.HasMessages()) {
					String msg = connection.GetNextMessageOrNull();
					if(msg != null) {
						HandleMessage(connection, msg);
					} else {
						connection.Close();
						RemoveClient(connection);
					}
				}
			}
		});
		t.setDaemon(true);
		t.start();
	}
	
	public String GetSocketID(Socket socket) {
		return ""+socket.getInetAddress().hashCode()+":"+socket.getPort();
	}
	
	public void SendMessage(StringConnection sock, String msg) {
		sock.SendMessage(msg);
	}
	
	public void Broadcast(String message, StringConnection sock) {
		List<StringConnection> ignored = Arrays.asList(sock);
		for(Entry<String, StringConnection> client : clientList.entrySet()) {
			if(ignored.contains(client.getValue())) continue;
			client.getValue().SendMessage(message);
		} 
	}
	
	
	private  void AsyncWaitForConnections() {
		Thread t = new Thread(() -> {
			for(;;) {
				try {
					Socket sock = server.accept();
					StringConnection client = new StringConnection(sock);
					if (OnClientConnect(client)) {
						
						if(clientList.containsKey(""+sock.getInetAddress().hashCode()+":"+sock.getPort())) {
							throw new IllegalStateException();
						}
						
						clientList.put(""+sock.getInetAddress().hashCode()+":"+sock.getPort(), client);
						System.out.println("[Server/Info] There Are Currently "+clientList.size()+" clients connected");
						System.out.println("Client #"+sock.getInetAddress().hashCode()+":"+sock.getPort()+" Is Connected On Port "+sock.getPort());
						client.Run();
						AsyncPollForMessages(client);
					}
				} catch (IOException e) {
					throw new NetworkException();
				} catch (IllegalStateException isx) {
					System.err.println("[ERROR] A Client That is already connected is Attempting To Connect, Aborting!");
					System.exit(-1);
				}
				
			}
		});
		t.setDaemon(true);
		t.start();
		
		
	}
	public void RemoveClient(StringConnection sock) {
		String id = sock.GetID();
		clientList.remove(id);
		
		System.out.println("[Server/Info] Removed Client "+id);
		System.out.println("[Server/Info] There Are Currently "+clientList.size()+" clients connected");
	}
}