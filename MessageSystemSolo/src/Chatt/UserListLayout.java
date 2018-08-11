package Chatt;
//En panel som ska hålla en användares namn och en bild för att placera i listor.

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class UserListLayout extends JPanel {
	private JLabel lblUserName = new JLabel();
	private JLabel lblUserImage = new JLabel ();
	private ImageIcon imgUser;
	private String userName;
	private JPanel pnlGrid = new JPanel(new GridLayout(1,2
			));
	
	//test
	private ImageIcon imgUser2 = new ImageIcon("images/känguru.jpg");
	
	public UserListLayout() {		//Param user user
		setLayout(new BorderLayout());
		
		lblUserName.setPreferredSize(new Dimension(80,40));	
		lblUserImage.setPreferredSize(new Dimension(80,40));
		
		lblUserName.setText("Henrik"); //user.getname
		lblUserImage.setIcon(imgUser2);	//user.getpicture
		
		lblUserName.setHorizontalAlignment(JLabel.CENTER);
		lblUserImage.setHorizontalAlignment(JLabel.CENTER);
		
		pnlGrid.add(lblUserName);
		pnlGrid.add(lblUserImage);
		pnlGrid.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		
		add(pnlGrid);
	}
	
	public static void main(String[] args) {
		UserListLayout ull = new UserListLayout();
		JOptionPane.showMessageDialog(null, ull);
	}

}
