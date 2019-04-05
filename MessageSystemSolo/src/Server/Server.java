package Server;

import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.ImageIcon;

import Client.Message;

public class Server {
	private Clients cl = new Clients();
	private ArrayList<Message> unsentMessages = new ArrayList<>();
	private TrafficLogger logger;

	/**
	 * Creates the server in the requested port and instantiates a ServerSocket.
	 * Then it listens for a client to connect to this socket and starts a
	 * ClientHandler-thread.
	 * 
	 * @param serverPort
	 *            the port to connect to
	 */
	public Server(int serverPort) {
		// logger = TrafficLogger.getInstance();
		// logger.saveToLog("Logger started");
		// System.out.println(logger.getLog());

		try {
			ServerSocket serverSocket = new ServerSocket(serverPort);
			System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");

			while (true) {
				Socket clientSocket = serverSocket.accept();
				System.out.println("Just connected to " + clientSocket.getRemoteSocketAddress());
				ClientHandler ch = new ClientHandler(clientSocket);
				ch.start();
				// System.out.println("Done awaiting new connections");

			}
		} catch (UnknownHostException ex) {
			ex.printStackTrace();
			System.out.println("Server stopped");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds a message-object to the list of unsent messages.
	 * 
	 * @param message
	 */
	public void addMessageToUnsentList(Message message) {
		unsentMessages.add(message);
	}

	/**
	 * Forwards the message-object to receivers who are online, and updates the list
	 * of receivers
	 * 
	 * @param message
	 *            the message to send
	 */
	public void checkReceiversAndOnliners(Message message) {
		ArrayList<String> onlineUsers = cl.getAllOnlineUsers();
		ArrayList<String> listOfReceivers = message.getReceivers();
		ArrayList<String> tempList = new ArrayList<String>();

		for (String receiverOnList : listOfReceivers) {
			boolean receiverFound = false;
			for (String onlineUser : onlineUsers) {

				if (receiverOnList.equals(onlineUser)) {
					receiverFound = true;
					System.out.println(receiverOnList + " is online");
					sendMessageToOnlineUser(message, receiverOnList);
					break;
				}
			}
			if (receiverFound == false) {
				tempList.add(receiverOnList);
				System.out.println(receiverOnList + " is not online");
			}
		}
		if (!tempList.isEmpty()) {
			message.setReceiver(tempList); // ersätt med ny lista (de som är offline)
			unsentMessages.add(message);
		}
	}

	public void sendMessageToOnlineUser(Message msg, String name) {
		User user = cl.getUser(name);
		cl.get(user).sendMessage(msg);

	}

	// may be implemented, but not required
	// public void registerUser(String name, ImageIcon icon) {
	// }

	/**
	 * Inner-class which handles the list of online users.
	 * 
	 * @author Jessica
	 *
	 */
	private class Clients {

		/* HashMap f�r att en anv�ndare ska associeras till en klient */
		private HashMap<User, ClientHandler> onlineUsers = new HashMap<User, ClientHandler>();

		/**
		 * Associates the specified user with the specified clientHandler-thread in this
		 * map.
		 * 
		 * @param user
		 *            key with which the specified value is to be associated
		 * @param clientHandler
		 *            value to be associated with the specified key
		 */
		public synchronized void put(User user, ClientHandler clientHandler) {
			if (getUser(user.getName()).equals(null)) { // nullpointer
				onlineUsers.put(user, clientHandler);

				// send messages to user if there are any unsent messages
				for (Message message : unsentMessages) {
					for (String receiver : message.getReceivers()) {
						if(receiver.equals(user.getName())) {
							clientHandler.sendMessage(message);
							break;
						}
					}
				}

			} else {
				// provide a new username if chosen username already exists 
				Random rand = new Random();
				String temp = user.getName() + rand.nextInt(99);
				user.setName(temp);
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
		 * Returns the user-object which has this name
		 * @param name 
		 * @return the user with this name
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
		 * Removes the user from the list of online users.
		 * 
		 * @param user
		 *            whose mapping is to be removed from the map
		 */
		public synchronized void remove(User user) {
			onlineUsers.remove(user);
		}

		/**
		 * Returns a String-arraylist of all online users.
		 * 
		 * @return an ArrayList<String> usersOnline
		 */
		public synchronized ArrayList<String> getAllOnlineUsers() {
			ArrayList<String> listOnliners = new ArrayList<>();

			for (User user : onlineUsers.keySet()) {
				listOnliners.add(user.getName());
			}

			return listOnliners;
		}

	}

	/**
	 * Thread which listens for and responds to a client's requests. This
	 * inner-class updates the connected clients...
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
		 * @param cl
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
		 * Reads a message from a client and attempts to forward it to receivers..
		 * 
		 * @param obj
		 *            the message-object
		 * @throws ClassNotFoundException
		 * @throws IOException
		 */
		public void readMessage(Object obj) throws ClassNotFoundException, IOException {
			Message msg = (Message) fromClient.readObject();

			checkReceiversAndOnliners(msg);
		}

		public void sendMessage(Message msg) {

			try {
				toClient.writeObject(msg);
				toClient.flush();
				System.out.println("skickat vidare till klient..");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void run() {

			try {

				/*
				 * det f�rsta som g�rs �r att anv�ndaren l�ggs till i hashmap f�r att indikera
				 * att den anv�ndare �r online Kollar INTE om namn redan finns
				 */
				user = (User) fromClient.readObject();
				cl.put(user, this);

				while (true) {
					Object obj = fromClient.readObject();
					try {
						if (obj instanceof Message) {
							readMessage(obj);
							// anropa metod som l�ser av & skickar till online receivers
							// anropa metod som loggar?
							// anropa metod som sparar meddelande till offline receivers
						}
					} catch (Exception e) {
						System.err.println(e);
					}
				}

			} catch (Exception e1) {
				if (e1 instanceof SocketException) {
					disconnectClient();
					System.out.println("controllern stoppad");
				} else {
					System.err.println(e1);
				}

			}

		}

		private void disconnectClient() {
			try {
				clientSocket.close();
				toClient.close();
				fromClient.close();
				cl.remove(user);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
