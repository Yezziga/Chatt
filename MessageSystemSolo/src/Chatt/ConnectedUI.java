package Chatt;

import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.ScrollPane;
import javax.swing.JScrollBar;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFileChooser;

/**
 * This class is the panel that will be placed in the window when connected.
 * It's the UI for the bigger part of the chat system.
 * 
 * @author Henrik Olofsson
 *
 */
public class ConnectedUI extends JPanel {
	private JTextField textField;
	private JMenuBar menuBar;
	private JMenu mnUsers;
	private JMenuItem mntmOnlineUsers;
	private JMenuItem mntmContacts;
	private JLabel lblYouAreSigned;
	private JScrollPane spLeft;
	private JScrollPane spRight;
	private JLabel lblYouAreChatting;
	private JButton btnSend;

	public ConnectedUI() {
		setBackground(SystemColor.textHighlight);
		setLayout(null);

		menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 800, 26);
		add(menuBar);

		mnUsers = new JMenu("Users");
		menuBar.add(mnUsers);

		mntmOnlineUsers = new JMenuItem("Online Users");
		mnUsers.add(mntmOnlineUsers);

		mntmContacts = new JMenuItem("Contacts");
		mnUsers.add(mntmContacts);

		lblYouAreSigned = new JLabel("You are signed in as: ");
		lblYouAreSigned.setForeground(SystemColor.textHighlightText);
		lblYouAreSigned.setBackground(SystemColor.textHighlightText);
		lblYouAreSigned.setFont(new Font("Arial", Font.PLAIN, 14));
		lblYouAreSigned.setBounds(10, 39, 253, 16);
		add(lblYouAreSigned);

		spLeft = new JScrollPane();
		spLeft.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		spLeft.setBounds(10, 80, 250, 570);
		add(spLeft);

		// userList = new UserList(); //Ändra sen!!!
		// scrollPane.setViewportView(userList.getUserLayout()); //!! ska iterera genom
		// lista och hämta userlistlayout

		JList list = new JList();
		spLeft.setViewportView(list);

		lblYouAreChatting = new JLabel("You are chatting with: ");
		lblYouAreChatting.setForeground(SystemColor.textHighlightText);
		lblYouAreChatting.setBackground(new Color(255, 255, 255));
		lblYouAreChatting.setFont(new Font("Arial", Font.PLAIN, 14));
		lblYouAreChatting.setBounds(289, 82, 235, 16);
		add(lblYouAreChatting);

		btnSend = new JButton("Send");
		btnSend.setBounds(658, 626, 95, 30);
		add(btnSend);

		textField = new JTextField();
		textField.setBounds(286, 625, 380, 30);
		add(textField);
		textField.setColumns(10);

		spRight = new JScrollPane();
		spRight.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		spRight.setBounds(289, 120, 456, 510);
		add(spRight);

	}
}
