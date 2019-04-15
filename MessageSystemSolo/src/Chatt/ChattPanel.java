package Chatt;

import java.awt.SystemColor;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Server.User;

public class ChattPanel extends JPanel {
	private JTextArea textArea;
	private JTextField txtField;
	private JButton btnChoosePicture;
	private JButton btnSendMessage;
	
	public ChattPanel(User sender, User receiver) {
		setLayout(null);
        setBackground(SystemColor.textHighlight);
		initializeComponents();	
	}
	
	private void initializeComponents() {
		textArea = new JTextArea();
		textArea.setBounds(20, 100, 650, 420);
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

}
