package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import Server.Contact;
import Server.User;

public class Client {
	private int serverPort;
	private ClientController controller;
	private ObjectInputStream fromServer;
	private ObjectOutputStream toServer;
	private Socket socket;
	private User user;

	/**
	 * Constructor which creates connections to the server
	 * 
	 * @param ip
	 * @param serverPort
	 *            the port to the server
	 */
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

	/**
	 * Creates a User-object with the given username and image, and forwards it to
	 * the server.
	 * 
	 * @param username
	 *            the given user name
	 * @param icon
	 *            the given image
	 */
	public void connectUser(User user) {
		try {
			toServer.writeObject(user);
			toServer.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Closes the connection to the server.
	 */
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
			toServer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void readMessage(Message msg) {
		// read & display message
	}

	private class Listener extends Thread {
		@SuppressWarnings("unchecked")
		public void run() {
			ArrayList<String> messageReceivers = new ArrayList<>(); // test purpose
			messageReceivers.add("Kalle");
			messageReceivers.add("Balle");
			messageReceivers.add("Nalle");
			// Message msg1 = new Message(user, messageReceivers, "meddelandet");

			/**
			 * Listens for input from the server
			 */
			while (true) {
				// sendMessage(msg1);
				try {
					Object obj = fromServer.readObject();

					if (obj instanceof ArrayList<?>) { // still in progress, does not know difference between
														// online-list and contacts-list! also not fully tested
						System.out.println(obj);
						if (!((ArrayList<?>) obj).isEmpty()) {
							if (((ArrayList<?>) obj).get(0) instanceof User) {
								ArrayList<User> arr = (ArrayList<User>) obj;
								controller.updateAllUsers(arr);
								System.out.println(obj);
							} else if (((ArrayList<?>) obj).get(0) instanceof Contact) {
								ArrayList<Contact> arr = (ArrayList<Contact>) obj;
								controller.updateContactList(arr);
							}
						} else if (((ArrayList<?>) obj).isEmpty()) {
							controller.clear();
						}
					}

					// @SuppressWarnings("unchecked")
					// ArrayList<String> arr = (ArrayList<String>) obj;
					// if (arr.get(arr.size()-1).equals("*")) {
					// arr.remove(arr.size()-1);
					// for (String string : arr) {
					// System.out.println(string);
					// }
					//
					//// controller.updateOnlineList(arr);
					// }
					// } else if (obj instanceof Message) {
					// Message msg = (Message) obj;
					// readMessage(msg); // does not do anything yet
					// }

				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				try {
					Thread.sleep(5000); // not needed?
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
