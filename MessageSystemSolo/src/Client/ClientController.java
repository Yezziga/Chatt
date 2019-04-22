package Client;

import Chatt.ChattWindow;
import Chatt.ConnectedUI;
import Chatt.StartScreenUI;
import Chatt.TestConnectedUI;
import Server.Contact;
import Server.User;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class ClientController {
	private Client client;
	private StartScreenUI ssui = new StartScreenUI(this);
	private ConnectedUI cui;
	private JFrame frame;
	private TestConnectedUI tcui = new TestConnectedUI(this);
	private ArrayList<User> allUsers;
	private User user;
	private ChattWindow chattWindow;

	/**
	 * Constructor which creates the startup window.
	 * 
	 * @param client
	 *            the client which this controller is handling
	 */
	public ClientController(Client client) {
		this.client = client;
		frame = new JFrame();
		frame.setTitle("Chatt");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(800, 700));
		frame.setVisible(true);
		frame.setResizable(false);
		frame.add(ssui);
		frame.pack();

	}

	/**
	 * Exits the application.
	 */
	public void disconnectClient() {
		client.disconnect();
		frame.dispose();
	}

	/**
	 * Forwards the user-input to the server and switches to the next panel
	 * 
	 * @param tfUsername
	 * @param icon
	 */
	public void sendUser(String tfUsername, ImageIcon icon) {
		user = new User(tfUsername, icon);
		client.connectUser(user);
		tcui.setUser(user);
		frame.remove(ssui);
		frame.add(tcui);
		frame.pack();
	}

	/**
	 * Forwards the list of online-users.
	 * 
	 * @param arr
	 *            the list of online users
	 */
	public void updateOnlineUsers(ArrayList<User> arr) {
		this.allUsers = arr;
		tcui.clearList();
		tcui.setAllUsers(arr);
		System.out.println("USERS IN CLIENT CONTROLLER SET");
	}

	public void clear() {
		tcui.clearLeftPanel();
	}

	/**
	 * Creates a Message-object from the user's input and calls for the client to
	 * send it to the server.
	 * @param message
	 * 				The message to be sent to the receiver.
	 */
	public void sendMessageToUsers(Message message) {
		client.sendMessage(message);
	}

	public void openChattWindows(User sender, ArrayList<User> receivers) {
		chattWindow = new ChattWindow(this, sender, receivers);
	}

	public void openNewChattWindow(){
		chattWindow = new ChattWindow(this, user.getName());
	}

	/**
	 * Forwards an ArrayList of Contact-objects.
	 * 
	 * @param arr
	 *            the list of contacts
	 */
	public void updateContactList(ArrayList<Contact> arr) {
		tcui.updateContactList(arr);

	}

	public void addMessage(Message msg) {
		System.out.println("1");
		if (chattWindow != null) {
			System.out.println("2");
			if(chattWindow.checkIfChatWindowOpen(msg.getSender())) {
				System.out.println("3");
				System.out.println("in add message: message sender " + msg.getSender());
				chattWindow.handleMessage(msg);
			} else {
				System.out.println("4");
				chattWindow.openNewChatTab(msg);
				chattWindow.handleMessage(msg);
			}

		} else {
			openNewChattWindow();
			chattWindow.openNewChatTab(msg);
			chattWindow.handleMessage(msg);
		}
	}

	public void addMessageSender(Message msg) {
		chattWindow.addMessage(msg);
	}

	public void addContact(User user) {
		client.addContact(user);

	}

	public void openChatTabs(Message msg) {
			chattWindow.openChatTabs(msg);
	}

}
