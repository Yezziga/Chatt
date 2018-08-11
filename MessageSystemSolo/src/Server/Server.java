package Server;

import java.io.*;
import java.net.*;
import java.util.*;
import Client.Message;

public class Server {
	private Clients cl = new Clients();
	private ArrayList<Message> unsentMessages = new ArrayList<>();

	/**
	 * Creates the server in the requested port and instantiates a ServerSocket.
	 * Then it listens for a client to connect to this socket and starts a
	 * ClientHandler-thread.
	 * 
	 * @param serverPort
	 *            the port to connect to
	 */
	public Server(int serverPort) {
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
			if (!receiverFound) {
				tempList.add(receiverOnList);
				System.out.println(receiverOnList + " is not online");
			}
		}
		if (!tempList.isEmpty()) {
			message.setReceiver(tempList);
			unsentMessages.add(message);
		}
	}

	public void sendMessageToOnlineUser(Message msg, String name) {
		User user = cl.getUser(name);
		cl.get(user).sendMessage(msg);

	}

	/**
	 * Inner-class which handles the list of online users.
	 * 
	 * @author Jessica
	 *
	 */
	private class Clients {

		/* HashMap för att en användare ska associeras till en klient */
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
			onlineUsers.put(user, clientHandler);
		}

		/**
		 * 
		 * 
		 * @param user
		 * @return
		 */
		public synchronized ClientHandler get(User user) {
			return onlineUsers.get(user);
		}

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
		 * Reads a message from a client and adds all receivers in a stored list.
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
				System.out.println("skickar vidare till klient..");
				toClient.writeObject(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void run() {

			try {

				/*
				 * det första som görs är att användaren läggs till i hashmap för att indikera
				 * att den användare är online
				 */
				user = (User) fromClient.readObject();
				cl.put(user, this);

				while (true) {
					Object obj = fromClient.readObject();
					try {
						if (obj instanceof Message) {
							readMessage(obj);
							// anropa metod som läser av & skickar till online receivers
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

	public static void main(String[] args) {
		Server srv = new Server(4447);
		// Server srv = new Server();
	}

}
