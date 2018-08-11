package Chatt;

import javax.swing.JPanel;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JFileChooser;

/**
 * This class will be the start panel when starting the application.
 * @author Henrik Olofsson
 *
 */
public class StartScreenUI extends JPanel implements ActionListener {
	private JTextField textField;
	private JTextField textField_1;
	private JLabel lblNewLabel;
	private JButton btnCreateUser;
	private JButton btnConnect;
	private JLabel lblWriteYourDesired;
	private JLabel lblStatus;
	private JLabel lblWriteInThe;
	private JLabel lblChooseAPicture;
	
	private JFileChooser fileChooser;
	private JButton btnFileButton;
	private ImageIcon image;
	
	
	
	public StartScreenUI() {
		setBackground(SystemColor.textHighlight);
		setLayout(null);
		
		lblNewLabel = new JLabel("ChattSystem");
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		lblNewLabel.setForeground(SystemColor.textHighlightText);
		lblNewLabel.setBounds(346, 43, 110, 16);
		add(lblNewLabel);
		
		btnCreateUser = new JButton("Create User");
		btnCreateUser.setBounds(35, 221, 139, 25);
		add(btnCreateUser);
		
		textField = new JTextField();
		textField.setBounds(35, 163, 146, 22);
		add(textField);
		textField.setColumns(10);
		
		lblWriteYourDesired = new JLabel("Write your desired user name");
		lblWriteYourDesired.setForeground(SystemColor.textHighlightText);
		lblWriteYourDesired.setFont(new Font("Arial", Font.PLAIN, 14));
		lblWriteYourDesired.setBounds(35, 120, 193, 16);
		add(lblWriteYourDesired);
		
		lblStatus = new JLabel("Status:");
		lblStatus.setFont(new Font("Arial", Font.PLAIN, 14));
		lblStatus.setForeground(SystemColor.textHighlightText);
		lblStatus.setBounds(35, 293, 193, 31);
		add(lblStatus);
		
		lblWriteInThe = new JLabel("Write in the user name you want to connect with");
		lblWriteInThe.setFont(new Font("Arial", Font.PLAIN, 14));
		lblWriteInThe.setForeground(SystemColor.textHighlightText);
		lblWriteInThe.setBounds(35, 430, 301, 16);
		add(lblWriteInThe);
		
		textField_1 = new JTextField();
		textField_1.setBounds(35, 474, 146, 22);
		add(textField_1);
		textField_1.setColumns(10);
		
		btnConnect = new JButton("Connect");
		btnConnect.setBounds(35, 525, 97, 25);
		add(btnConnect);
		
		lblChooseAPicture = new JLabel("Choose a picture for your user");
		lblChooseAPicture.setForeground(SystemColor.textHighlightText);
		lblChooseAPicture.setBackground(SystemColor.textHighlightText);
		lblChooseAPicture.setBounds(304, 120, 174, 16);
		add(lblChooseAPicture);
		
		btnFileButton = new JButton("File Button");
		btnFileButton.setBounds(304, 163, 89, 23);
		add(btnFileButton);
		
		fileChooser = new JFileChooser();
		
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnFileButton) {
			if(fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("jpg", "png", "bmp"));
				if(fileChooser.getSelectedFile() != null) {
					String path = fileChooser.getSelectedFile().getAbsolutePath();
					Image temporaryImage = new ImageIcon(path).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
					image = new ImageIcon(temporaryImage);
				}
			}
		}
		
		if(e.getSource() == btnCreateUser) {
			
		}
		
	}
}
