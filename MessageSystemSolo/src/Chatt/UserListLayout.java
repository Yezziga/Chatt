package Chatt;
//En panel som ska hålla en användares namn och en bild för att placera i listor.

import java.awt.*;

import javax.swing.*;

import Server.Contact;
import Server.User;


public class UserListLayout extends JPanel {
	private JLabel lblUserName;
	private JLabel lblUserImage;
	private ImageIcon imgUser;
	private String userName;
	private JPanel pnlGrid;
	private JCheckBox checkBox;
	private User user;
	
	//test
	private ImageIcon imgUser2 = new ImageIcon("files/elefant.png");
	
	public UserListLayout(User user) {	
		this.user = user;
		setLayout(new GridLayout(1, 3));
		initializeComponents();

		add(lblUserImage);
		add(lblUserName);
		add(checkBox);
		setBorder(BorderFactory.createLineBorder(Color.BLACK));

	}

	private void initializeComponents() {
		lblUserName = new JLabel();
		lblUserImage = new JLabel ();
		checkBox = new JCheckBox();

		lblUserName.setPreferredSize(new Dimension(80,100));
		lblUserImage.setPreferredSize(new Dimension(100,100));
		checkBox.setPreferredSize(new Dimension(30, 100));

		if(user != null) {
			lblUserName.setText(user.getName());
			lblUserImage.setIcon(user.getPicture());
		}

		lblUserName.setHorizontalAlignment(JLabel.CENTER);
		lblUserImage.setHorizontalAlignment(JLabel.LEFT);

		setMaximumSize(new Dimension(220, 100));
		setPreferredSize(new Dimension(220, 100));

	}
	
	public boolean getCheckBoxMarked() {
		return checkBox.isSelected();
	}
	
	public String getUserName() {
		return user.getName();
	}
	
	public User getUser() {
		return this.user;
	}
	

	
//	public static void main(String[] args) {
//		UserListLayout ull = new UserListLayout();
//		JOptionPane.showMessageDialog(null, ull);
//	}

}
