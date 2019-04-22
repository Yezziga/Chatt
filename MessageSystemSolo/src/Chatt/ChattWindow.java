package Chatt;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Client.ClientController;
import Client.Message;
import Server.User;

/**
 * This class shall be the frame/window for our chat-application.
 * @author Henrik Olofsson
 *
 */

public class ChattWindow extends JFrame {
	private JTabbedPane tabbedPane;
	private ArrayList<User> receivers;
	private ChattPanel chattPanel;
	private User clientsUser;
	private ArrayList<JComponent> listOfChats;
	private ClientController controller;
	private User receiver;

	/**
	 *	Creates a chat window with a reference to the ClientController with the users name.
	 * @param controller
	 * @param username
	 */

	public ChattWindow(ClientController controller, String username) {
		this.setTitle(username);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(700,700));
		this.setVisible(true);
		this.setResizable(false);
		this.controller = controller;

		initializeTabbedPane();
		addTabbedPane();

	}

	/**
	 * Creates a chat window with a reference to the ClientController with the clients user, and all the receivers.
	 * @param controller
	 * @param clientsUser
	 * @param receivers
	 */
	
	public ChattWindow(ClientController controller, User clientsUser, ArrayList<User> receivers) {
		this.setTitle("Test");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(700,700));
		this.setVisible(true);
		this.setResizable(false);
		this.controller = controller;
		this.receivers = receivers;
		this.clientsUser = clientsUser;

		initializeTabbedPane();
		initializePanel();
	}

	/**
	 * Creates a chat window with a reference to the ClientController with the clients user, and a single receiver.
	 * @param controller
	 * @param clientsUser
	 * @param receiver
	 */
	
	public ChattWindow(ClientController controller, User clientsUser, User receiver) {
		this.setTitle(clientsUser.getName());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(700,700));
		this.setVisible(true);
		this.setResizable(false);
		this.controller = controller;
		this.receiver = receiver;
		this.clientsUser = clientsUser;

		initializeTabbedPane();
		addSingleReceiverToArrayList();
		initializePanel();
		
		/*System.out.println("Receiver IN CHATWINDOW: " + receiver.getName());*/
	}
	
	private void initializeTabbedPane() {
		tabbedPane = new JTabbedPane();
		listOfChats = new ArrayList<>();
	}

	private void addSingleReceiverToArrayList() {
		if(receiver != null) {
			receivers = new ArrayList<>();
			receivers.add(receiver);
		}
	}

	/**
	 * This method initializes all the chat panels if the
	 * constructor took in an array list of receivers.
	 */
	private void initializePanel() {
		for(int i = 0; i < receivers.size(); i++) {
			listOfChats.add(createPanel(receivers.get(i)));
		}
		for(int i = 0; i < receivers.size(); i++) {
			tabbedPane.add(receivers.get(i).getName(), listOfChats.get(i));
		}
		/*for(int i = 0; i < listOfChats.size()-1; i++) {
			for(int j = 1; j < listOfChats.size(); j++) {
				if(listOfChats.get(i) == listOfChats.get(j)) {
					System.out.println("SAME OBJEEEEEEEECT");
				}
			}
		}*/
		addTabbedPane();
	}
	
	private ChattPanel createPanel(User receiver) {
		ChattPanel chattPanelToAdd = new ChattPanel(controller, clientsUser, receiver);
		return chattPanelToAdd;
	}
	
	private void addTabbedPane() {
		add(tabbedPane);
		pack();
		tabbedPane.setVisible(true);
		repaint();
	}

	/**
	 * Checks if a chat panel is already open with a specific user.
	 * @param receiver
	 * @return
	 */
	public boolean checkIfChatWindowOpen(User receiver) {
		if(listOfChats.size() > 0) {
			for(int i = 0; i < listOfChats.size(); i++) {
				/*System.out.println("Chatt panels receiver " + ((ChattPanel)listOfChats.get(i)).getReceiver() + " and the receiver of mesage is " + receiver.getName());*/
				if(((ChattPanel)listOfChats.get(i)).getReceiver().equals(receiver.getName())) {
					/*System.out.println("Chatt panels receiver " + ((ChattPanel)listOfChats.get(i)).getReceiver() + " and the receiver of mesage is " + receiver.getName());
					System.out.println("IT IS TRUE THIS USER IS HERE");*/
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Adds a message in the panel that is used for the specific user.
	 * @param msg
	 */
	public void handleMessage(Message msg) {
		for(int i = 0; i < listOfChats.size(); i++) {
			if(((ChattPanel) listOfChats.get(i)).getReceiver().equals(msg.getSender().getName())) {
				((ChattPanel) listOfChats.get(i)).addMessageToChat(msg);
			}
		}
	}

	/**
	 * Adding the message in the panels on the clients side after sending it to receivers.
	 * @param msg
	 */
	public void addMessage(Message msg) {
		for(int i = 0; i < listOfChats.size(); i++) {
			for (int j = 0; j < msg.getReceivers().size(); j++) {
//				System.out.println("ChattPanels sender: " + ((ChattPanel) listOfChats.get(i)).getSender());
//				System.out.println("ChattPanels receiver: " + ((ChattPanel) listOfChats.get(i)).getReceiver());
//				System.out.println("Message sender: " + msg.getSender().getName());
//				System.out.println("Message receiver: " + msg.getReceivers().get(i).getName());
				if (((ChattPanel) listOfChats.get(i)).getReceiver().equals(msg.getReceivers().get(j).getName())) {
					((ChattPanel) listOfChats.get(i)).addMessageToChat(msg);
				}
			}
		}
	}

	/**
	 * Opens one specific chat tab for every message.
	 * @param message
	 */
	public void openNewChatTab(Message message) {
	/*	System.out.println("openNewChattTab");*/
		chattPanel = new ChattPanel(controller, message.getReceivers().get(0), message.getSender());
		listOfChats.add(chattPanel);
		tabbedPane.addTab(message.getSender().getName(), chattPanel);
		tabbedPane.updateUI();
	}

	/**
	 * Opens a list of chat tabs for every receiver in the message.
	 * @param msg
	 */
	public void openChatTabs(Message msg) {
		for(int i = 0; i < msg.getReceivers().size(); i++) {
			chattPanel = new ChattPanel(controller, msg.getSender(), msg.getReceivers().get(i));
			listOfChats.add(chattPanel);
			tabbedPane.addTab(msg.getReceivers().get(i).getName(), chattPanel);
		}
		tabbedPane.updateUI();

		for(int i = 0; i < listOfChats.size(); i++) {
			System.out.println(listOfChats.get(i));
		}
	}

}
