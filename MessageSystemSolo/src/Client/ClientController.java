package Client;

import java.awt.Dimension;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import Chatt.StartScreenUI;

public class ClientController {
	private Client client;
	private static StartScreenUI ssui;
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

	public void sendNewUser(String tfNewUsername, ImageIcon icon) {
		client.sendNewUser(tfNewUsername, icon);
//		System.out.println("bla");
		
	}

}
