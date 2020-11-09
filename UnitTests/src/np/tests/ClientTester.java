package np.tests;

import np.net.Client;

public class ClientTester {
	
	public static void main(String[] args) {
		Client client = new BasicClient();
		client.Run();
	}
	
	public static class BasicClient extends Client {

		public BasicClient() {
			super("127.0.0.1", 5000);
		}

		@Override
		protected void HandleMessage(String message) {
			String[] parts = message.split("[\\s]+");
			switch(parts[0]) {
			case "ECHO": {
					long received = Long.parseLong(parts[1]);
					long now = System.currentTimeMillis();
					System.out.println("Ping: "+(now - received)+"ms");
					break;
				}
			default: System.out.println(message); break;
			}
		}

		@Override
		protected void OnConnect() {
			System.out.println("Connected...");
			SendMessage("ECHO "+System.currentTimeMillis());
		}
		
	}
}
