package Client;

import Chatt.ConnectedUI;
import Chatt.StartScreenUI;
import Chatt.TestConnectedUI;
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

	/**
	 * Constructor which creates the startup window.
	 * @param client the client which this controller is handling
	 */
	public ClientController(Client client) {
		this.client = client;
		frame = new JFrame();
		frame.setTitle("Chatt");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(800,700));
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
	 * @param tfUsername
	 * @param icon
	 */
	public void sendUser(String tfUsername, ImageIcon icon) {
		/*client.connectUser(tfNewUsername, icon);
		cui = new ConnectedUI(this);
		cui.setLblUser(tfNewUsername); // kanske får nytt namn av server
		frame.remove(ssui);
		frame.add(cui);
		frame.pack();	*/
		client.connectUser(tfUsername, icon);
		tcui.setLblUser(tfUsername);
		frame.remove(ssui);
		frame.add(tcui);
		frame.pack();
	}
	
	public void updateOnlineList(ArrayList<String> onlineUsers) {
//		cui.setOnlineList(onlineUsers);
	} 

	public void updateAllUsers(ArrayList<User> arr) {
		this.allUsers = arr;
		tcui.clearList();
		tcui.setAllUsers(arr); 
		System.out.println("USERS IN CLIENT CONTROLLER SET");
	}

	public void clear() {
		tcui.clearList();
		
	}


}
