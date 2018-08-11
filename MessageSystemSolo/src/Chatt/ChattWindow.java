package Chatt;

import java.awt.Dimension;

import javax.swing.JFrame;

/**
 * This class shall be the frame/window for our chat-application.
 * @author Henrik Olofsson
 *
 */

public class ChattWindow extends JFrame {
	
	public ChattWindow(String title) {
		this.setTitle(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(800,910));
		this.setVisible(true);
		this.setResizable(false);
		
	}
	
	
	
	
}
