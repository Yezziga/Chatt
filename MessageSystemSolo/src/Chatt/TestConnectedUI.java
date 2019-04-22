package Chatt;

import Client.ClientController;
import Client.Message;
import Server.Contact;
import Server.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TestConnectedUI extends JPanel {
	private ClientController controller;
	private JMenuBar menuBar;
	private JMenu mnUsers;
	private JMenuItem mntmOnlineUsers;
	private JMenuItem mntmContacts;
	private JLabel lblSignedAs;
	private JPanel pnlScrollPaneAll;
	private JPanel pnlScrollPaneContacts;
	private JScrollPane scrollPaneAll;
	private JScrollPane scrollPaneContacts;
	private ArrayList<User> listOfAllUsers;
	private ArrayList<UserListLayout> layoutList;
	private JTextField txtMessageField;
	private JButton btnSendMessage;
	private JButton btnOpenChats;
	private User user;
	private JButton btnAddToContacts;
	private JButton btnClose;

	public TestConnectedUI(ClientController controller) {
		this.controller = controller;
		layoutList = new ArrayList<UserListLayout>();
		setBackground(SystemColor.textHighlight);
		setLayout(null);
		initializeMenuBar();
		initializeSignedInLabel();
		initializeLeftScrollPane();
		initializeRightScrollPane();
		initializeMessageSystem();
		initializeButtons();
		registerListeners();
	}

	public void setAllUsers(ArrayList<User> users) {
		this.listOfAllUsers = users;
		updateList();
	}

	private void updateList() {
		UserListLayout userListLayout;
		pnlScrollPaneAll.removeAll();
		for (User user : listOfAllUsers) {
			userListLayout = new UserListLayout(user);
			userListLayout.setMaximumSize(new Dimension(300, 100));
			layoutList.add(userListLayout);
			pnlScrollPaneAll.add(userListLayout);
		}
		scrollPaneAll.updateUI();
	}

	public void updateContactList(ArrayList<Contact> arr) { // FIXA DENNA
		pnlScrollPaneContacts.removeAll();
		UserListLayout userListLayout;
		for (Contact u : arr) {
			userListLayout = new UserListLayout(u);
			userListLayout.setMaximumSize(new Dimension(300, 100));
			pnlScrollPaneContacts.add(userListLayout);
			System.out.println("TESTCONNECTEDUI: USER: " + u);
		}
		scrollPaneContacts.updateUI();

	}

	private void initializeMenuBar() {
		menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 800, 26);
		add(menuBar);

		mnUsers = new JMenu("Users");
		menuBar.add(mnUsers);

		mntmOnlineUsers = new JMenuItem("Online Users");
		mnUsers.add(mntmOnlineUsers);

		mntmContacts = new JMenuItem("Contacts");
		mnUsers.add(mntmContacts);
	}

	private void initializeSignedInLabel() {
		lblSignedAs = new JLabel("You are signed in as: ");
		lblSignedAs.setForeground(SystemColor.textHighlightText);
		lblSignedAs.setBackground(SystemColor.textHighlightText);
		lblSignedAs.setFont(new Font("Arial", Font.PLAIN, 14));
		lblSignedAs.setBounds(10, 39, 253, 16);
		add(lblSignedAs);
	}

	private void initializeLeftScrollPane() {
		pnlScrollPaneAll = new JPanel();
		pnlScrollPaneAll.setLayout(new BoxLayout(pnlScrollPaneAll, BoxLayout.PAGE_AXIS));

		scrollPaneAll = new JScrollPane(pnlScrollPaneAll);
		scrollPaneAll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneAll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPaneAll.setBounds(10, 80, 250, 570);
		add(scrollPaneAll);
		System.out.println("UserListLayoutWidth: " + pnlScrollPaneAll.getWidth() + " UserListLayoutHeight: "
				+ pnlScrollPaneAll.getHeight());
		System.out.println(
				"ScrollPaneWidth: " + scrollPaneAll.getWidth() + " ScrollPaneHeight: " + scrollPaneAll.getHeight());
	}

	private void initializeRightScrollPane() {
		pnlScrollPaneContacts = new JPanel();
		pnlScrollPaneContacts.setLayout(new BoxLayout(pnlScrollPaneContacts, BoxLayout.PAGE_AXIS));
		scrollPaneContacts = new JScrollPane(pnlScrollPaneContacts);
		scrollPaneContacts.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneContacts.setBounds(290, 80, 250, 570);
		add(scrollPaneContacts);
	}

	private void initializeMessageSystem() {
		txtMessageField = new JTextField();
		txtMessageField.setBounds(550, 500, 200, 30);
		btnSendMessage = new JButton("Send");
		btnSendMessage.setBounds(550, 550, 200, 30);
		btnOpenChats = new JButton("Open chats");
		btnOpenChats.setBounds(550, 600, 200, 30);
		add(txtMessageField);
		add(btnSendMessage);
		add(btnOpenChats);
	}

	private void initializeButtons() {
		btnClose = new JButton("Close");
		btnClose.setBounds(550, 80, 200, 30);

		btnAddToContacts = new JButton("Add To Contacts");
		btnAddToContacts.setBounds(550, 130, 200, 30);

		add(btnClose);
		add(btnAddToContacts);

	}

	public void clearList() {
		layoutList.clear();
		if (listOfAllUsers != null) {
			listOfAllUsers.clear();
		}
	}

	public void clearLeftPanel() {
		pnlScrollPaneAll.removeAll();
		scrollPaneAll.updateUI();
	}

	public void clearRightPanel() {
		pnlScrollPaneContacts.removeAll();
		scrollPaneContacts.updateUI();
	}

	public void setUser(User user) {
		this.user = user;
		lblSignedAs.setText("You are signed in as: " + user.getName());
	}

	private void registerListeners() {
		btnSendMessage.addActionListener(new ButtonSendListener());
		btnOpenChats.addActionListener(new ButtonOpenChatsListener());
		btnClose.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.disconnectClient();
				System.exit(0);
				
			}
		});
		btnAddToContacts.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				for (UserListLayout u : layoutList) {
					if (u.getCheckBoxMarked() && e.getSource() == btnAddToContacts) {
						controller.addContact(u.getUser());
					}
				}

			}
		});
	}

	private class ButtonSendListener implements ActionListener {
		ArrayList<User> markedUsers = new ArrayList<>();
		Message message;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			for (UserListLayout ull : layoutList) {
				if (ull.getCheckBoxMarked()) {
					markedUsers.add(ull.getUser());
				}
				System.out.println(markedUsers);
			}
			Calendar calendar = Calendar.getInstance();
			Date date = calendar.getTime();

			message = new Message(user, markedUsers, txtMessageField.getText());
			message.setDateSent(date);
			message.setDateReceived(date);

			controller.sendMessageToUsers(message);
			controller.openNewChattWindow();

			controller.openChatTabs(message);
			controller.addMessageSender(message);

		}
	}

	private class ButtonOpenChatsListener implements ActionListener {
		ArrayList<User> markedUsers = new ArrayList<>();

		@Override
		public void actionPerformed(ActionEvent e) {
			for (UserListLayout ull : layoutList) {
				if (ull.getCheckBoxMarked()) {
					markedUsers.add(ull.getUser());
				}
				System.out.println(markedUsers);
			}
			controller.openChattWindows(user, markedUsers);
		}
	}

}
