package Client;

import Server.User;

public class ClientController {
	private Client client;

	public ClientController(Client client) {
		this.client = client;
	}
	
	public void disconnectClient() {
		client.disconnect();
	}

	public static void main(String[] args) {
		Client client1 = new Client("127.0.0.1", 4447, new User("Balle"));
//		 Client client2 = new Client("127.0.0.1",4447, new User("Nalle"));
		// Client client3 = new Client("127.0.0.1",4447);
		// ClientController controller = new ClientController(client1);
	}
}
