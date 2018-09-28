package Chatt;

import javax.swing.JPanel;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import Client.ClientController;

import javax.swing.JFileChooser;

/**
 * This class will be the start panel when starting the application.
 * 
 * @author Henrik Olofsson
 *
 */
public class StartScreenUI extends JPanel implements ActionListener {
	private JTextField tfNewUsername, tfUsername;
	private JButton btnCreateUser, btnConnect, btnFileButton;
	private JLabel lblNewUsername, lblHeader,lblStatus, lblUsername, lblIcon;
	private JFileChooser fileChooser;
	private ImageIcon image;
	private ClientController controller;

	public StartScreenUI(ClientController controller) {
		this.controller = controller;
		setBackground(SystemColor.textHighlight);
		setLayout(null);

		lblHeader = new JLabel("ChattSystem");
		lblHeader.setFont(new Font("Arial", Font.PLAIN, 18));
		lblHeader.setForeground(SystemColor.textHighlightText);
		lblHeader.setBounds(346, 43, 110, 16);
		add(lblHeader);

		btnCreateUser = new JButton("Create User");
		btnCreateUser.setBounds(35, 221, 139, 25);
		add(btnCreateUser);

		tfNewUsername = new JTextField();
		tfNewUsername.setBounds(35, 163, 170, 22);
		add(tfNewUsername);
		tfNewUsername.setColumns(10);

		lblNewUsername = new JLabel("Write your desired user name");
		lblNewUsername.setForeground(SystemColor.textHighlightText);
		lblNewUsername.setFont(new Font("Arial", Font.PLAIN, 14));
		lblNewUsername.setBounds(35, 120, 193, 16);
		add(lblNewUsername);

		lblStatus = new JLabel("Status:");
		lblStatus.setFont(new Font("Arial", Font.PLAIN, 14));
		lblStatus.setForeground(SystemColor.textHighlightText);
		lblStatus.setBounds(35, 293, 193, 31);
		add(lblStatus);

		lblUsername = new JLabel("Write in the user name you want to connect with");
		lblUsername.setFont(new Font("Arial", Font.PLAIN, 14));
		lblUsername.setForeground(SystemColor.textHighlightText);
		lblUsername.setBounds(35, 430, 301, 16);
		add(lblUsername);

		tfUsername = new JTextField();
		tfUsername.setBounds(35, 474, 146, 22);
		add(tfUsername);
		tfUsername.setColumns(10);

		btnConnect = new JButton("Connect");
		btnConnect.setBounds(35, 525, 97, 25);
		add(btnConnect);

		lblIcon = new JLabel();
		lblIcon.setForeground(SystemColor.textHighlightText);
		lblIcon.setBounds(350, 150, 100, 100);
		add(lblIcon);

		btnFileButton = new JButton("Choose a picture for your profile");
		btnFileButton.setBounds(300, 115, 250, 30);
		add(btnFileButton);

		fileChooser = new JFileChooser();
		btnFileButton.addActionListener(this);
		btnCreateUser.addActionListener(this);

	}

	public String getTfNewUsername() {
		return tfNewUsername.getText();
	}

	public String getTfUsername() {
		return tfUsername.getText();
	}

	public ImageIcon getImage() {
		return image;
	}

	public void setIcon(ImageIcon icon) {
		lblIcon.setIcon(image);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnFileButton) {
			if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("jpg", "png", "bmp"));
				if (fileChooser.getSelectedFile() != null) {
					String path = fileChooser.getSelectedFile().getAbsolutePath();
					Image temporaryImage = new ImageIcon(path).getImage().getScaledInstance(100, 100,
							Image.SCALE_SMOOTH);
					image = new ImageIcon(temporaryImage);
					setIcon(image);
				}
			}
		}
		
		// create a user if there is an input
		if ((e.getSource() == btnCreateUser) && !(getTfNewUsername().isEmpty()) && (image!=null)) {
			System.out.println("En user skapas");
			controller.sendNewUser(tfNewUsername.getText(), image);
			
		}

	}
}
