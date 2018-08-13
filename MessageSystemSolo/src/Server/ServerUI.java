package Server;

import java.awt.Dimension;

import javax.swing.*;

//Ett UI för servern som ska kunna visa all trafik mellan två tidpunkter

public class ServerUI {
	private JFrame frame = new JFrame("Server log");
	private JTextArea txtArea = new JTextArea();

	public ServerUI() {
		start();
		
		frame.add(txtArea);
	}
	
	public void setText(String str) {
		txtArea.setText(str);
	}

	public void start() {

		frame.setBounds(0, 0, 601, 482);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.setVisible(true);
		frame.setResizable(false); // Prevent user from change size
		frame.setLocationRelativeTo(null); // Start middle screen
	}

	public static void main(String args[]) {
		new ServerUI();
	}
}
