package Server;

import java.awt.Image;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.Map.Entry;

import javax.swing.ImageIcon;

import Client.Message;

public class Server {
	private Clients cl = new Clients(); // inner class
	private ArrayList<Message> unsentMessages = new ArrayList<>();
	private TrafficLogger logger;
	private ServerSocket serverSocket;

	/**
	 * Creates the server in the requested port and instantiates a ServerSocket.
	 * Then it listens for a client to connect to this socket and starts a
	 * ClientHandler-thread.
	 * 
	 * @param serverPort
	 *            the port to connect to
	 */
	public Server(int serverPort) {
		logger = TrafficLogger.getInstance();
		logger.log("Logger started");

		try {
			serverSocket = new ServerSocket(serverPort);
			System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");

			while (true) {
				Socket clientSocket = serverSocket.accept();
				System.out.println("Just connected to " + clientSocket.getRemoteSocketAddress());
				ClientHandler ch = new ClientHandler(clientSocket);
				ch.start();

			}
		} catch (UnknownHostException ex) {
			ex.printStackTrace();
			try {
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("Server stopped");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds a Message-object to the list of unsent messages.
	 * 
	 * @param message
	 *            the Message-object to add to the list
	 */
	public void addMessageToUnsentList(Message message) {
		unsentMessages.add(message);
	}

	/**
	 * Forwards the Message-object to receivers who are online, and updates the
	 * Message-object's list of receivers. If there are still receivers left, the
	 * Message-object will be added to the list of unsent messages.
	 * 
	 * @param message
	 *            the message to send
	 */
	public void checkReceiversAndOnliners(Message message) {
		ArrayList<User> onlineUsers = cl.getAllOnlineUsers(); // list with online users
		ArrayList<User> listOfReceivers = message.getReceivers();
		ArrayList<User> tempList = new ArrayList<>(); // new list with offline receivers

		for (User receiverOnList : listOfReceivers) {
			boolean receiverFound = false;
			for (User onlineUser : onlineUsers) {

				if (receiverOnList.getName().equals(onlineUser.getName())) {
					receiverFound = true;
					logger.log(receiverOnList.getName() + " is online. Attempting to send message");
					// System.out.println(receiverOnList.getName() + " is online");

					sendMessageToOnlineUser(message, receiverOnList);
					break;
				}
			}
			if (receiverFound == false) {
				tempList.add(receiverOnList);
				logger.log(receiverOnList.getName() + " is not online. Storing message to send when online");
				// System.out.println(receiverOnList.getName() + " is not online");
			}
		}
		if (!tempList.isEmpty()) { // updates the list of receivers if there still are users who haven't come
									// online.
			message.setReceiver(tempList);
			unsentMessages.add(message);
		}
	}

	/**
	 * Sends a Message-object to the user with the specified name
	 * 
	 * @param msg
	 *            the message to send
	 * @param receiver
	 *            the user who is receiving
	 */
	public void sendMessageToOnlineUser(Message msg, User receiver) {
		// System.out.println("In server, sendMessageToOnlineUser: Message is " +
		// msg.getMessage());
		Message message = new Message(msg.getSender(), receiver, msg.getMessage());

		User user = cl.getUser(receiver.getName());
		cl.get(user).sendMessage(message);

	}

	/**
	 * Writes a list of all online users to all connected clients, excluding the
	 * current client's user itself.
	 */
	public void updateAllClients() {
		ArrayList<User> tempList = null;
		Iterator<Entry<User, ClientHandler>> it = cl.onlineUsers.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			ClientHandler ch = (ClientHandler) pair.getValue();
			try {
				tempList = cl.getAllOnlineUsers();
				tempList.remove(pair.getKey());
				ch.toClient.writeObject(tempList);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	/**
	 * Inner-class which handles the list of online users.
	 * 
	 * @author Jessica
	 *
	 */
	private class Clients {

		// HashMap containing a User-object and its corresponding ClientHandler.
		private HashMap<User, ClientHandler> onlineUsers = new HashMap<User, ClientHandler>();

		/**
		 * Associates the specified user with the specified ClientHandler-thread in this
		 * map. Sends pending messages to this user if there are any.
		 * 
		 * @param user
		 *            User-key with which the specified value is to be associated
		 * @param clientHandler
		 *            ClientHandler-value to be associated with the specified key
		 */
		public synchronized void put(User user, ClientHandler clientHandler) {

			onlineUsers.put(user, clientHandler);
			logger.log(user.getName() + " connected to the server");
			updateAllClients();
			// send messages to user if there are any unsent messages.
			for (Message message : unsentMessages) {
				for (User receiver : message.getReceivers()) {
					if (receiver.getName().equals(user.getName())) {
						clientHandler.sendMessage(message);
						break;
					}
				}
			}

		}

		/**
		 * Returns the clientHandler mapped with the specified user
		 * 
		 * @param user
		 * @return the clientHandler mapped with this user
		 */
		public synchronized ClientHandler get(User user) {
			return onlineUsers.get(user);
		}

		/**
		 * Returns the User-object which has this name
		 * 
		 * @param name
		 * @return the User-object with this name
		 */
		public synchronized User getUser(String name) {
			for (User user : onlineUsers.keySet()) {
				if (user.getName().matches(name)) {
					return user;
				}
			}
			return null;
		}

		/**
		 * Removes the user from the list of online users and updates all connected
		 * clients.
		 * 
		 * @param user
		 *            whose mapping is to be removed from the map
		 */
		public synchronized void remove(User user) {
			onlineUsers.remove(user);
			updateAllClients();
		}

		/**
		 * Adds all online users to a list and returns it.
		 * 
		 * @return an ArrayList of online users
		 */
		public synchronized ArrayList<User> getAllOnlineUsers() {
			ArrayList<User> listOnliners = new ArrayList<>();

			for (User user : onlineUsers.keySet()) {
				listOnliners.add(user);
				// System.out.print(user.getName() + " ");
			}

			return listOnliners;
		}
	}

	/**
	 * Thread which listens for and responds to a client's requests. This
	 * inner-class updates the connected clients.
	 * 
	 * @author Jessica
	 *
	 */
	private class ClientHandler extends Thread {
		private Socket clientSocket;
		private ObjectInputStream fromClient;
		private ObjectOutputStream toClient;
		private User user;

		/**
		 * Creates the client's streams which are used to connect and communicate with
		 * the server.
		 * 
		 * @param socket
		 *            the new socket provided by the sever
		 */
		public ClientHandler(Socket socket) {
			this.clientSocket = socket;

			try {
				fromClient = new ObjectInputStream(clientSocket.getInputStream());
				toClient = new ObjectOutputStream(clientSocket.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		/**
		 * Writes the Message-object to the client
		 * 
		 * @param msg
		 *            the Message-object to send
		 */
		public void sendMessage(Message msg) {

			try {
				toClient.writeObject(msg);
				toClient.flush();
				logger.log("message sent to " + user.getName());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void run() {

			try {

				user = (User) fromClient.readObject();
				if (user.getPicture() == null) {
					Image temp = new ImageIcon("files/MSN-icon.png").getImage().getScaledInstance(100, 100,
							Image.SCALE_SMOOTH);
					user.setPicture(new ImageIcon(temp));
				}

				cl.put(user, this);
				// Reads the users's contact list and writes it to the client.
				toClient.writeObject(ContactsReader.readContacts(user));
				toClient.flush();

				while (true) {
					Object obj = fromClient.readObject();
					try {
						if (obj instanceof Message) {
							// System.out.println("In Server: Object instance of message");
							Message msg = (Message) obj;
							System.out.println(msg.getMessage());
							Calendar calendar = Calendar.getInstance();
							Date date = calendar.getTime();
							msg.setDateSent(date);
							logger.log("Server received Message-object from " + user.getName());
							checkReceiversAndOnliners(msg);
						} else if (obj instanceof User) {
							User contactToAdd = (User) obj;
							ContactsReader.addContact(user, contactToAdd);
							logger.log(user.getName() + " added " + contactToAdd.getName() + " to contact list");
							toClient.writeObject(ContactsReader.readContacts(user));
							toClient.flush();

						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			} catch (Exception e1) {
				disconnectClient();
			}

		}

		/**
		 * Disconnects the client from the server and closes all connections to it.
		 */
		private void disconnectClient() {
			try {
				cl.remove(user);
				toClient.close();
				fromClient.close();
				clientSocket.close();
				logger.log(user.getName() + " disconnected from the server.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
