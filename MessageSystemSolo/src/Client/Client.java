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


	public void disconnect() {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sends a Message-object to the server.
	 * @param msg the message 
	 */
	public void sendMessage(Message msg) {
		try {
			toServer.writeObject(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void readMessage() {
		try {
			Message msg = (Message) fromServer.readObject();
			System.out.println(msg.getMessage() + " client received msg");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private class Listener extends Thread {
		public void run() {
			ArrayList<String> messageReceivers = new ArrayList<>();
			messageReceivers.add("Kalle");
			messageReceivers.add("Balle");
			messageReceivers.add("Nalle");
//			Message msg1 = new Message(user, messageReceivers, "meddelandet");

			
			/*
			 * s� fort en klient skapas, skickar den sin User-info till servern s� att
			 * serven vet vilken user som loggar in. Detta ska bara ske en g�ng.
			 */
//			try {
////				toServer.writeObject(user);
//			} catch (IOException e1) {
//				e1.printStackTrace();
//			}
			


			/**
			 * h�r b�r klienten lyssna efter uppdateringar av listor & inkommande
			 * meddelanden fr�n server
			 */
			while (true) {
//				sendMessage(msg1);
				try {
					Thread.sleep(5000);
				} catch (Exception e) {
					System.err.println(e);
					e.printStackTrace();
				}
//				disconnectClient();
			}
		}

	}
	
	public static void main(String[] args) {
		Client client1 = new Client("127.0.0.1", 4447);
		
//		 Client client2 = new Client("127.0.0.1",4447, new User("Nalle"));
		// Client client3 = new Client("127.0.0.1",4447);
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

}
