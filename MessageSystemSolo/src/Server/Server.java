package Server;

import java.awt.Image;
import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.ImageIcon;

import Client.Message;

public class Server {
	private Clients cl = new Clients(); // inner class
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
		 logger = TrafficLogger.getInstance();
		 logger.log("Logger started"); 
		 System.out.println(logger.getLog());

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
					logger.log(receiverOnList + " is online. Attempting to send message");
					System.out.println(receiverOnList + " is online");
					
					sendMessageToOnlineUser(message, receiverOnList.getName());
					break;
				}
			}
			if (receiverFound == false) {
				tempList.add(receiverOnList);
				logger.log(receiverOnList + " is not online. Storing message to send when online");
				System.out.println(receiverOnList + " is not online");
			}
		}
		if (!tempList.isEmpty()) {
			message.setReceiver(tempList); // ersätt med ny lista (de som är offline)
			unsentMessages.add(message);
		}
	}

	/**
	 * Sends a Message-object to the user with the specified name
	 * 
	 * @param msg
	 *            the message to send
	 * @param name
	 *            the name of the user
	 */
	public void sendMessageToOnlineUser(Message msg, String name) {
		User user = cl.getUser(name);
		cl.get(user).sendMessage(msg);

	}

	public void updateAllClients() {
		ArrayList<User> tempList = null;
		Iterator it = cl.onlineUsers.entrySet().iterator();
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

		private HashMap<User, ClientHandler> onlineUsers = new HashMap<User, ClientHandler>(); // HashMap which contains
																								// online users and
																								// their handlers
		private HashMap<User, ClientHandler> allUsers = new HashMap<User, ClientHandler>(); // HashMap which contains
																							// users and their handlers

		/**
		 * Associates the specified user with the specified clientHandler-thread in this
		 * map. Sends pending messages to this user if there are any.
		 * 
		 * @param user
		 *            key with which the specified value is to be associated
		 * @param clientHandler
		 *            value to be associated with the specified key
		 */
		public synchronized void put(User user, ClientHandler clientHandler) {

			onlineUsers.put(user, clientHandler);
			allUsers.put(user, clientHandler);
			logger.log(user.getName() + " connected to the server");
			updateAllClients();
			// send messages to user if there are any unsent messages. not tested!
			// for (Message message : unsentMessages) {
			// for (String receiver : message.getReceivers()) {
			// if(receiver.equals(user.getName())) {
			// clientHandler.sendMessage(message);
			// break;
			// }
			// }
			// }

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
		 * 
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
			updateAllClients();
		}

		/**
		 * Returns
		 * 
		 * @return an ArrayList<String> usersOnline
		 */
		public synchronized ArrayList<User> getAllOnlineUsers() {
			ArrayList<User> listOnliners = new ArrayList<>();

			for (User user : onlineUsers.keySet()) {
				listOnliners.add(user);
				System.out.print(user.getName() + " ");
			}

			return listOnliners;
		}

		/**
		 * Returns a list of all users of the application
		 * 
		 * @return
		 */
		public ArrayList<User> getAllUsers() { // 	ANVÄNDS EJ?
			ArrayList<User> arr = new ArrayList<>();
			for (User user : allUsers.keySet()) {
				arr.add(user);
			}
			return arr;
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

		public void sendMessage(Message msg) {

			try {
				toClient.writeObject(msg);
				toClient.flush();
				logger.log("message sent to " + user.getName());
				System.out.println("skickat vidare till klient..");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void sendOnlineList() {
			try {
				ArrayList<User> arr = cl.getAllOnlineUsers();
				toClient.writeObject(arr);
				toClient.flush();
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
				if (user.getPicture() == null) {
					Image temp = new ImageIcon("files/MSN-icon.png").getImage().getScaledInstance(100, 100,
							Image.SCALE_SMOOTH);
					user.setPicture(new ImageIcon(temp));
				}

				cl.put(user, this);
				toClient.writeObject(ContactsReader.readContacts(user));
				toClient.flush();

				while (true) {
					Object obj = fromClient.readObject();
					try {
						if (obj instanceof Message) {
							Message msg = (Message) obj;
							Calendar calendar = Calendar.getInstance();
							Date date = calendar.getTime();
							msg.setDateSent(date);
							logger.log("Server received message from client");
							checkReceiversAndOnliners(msg);
						} else if(obj instanceof User) {
							User contactToAdd = (User) obj;
							ContactsReader.addContact(user, contactToAdd);
							toClient.writeObject(ContactsReader.readContacts(user));
							toClient.flush();

						}
					} catch (Exception e) {
						System.err.println(e);
					}
				}

			} catch (Exception e1) {

				disconnectClient();
				System.out.println("client-controller stoppad");


			}

		}

		/**
		 * Disconnects the client from the server and closes all connections to it.
		 */
		private void disconnectClient() {
			try {
				cl.remove(user);
				clientSocket.close();
				toClient.close();
				fromClient.close();
				logger.log(user.getName() + " disconnected from the server.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
