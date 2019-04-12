package Client;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import Chatt.ConnectedUI;
import Chatt.StartScreenUI;

public class ClientController {
	private Client client;
	private StartScreenUI ssui;
	private ConnectedUI cui;
	private JFrame frame;

	public ClientController(Client client) {
		this.client = client;
		frame = new JFrame();
		frame.setTitle("Chatt");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(800,700));
		frame.setVisible(true);
		frame.setResizable(false);
		ssui = new StartScreenUI(this);
		frame.add(ssui);
		frame.pack();
		
	}
	
	public void disconnectClient() {
		client.disconnect();
	}

	public void sendUser(String tfNewUsername, ImageIcon icon) {
		client.connectUser(tfNewUsername, icon);
		cui = new ConnectedUI(this);
		cui.setLblUser(tfNewUsername); // kanske får nytt namn av server
		frame.remove(ssui);
		frame.add(cui);
		frame.pack();	
	}
	
	public void updateOnlineList(ArrayList<String> onlineUsers) {
//		cui.setOnlineList(onlineUsers);
	}

}
