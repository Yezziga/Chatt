package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import Server.User;

public class Client {
	private int serverPort;
	private ClientController controller;
	private ObjectInputStream fromServer;
	private ObjectOutputStream toServer;
	private Socket socket;

	public Client(String ip, int serverPort) {
		this.serverPort = serverPort;
		try {
			socket = new Socket(ip, serverPort);
			toServer = new ObjectOutputStream(socket.getOutputStream());
			fromServer = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {

			System.out.println("FETT FEL");
			e.printStackTrace();
		}

		controller = new ClientController(this);
		new Listener().start();

	}

	public void connectUser(String username, ImageIcon icon) {
		try {
			User user = new User(username, icon);
			toServer.writeObject(user);
			toServer.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void disconnect() {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sends a Message-object to the server.
	 * 
	 * @param msg
	 *            the message
	 */
	public void sendMessage(Message msg) {
		try {
			toServer.writeObject(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void readMessage(Message msg) {
		// read & display message
	}

	private class Listener extends Thread {
		public void run() {
			ArrayList<String> messageReceivers = new ArrayList<>();
			messageReceivers.add("Kalle");
			messageReceivers.add("Balle");
			messageReceivers.add("Nalle");
			// Message msg1 = new Message(user, messageReceivers, "meddelandet");

			/**
			 * h�r b�r klienten lyssna efter uppdateringar av listor & inkommande
			 * meddelanden fr�n server
			 */
			while (true) {
				// sendMessage(msg1);
				try {
					Object obj = fromServer.readObject();

					if (obj instanceof ArrayList<?>) {
						@SuppressWarnings("unchecked")
						ArrayList<String> arr = (ArrayList<String>) obj;
						controller.updateOnlineList(arr);
					} else if (obj instanceof Message) {
						Message msg = (Message) obj;
						readMessage(msg);
					}
					
					
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				

				// disconnectClient();
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public static void main(String[] args) {
		Client client1 = new Client("127.0.0.1", 4447);

		// Client client2 = new Client("127.0.0.1",4447, new User("Nalle"));
		// Client client3 = new Client("127.0.0.1",4447);
	}

}
