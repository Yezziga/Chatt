package Client;

import Chatt.ChattWindow;
import Chatt.ConnectedUI;
import Chatt.StartScreenUI;
import Chatt.ConnectedUI;
import Server.Contact;
import Server.User;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class ClientController {
	private Client client;
	private StartScreenUI ssui = new StartScreenUI(this);
	private JFrame frame;
	private ConnectedUI cui = new ConnectedUI(this);
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
	 * Exits the application, closing all connections between the server and the client.
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
		cui.setUser(user);
		frame.remove(ssui);
		frame.add(cui);
		frame.pack();
	}

	/**
	 * Forwards the list of online-users.
	 * 
	 * @param arr
	 *            the list of online users
	 */
	public void updateOnlineUsers(ArrayList<User> arr) {
		cui.clearList();
		cui.setAllUsers(arr);
	}

	public void clear() {
		cui.clearLeftPanel();
	}

	/**
	 * Creates a Message-object from the user's input and calls for the client to
	 * send it to the server.
	 * 
	 * @param message
	 *            The message to be sent to the receiver.
	 */
	public void sendMessageToUsers(Message message) {
		client.sendMessage(message);
	}

	public void openChattWindows(User sender, ArrayList<User> receivers) {
		chattWindow = new ChattWindow(this, sender, receivers);
	}

	public void openNewChattWindow() {
		if (chattWindow == null) {
			chattWindow = new ChattWindow(this, user.getName());
		}
	}

	/**
	 * Forwards an ArrayList of Contact-objects to the client's UI.
	 * 
	 * @param arr
	 *            the list of contacts
	 */
	public void updateContactList(ArrayList<Contact> arr) {
		cui.updateContactList(arr);

	}

	public void addMessage(Message msg) {
		if (chattWindow != null) {
			if (chattWindow.checkIfChatWindowOpen(msg.getSender())) {
			} else {
				chattWindow.openNewChatTab(msg);
			}
		} else {
			openNewChattWindow();
			chattWindow.openNewChatTab(msg);
		}
		chattWindow.handleMessage(msg);
		if (msg.getImage() != null) {
			ImageIcon temp = msg.getImage();
			chattWindow.showPictureMessage(temp);
		}
	}

	public void addMessageSender(Message msg) {
		chattWindow.addMessage(msg);
	}

	/**
	 * Forwards the user to add as contact to the client.
	 * @param user the user to add as contact
	 */
	public void addContact(User user) {
		client.addContact(user);

	}

	public void openChatTabs(Message msg) {
		chattWindow.openChatTabs(msg);
	}

}
