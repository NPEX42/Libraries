package np.io.core;

import java.util.*;
import java.util.Map.Entry;
import java.net.*;
import java.io.*;

public class NET {
	
	
	
	
	public static interface ClientConnectCallback {
		public boolean Call(Socket socket, InetAddress address, int port);
	}
	
	public static interface ClientDisconnectCallback {
		public void Call(Socket socket, InetAddress address, int port);
	}
	
	public static abstract class Server {
		int id = 1000;
		private boolean isRunning = true;
		private Map<String, Socket> clientList = new HashMap<>();
		private Map<Socket, LinkedList<String>> clientMessages = new HashMap<>();
		
		protected abstract boolean OnClientConnect(Socket sock);
		protected abstract void OnClientDisconnect(Socket sock);
		
		protected abstract void HandleMessage(Socket sock, String message);
		
		ServerSocket server;
		public Server(int port) {
			try {
				server = new ServerSocket(port);
			} catch (IOException e) {
				throw new ServerException();
			}
		}
		
		public void Start() {
			AsyncWaitForConnections();
			
			while(isRunning) {}
		}
		
		private void AsyncPollForMessages(Socket sock) {
			Thread t = new Thread(() -> {
				clientMessages.put(sock, new LinkedList<>());
				for(;;) {
					String msg = IO.ReadStringFromSocket(sock);
					if(msg == null) {
						OnClientDisconnect(sock);
						RemoveClient(sock);
						break;
					}
					clientMessages.get(sock).addLast(msg);
				}
			});
			t.setDaemon(true);
			t.start();
		}
		
		public String GetSocketID(Socket socket) {
			return ""+socket.getInetAddress().hashCode()+":"+socket.getPort();
		}
		
		public void SendMessage(Socket sock, String msg) {
			IO.WriteStringToSocket(sock, msg);
		}
		
		
		private void AsyncHandleMessages(Socket sock) {
			Thread t = new Thread(() -> {
				for(;;) {
					String message = GetNextMessageOrNull(sock);
					if(message != null) {
						HandleMessage(sock, message);
					}
				}
			});
			t.setDaemon(true);
			t.start();
		}
		
		public synchronized String GetNextMessageOrNull(Socket socket) {
			LinkedList<String> messages = clientMessages.get(socket);
			
			if(messages == null) return null;
			
			if(messages.size() > 0) {
				return messages.pollFirst();
			} else {
				return null;
			}
		}
		
		public void Broadcast(String message, Socket... ignoredClients) {
			List<Socket> ignored = Arrays.asList(ignoredClients);
			for(Entry<String, Socket> client : clientList.entrySet()) {
				if(ignored.contains(client.getValue())) continue;
				SendMessage(client.getValue(), message);
			} 
		}
		
		
		private  void AsyncWaitForConnections() {
			Thread t = new Thread(() -> {
				for(;;) {
					try {
						Socket sock = server.accept();
						if (OnClientConnect(sock)) {
							
							if(clientList.containsKey(""+sock.getInetAddress().hashCode()+":"+sock.getPort())) {
								throw new IllegalStateException();
							}
							
							clientList.put(""+sock.getInetAddress().hashCode()+":"+sock.getPort(), sock);
							System.out.println("[Server/Info] There Are Currently "+clientList.size()+" clients connected");
							System.out.println("Client #"+sock.getInetAddress().hashCode()+":"+sock.getPort()+" Is Connected On Port "+sock.getPort());
							AsyncPollForMessages(sock);
							AsyncHandleMessages(sock);
						}
					} catch (IOException e) {
						e.printStackTrace();
					} catch (IllegalStateException isx) {
						System.err.println("[ERROR] A Client That is already connected is Attempting To Connect, Aborting!");
						System.exit(-1);
					}
					
				}
			});
			t.setDaemon(true);
			t.start();
			
			
		}
		public void RemoveClient(Socket sock) {
			String id = GetSocketID(sock);
			clientMessages.remove(sock);
			clientList.remove(id);
			
			System.out.println("[Server/Info] Removed Client "+id);
			System.out.println("[Server/Info] There Are Currently "+clientList.size()+" clients connected");
		}
	}
}
