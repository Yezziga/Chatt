package Chatt;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.*;

import Client.ClientController;
import Client.Message;
import Server.User;

/**
 * This class is a panel for every tab in the ChattWindow-class.
 * It is used to set up an individual chat room between two users.
 */

public class ChattPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1344968108806433304L;
	private User sender;
	private User receiver;
	private JTextArea textArea;
	private JTextField txtField;
	private JButton btnChoosePicture;
	private JButton btnSendMessage;
	private ClientController controller;
	private JLabel lblYouAreChattingWith;
	private JLabel lblReceiversUserName;
	private JLabel lblReceiversUserImage;

	/**
	 *
	 * @param controller
	 * 					- The controller is used to communicate between the client and server
	 * @param sender
	 * 					- The sender symbolizes the clients user
	 * @param receiver
	 * 					- The receiver symbolizes the user that sender is communicating with
	 */
	
	public ChattPanel(ClientController controller, User sender, User receiver) {
		this.controller = controller;
		this.sender = sender;
		this.receiver = receiver;
		setLayout(null);
        setBackground(SystemColor.textHighlight);
		initializeComponents();	
		registerListeners();
	}

	/**
	 * Sets up all the components in the panel
	 */
	private void initializeComponents() {
		lblYouAreChattingWith = new JLabel("You are chatting with user: ");
		lblYouAreChattingWith.setForeground(Color.WHITE);
		lblReceiversUserName = new JLabel(receiver.getName());
		lblReceiversUserName.setForeground(Color.WHITE);
		lblReceiversUserImage = new JLabel(receiver.getPicture());

		lblYouAreChattingWith.setBounds(20, 10, 200, 100);
		add(lblYouAreChattingWith);

		lblReceiversUserName.setBounds(240, 10, 150, 100);
		add(lblReceiversUserName);

		lblReceiversUserImage.setBounds(400, 10, 100, 110);
		add(lblReceiversUserImage);


		textArea = new JTextArea();
		textArea.setBounds(20, 130, 650, 420);
		textArea.setEditable(false);
		add(textArea);
		
		txtField = new JTextField();
		txtField.setBounds(20, 560, 500, 60);
		add(txtField);
		
		btnChoosePicture = new JButton("Choose Picture");
		btnChoosePicture.setBounds(540, 560, 130, 20);
		add(btnChoosePicture);
		
		btnSendMessage = new JButton("Send");
		btnSendMessage.setBounds(540, 590, 130, 30);
		add(btnSendMessage);
	}
	
	private void registerListeners() {
		btnSendMessage.addActionListener(new SendMessageListener());
	}
	
	public String getReceiver() {
		return receiver.getName();
	}
	
	public String getSender() {
		return sender.getName();
	}

	/**
	 * Adding the message in the text area.
	 * @param message
	 */
	public void addMessageToChat(Message message) {
		System.out.println("In AddMessageToChat - ChattPanel: ");
		textArea.append("[" + message.getDateReceived() +"] "+ message.getSender().getName() + ": " + message.getMessage() + "\n");
		
	}

	@Override
	public String toString() {
		return "ChattPanel{" +
				"sender=" + sender +
				", receiver=" + receiver +
				", textArea=" + textArea +
				", txtField=" + txtField +
				", btnChoosePicture=" + btnChoosePicture +
				", btnSendMessage=" + btnSendMessage +
				", controller=" + controller +
				'}';
	}

	/**
	 * Inner class that acts as a listener for the send button in the chat panel.
	 */
	private class SendMessageListener implements ActionListener {
		@Override 
		public void actionPerformed(ActionEvent arg0) {
			ArrayList<User> receivers = new ArrayList<User>();
			receivers.add(receiver);
			Message msg = new Message(sender, receivers, txtField.getText());
			msg.setDateReceived(new Date());
			addMessageToChat(msg);
			controller.sendMessageToUsers(msg);
			txtField.setText("");
		}
		
	}

}
