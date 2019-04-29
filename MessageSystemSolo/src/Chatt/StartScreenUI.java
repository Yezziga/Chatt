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

import Client.ClientController;

import javax.swing.JFileChooser;

/**
 * This is the starting UI for the application.
 * 
 * @author Henrik Olofsson
 *
 */
public class StartScreenUI extends JPanel implements ActionListener {
	private JTextField tfUsername;
	private JButton btnConnect, btnFileButton;
	private JLabel lblUsername, lblHeader, lblStatus, lblIcon;
	private JFileChooser fileChooser;
	private ImageIcon image = null;
	private ClientController controller;

	/**
	 * Constructor which builds the components in the panel.
	 * @param controller the ClientController for this panel.
	 */
	public StartScreenUI(ClientController controller) {
		this.controller = controller;
		setBackground(SystemColor.textHighlight);
		setLayout(null);

		lblHeader = new JLabel("ChattSystem");
		lblHeader.setFont(new Font("Arial", Font.PLAIN, 18));
		lblHeader.setForeground(SystemColor.textHighlightText);
		lblHeader.setBounds(346, 43, 110, 16);
		add(lblHeader);

		
		lblUsername = new JLabel("Enter your username");
		lblUsername.setForeground(SystemColor.textHighlightText);
		lblUsername.setFont(new Font("Arial", Font.PLAIN, 14));
		lblUsername.setBounds(35, 120, 193, 16);
		add(lblUsername);

		lblStatus = new JLabel("Status: ");
		lblStatus.setFont(new Font("Arial", Font.PLAIN, 14));
		lblStatus.setForeground(SystemColor.textHighlightText);
		lblStatus.setBounds(35, 293, 193, 31);
		add(lblStatus);

		tfUsername = new JTextField();
		tfUsername.setBounds(35, 163, 170, 22);
		add(tfUsername);

		btnConnect = new JButton("Connect");
		btnConnect.setBounds(35, 221, 139, 25);
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
		btnConnect.addActionListener(this);

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
	
	public void updateStatus(String str) {
		lblStatus.setText("Status: " + str);
	}

	/**
	 * The listener for JFileChooser:s button. Used to select a file
	 * that the user wishes to use as a profile picture.
	 * @param e
	 */

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
		
		if (e.getSource() == btnConnect) {
			controller.sendUser(getTfUsername(), getImage());
		}
		

	}
}
