package np.tests;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import np.io.core.NET;
import np.io.core.NET.Server;

public class NetworkTester {
	static Server server;
	public static void main(String[] args) {
		server = new BasicServer(5000);
		if(server == null) fail();
		server.Start();
	}
	
	public static class BasicServer extends Server {

		public BasicServer(int port) {
			super(port);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected boolean OnClientConnect(Socket sock) {
			System.out.println("Client Has Connected");
			SendMessage(sock, "Hello...");
			return true;
		}

		@Override
		protected void OnClientDisconnect(Socket sock) {
			System.out.println("Client Has Disconnected");
			try {
			sock.close();
			} catch(IOException ioex) {}
		}

		@Override
		protected void HandleMessage(Socket socket, String message) {
			System.out.println(GetSocketID(socket)+" - "+message);
			Broadcast(message, socket);
		}
		
	}

}
