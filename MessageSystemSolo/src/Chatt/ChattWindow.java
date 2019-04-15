package Chatt;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import Client.ClientController;
import Client.Message;
import Server.User;

/**
 * This class shall be the frame/window for our chat-application.
 * @author Henrik Olofsson
 *
 */

public class ChattWindow extends JFrame {
	private JPanel panel;
	private JTabbedPane tabbedPane;
	private ArrayList<User> receivers;
	private ChattPanel chattPanel;
	private User clientsUser;
	private ArrayList<JComponent> listOfChats;
	private ClientController controller;
	
	public ChattWindow(ClientController controller, User clientsUser, ArrayList<User> receivers) {
		this.setTitle("Test");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(700,700));
		this.setVisible(true);
		this.setResizable(false);
		this.controller = controller;
		this.receivers = receivers;
		this.clientsUser = clientsUser;
		
		initializeWindowPanel();
		initializeTabbedPane();
		initializePanel();
	}
	
	private void initializeWindowPanel() {
		JTextArea txtarea = new JTextArea();
		txtarea.setBounds(10, 10, 500, 500);
		panel = new JPanel();
		panel.add(txtarea);
	}
	
	private void initializeTabbedPane() {
		tabbedPane = new JTabbedPane();
		listOfChats = new ArrayList<>();
	}
	
	private void initializePanel() {
		for(int i = 0; i < receivers.size(); i++) {
			listOfChats.add(createPanel(receivers.get(i)));
			tabbedPane.addTab(receivers.get(i).getName(), null, listOfChats.get(i));
		}
		addTabbedPane();
	}
	
	private ChattPanel createPanel(User receiver) {
		chattPanel = new ChattPanel(controller, clientsUser, receiver);
		return chattPanel;
	}
	
	private void addTabbedPane() {
		add(tabbedPane);
		pack();
		tabbedPane.setVisible(true);
		repaint();
	}

	public void handleMessage(Message msg) {
		System.out.println("Handle Message: ChattWindow: ");
		ArrayList<User> tempReceivers = msg.getReceivers();
		for(int i = 0; i < listOfChats.size(); i++) {
			System.out.println("Receiver in temp receivers: " + tempReceivers.get(i).getName());
			System.out.println("Receiver in"
					+ "list of chats: " + ((ChattPanel)listOfChats.get(i)).getSender());
			
			if(((ChattPanel)listOfChats.get(i)).getSender().equals(tempReceivers.get(i).getName())) {
				System.out.println("XXReceiver in temp receivers: " + tempReceivers.get(i).getName() + "\nReceiver in"
						+ "list of chats: " + ((ChattPanel)listOfChats.get(i)).getReceiver());
				((ChattPanel)listOfChats.get(i)).addMessageToChat(msg);
			}
		}
		
	}
	
	
	
	
}
