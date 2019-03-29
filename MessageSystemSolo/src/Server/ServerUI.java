package Server;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;

//Ett UI för servern som ska kunna visa all trafik mellan två tidpunkter

public class ServerUI {
	private JFrame frame = new JFrame("Server log");
	private JTextArea display = new JTextArea();
	private JTextField txtInput = new JTextField();
	

	public ServerUI() {
		
		display.setLineWrap(true);
		display.setWrapStyleWord(true);
		display.setEditable(false);
		JScrollPane scroller = new JScrollPane(display);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroller.setBounds(0, 0, 650, 390);
	
		txtInput.setBounds(1, 409, 649, 20);
		txtInput.setBorder(BorderFactory.createLineBorder(Color.black));
		
		frame.add(txtInput);
		frame.add(scroller);
		start();
	}

	public void append(String str) {
		display.append(str + "\n");
	}
	
	public String getInput() {
		return txtInput.getText();
	}

	public void start() {

		frame.setBounds(0, 0, 650, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.setVisible(true);
		frame.setResizable(false); // Prevent user from change size
		frame.setLocationRelativeTo(null); // Start middle screen
	}
	
	public static void main(String[] args) {
		new ServerUI();
	}
}
