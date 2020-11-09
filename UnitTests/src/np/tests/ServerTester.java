package np.tests;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;

import np.io.core.*;
import np.net.*;

public class ServerTester {
	static Server server;
	static HashMap<StringConnection, String> aliases = new HashMap<>();
	
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
		protected boolean OnClientConnect(StringConnection sock) {
			System.out.println("Client Has Connected");
			SendMessage(sock, "==== Terminal Chat V1.0 ====");
			return true;
		}

		@Override
		protected void OnClientDisconnect(StringConnection sock) {
			System.out.println("Client Has Disconnected");
			Broadcast(GetAlias(sock)+" has Left The Chat...", sock);
			sock.Close();
		}
		
		@Override
		protected void HandleMessage(StringConnection socket, String message) {
			String[] parts = message.split("[\\s]+");
			switch(parts[0]) {
			case "ALIAS": aliases.put(socket, parts[1]); break;
			case "ECHO": SendMessage(socket, "ECHO " + parts[1]); break; 
			default: Broadcast(aliases.getOrDefault(socket, socket.GetID())+": "+message, socket); break;
			}
			System.out.println(aliases.getOrDefault(socket, socket.GetID())+" - "+message);
		}
		
		protected String GetAlias(StringConnection socket) {
			return aliases.getOrDefault(socket, socket.GetID());
		}
		
	}

}
