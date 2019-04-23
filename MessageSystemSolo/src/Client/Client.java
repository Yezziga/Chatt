package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

import Server.Contact;
import Server.User;

public class Client {
	private int serverPort;
	private ClientController controller;
	private ObjectInputStream fromServer;
	private ObjectOutputStream toServer;
	private Socket socket;

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
			e.printStackTrace();
		}
		controller = new ClientController(this);
		new Listener().start();
	}

	/**
	 * Writes the User-object to the server for connection.
	 * 
	 * @param user
	 *            the user to connect to the server.
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
			toServer.close();
			fromServer.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Writes a Message-object to the server.
	 * 
	 * @param msg
	 *            the message to send.
	 */
	public void sendMessage(Message msg) {
		try {
			toServer.writeObject(msg);
			toServer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Forwards the message from the server to the controller
	 * 
	 * @param msg
	 *            the Message-object from the server
	 */
	public void readMessage(Message msg) {
		msg.setDateReceived(new Date());
		controller.addMessage(msg);
	}

	/**
	 * Writes the user to add as contact to the server
	 * 
	 * @param user
	 *            the user to add as contact
	 */
	public void addContact(User user) {
		try {
			toServer.writeObject(user);
			toServer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private class Listener extends Thread {
		@SuppressWarnings("unchecked")
		public void run() {
			while (true) {
				try {
					Object obj = fromServer.readObject();

					/*
					 * Checks if the data is an ArrayList. Determines if the incoming list is a list
					 * of online users or contact list.
					 */
					if (obj instanceof ArrayList<?>) {
						System.out.println(obj);
						if (!((ArrayList<?>) obj).isEmpty()) {
							if (((ArrayList<?>) obj).get(0) instanceof Contact) {
								ArrayList<Contact> arr = (ArrayList<Contact>) obj;
								controller.updateContactList(arr);
							} else if (((ArrayList<?>) obj).get(0) instanceof User) {
								ArrayList<User> arr = (ArrayList<User>) obj;
								controller.updateOnlineUsers(arr);
								System.out.println(obj);
							}
						} else if (((ArrayList<?>) obj).isEmpty()) {
							controller.clear();
						}
					}
					if (obj instanceof Message) {
						System.out.println("Object instance of message");
						readMessage((Message) obj);
					}

				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}
		}

	}

	public static void main(String[] args) {
		Client client1 = new Client("127.0.0.1", 4447);
	}

}
