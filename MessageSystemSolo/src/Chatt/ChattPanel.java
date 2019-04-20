package Chatt;

import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Client.ClientController;
import Client.Message;
import Server.User;

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
	
	public ChattPanel(ClientController controller, User sender, User receiver) {
		this.controller = controller;
		this.sender = sender;
		this.receiver = receiver;
		setLayout(null);
        setBackground(SystemColor.textHighlight);
		initializeComponents();	
		registerListeners();
	}
	
	private void initializeComponents() {
		textArea = new JTextArea();
		textArea.setBounds(20, 100, 650, 420);
		textArea.setEditable(false);
		add(textArea);
		
		txtField = new JTextField();
		txtField.setBounds(20, 550, 500, 60);
		add(txtField);
		
		btnChoosePicture = new JButton("Choose Picture");
		btnChoosePicture.setBounds(540, 550, 130, 20);
		add(btnChoosePicture);
		
		btnSendMessage = new JButton("Send");
		btnSendMessage.setBounds(540, 580, 130, 30);
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
	
	public void addMessageToChat(Message message) {
		System.out.println("In AddMessageToChat - ChattPanel: ");
//		textArea.append(message.getSender().getName() + ": " + message.getMessage() + "\n");
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

	private class SendMessageListener implements ActionListener {
		@Override 
		public void actionPerformed(ActionEvent arg0) {
			ArrayList<User> receivers = new ArrayList<User>();
			receivers.add(receiver);
			Message msg = new Message(sender, receivers, txtField.getText());
			msg.setDateReceived(new Date());
			addMessageToChat(msg);
		//	receivers.add(receiver);
			controller.sendMessageToUsers(msg);
			txtField.setText("");
		}
		
	}

}
