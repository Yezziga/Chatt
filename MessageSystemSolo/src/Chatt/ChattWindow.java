package Chatt;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import Server.User;

/**
 * This class shall be the frame/window for our chat-application.
 * @author Henrik Olofsson
 *
 */

public class ChattWindow extends JFrame {
	private JPanel panel;
	private JTabbedPane tabbedPane;
	private ArrayList<User> receivers;
	private ChattPanel chattPanel;
	
	public ChattWindow(ArrayList<User> receivers) {
		this.setTitle("Test");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(700,700));
		this.setVisible(true);
		this.setResizable(false);
		this.receivers = receivers;
		
		initializeTabbedPane();
		initializePanel();
	}
	
	private void initializeTabbedPane() {
		tabbedPane = new JTabbedPane();
	}
	
	private void initializePanel() {
		for(User user : receivers) {
			JComponent panel = createPanel(user);
		}
	}
	
	private ChattPanel createPanel(User user) {
		return null;
	}
	
	
	
	
}
