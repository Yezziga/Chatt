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
	private JPanel panel;
	private JTabbedPane tabbedPane;
	private ArrayList<User> receivers;
	private ChattPanel chattPanel;
	private User clientsUser;
	private ArrayList<JComponent> listOfChats;
	private ClientController controller;
	private User receiver;

	public ChattWindow(ClientController controller) {
		this.setTitle("Test");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(700,700));
		this.setVisible(true);
		this.setResizable(false);
		this.controller = controller;

		initializeTabbedPane();
		addTabbedPane();

	}
	
	public ChattWindow(ClientController controller, User clientsUser, ArrayList<User> receivers) {
		this.setTitle("Test");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(700,700));
		this.setVisible(true);
		this.setResizable(false);
		this.controller = controller;
		this.receivers = receivers;
		this.clientsUser = clientsUser;
		
		//initializeWindowPanel();
		initializeTabbedPane();
		initializePanel();
		addTabbedListener();
	}
	
	public ChattWindow(ClientController controller, User clientsUser, User receiver) {
		this.setTitle(clientsUser.getName());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(700,700));
		this.setVisible(true);
		this.setResizable(false);
		this.controller = controller;
		this.receiver = receiver;
		this.clientsUser = clientsUser;
		
		//initializeWindowPanel();
		initializeTabbedPane();
		addSingleReceiverToArrayList();
		initializePanel();
		addTabbedListener();
		
		System.out.println("Receiver IN CHATWINDOW: " + receiver.getName());
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
	
	private void addSingleReceiverToArrayList() {
		if(receiver != null) {
			receivers = new ArrayList<>();
			receivers.add(receiver);
		}
	}
	
	private void initializePanel() {
		for(int i = 0; i < receivers.size(); i++) {
			//chattPanel = createPanel(receivers.get(i));
			listOfChats.add(createPanel(receivers.get(i)));
		}
		for(int i = 0; i < receivers.size(); i++) {
			tabbedPane.add(receivers.get(i).getName(), listOfChats.get(i));
		}
		for(int i = 0; i < listOfChats.size()-1; i++) {
			for(int j = 1; j < listOfChats.size(); j++) {
				if(listOfChats.get(i) == listOfChats.get(j)) {
					System.out.println("SAME OBJEEEEEEEECT");
				}
			}
		}
		addTabbedPane();
	}
	
	private ChattPanel createPanel(User receiver) {
		ChattPanel chattPanelToAdd = new ChattPanel(controller, clientsUser, receiver);
		return chattPanelToAdd;
	}
	
	private ChattPanel createPanel(Message msg, User receiver) {
		ChattPanel chattPanelToAdd = new ChattPanel(controller, clientsUser, receiver); //Fixa
		return chattPanelToAdd;
	}
	
	private void addTabbedPane() {
		add(tabbedPane);
		pack();
		tabbedPane.setVisible(true);
		repaint();
	}

	public boolean checkIfChatWindowOpen(User receiver) {
		if(listOfChats.size() > 0) {
			for(int i = 0; i < listOfChats.size(); i++) {
				if(((ChattPanel)listOfChats.get(i)).getReceiver().equals(receiver.getName())) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean checkIfChatWindowOpen(Message msg) {
		System.out.println("CHECKIFCHATWINDOWOPEN: " + msg);
		
		if(listOfChats.size() > 0) {
			for(int i = 0; i < listOfChats.size(); i++) {
				for(int j = 0; j < msg.getReceivers().size(); j++) {
					if(receiver != null) {
						if(((ChattPanel)listOfChats.get(i)).getReceiver() == receiver.getName()) {
							return true;
						}
					} else {
						if(((ChattPanel)listOfChats.get(i)).getReceiver() == receivers.get(j).getName()) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public void handleMessage(Message msg) {
	
		if(checkIfChatWindowOpen(msg)) {
			ArrayList<User> tempReceivers = msg.getReceivers();
			for(int i = 0; i < listOfChats.size(); i++) {
				for(int j = 0; j < tempReceivers.size(); j++) {
				if(((ChattPanel)listOfChats.get(i)).getSender().equals(tempReceivers.get(j).getName())) {
						((ChattPanel)listOfChats.get(i)).addMessageToChat(msg);
					}
				}
			}
			
		} else {
			for(int i = 0; i < listOfChats.size(); i++) {
				for(int j = 0; j < msg.getReceivers().size(); j++) {
				if(((ChattPanel)listOfChats.get(i)).getSender().equals(msg.getReceivers().get(j))) {
					User receiver = msg.getReceivers().get(i);
					chattPanel = createPanel(msg, receiver);
					listOfChats.add(chattPanel);
					tabbedPane.addTab(receiver.getName(), null, chattPanel);
					chattPanel.addMessageToChat(msg);
				}
				}
			}
		}	
	}

	public void testHandleMessage(Message msg) {
		for(int i = 0; i < listOfChats.size(); i++) {
			if(((ChattPanel) listOfChats.get(i)).getReceiver().equals(msg.getSender().getName())) {
				((ChattPanel) listOfChats.get(i)).addMessageToChat(msg);
			}
		}

	/*	for(int i = 0; i < listOfChats.size(); i++) {
			for (int j = 0; j < msg.getReceivers().size(); j++) {
				System.out.println("xxIn testHandleMessage: in this chatpanel receiver is " + ((ChattPanel) listOfChats.get(i)).getReceiver() +
						" and chatpanel sender is " + ((ChattPanel) listOfChats.get(i)).getSender());
				if (((ChattPanel) listOfChats.get(i)).getReceiver().equals(msg.getReceivers().get(j))) {
					System.out.println("xxIn testHandleMessage: in this chatpanel receiver is " + ((ChattPanel) listOfChats.get(i)).getReceiver() +
							" and chatpanel sender is " + ((ChattPanel) listOfChats.get(i)).getSender());
				}
			}
		}*/
	}

	public void openNewChattTab(Message message) {
		System.out.println("openNewChattTab");
		chattPanel = new ChattPanel(controller, message.getReceivers().get(0), message.getSender());
		listOfChats.add(chattPanel);
		tabbedPane.addTab(message.getSender().getName(), chattPanel);
		tabbedPane.updateUI();
	}
	
	private void addTabbedListener() {
		tabbedPane.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				System.out.println(tabbedPane.getComponentAt(tabbedPane.getSelectedIndex()));
				
			}
			
		});
	}
}
