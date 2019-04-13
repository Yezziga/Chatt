package Chatt;

import Client.ClientController;
import Server.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TestConnectedUI extends JPanel {
    private ClientController controller;
    private JMenuBar menuBar;
    private JMenu mnUsers;
    private JMenuItem mntmOnlineUsers;
    private JMenuItem mntmContacts;
    private JLabel lblSignedAs;
    private JPanel pnlScrollPaneAll;
    private JPanel pnlScrollPaneContacts;
    private JScrollPane scrollPaneAll;
    private JScrollPane scrollPaneContacts;
    private ArrayList<User> listOfAllUsers;
    private ArrayList<UserListLayout> layoutList;
    private UserListLayout userListLayout;


    public TestConnectedUI(ClientController controller) {
        this.controller = controller;
        layoutList = new ArrayList<UserListLayout>();
        setBackground(SystemColor.textHighlight);
        setLayout(null);
        initializeMenuBar();
        initializeSignedInLabel();
        initializeLeftScrollPane();
        initializeRightScrollPane();

        fillLeftScrollPaneWithBullShit();
        System.out.println("TEST CONNECTED UI SET");
    }
    

	public void setAllUsers(ArrayList<User> users) {
        this.listOfAllUsers = users;
        updateList();
    }
	
	private void updateList() {
		pnlScrollPaneAll.removeAll();
		for(User u : listOfAllUsers) {
			System.out.println(u + " user finns");
			userListLayout = new UserListLayout(u.getName(), u.getPicture());
			userListLayout.setMaximumSize(new Dimension(300, 100));
			layoutList.add(userListLayout);
			pnlScrollPaneAll.add(userListLayout);
		}
		scrollPaneAll.updateUI();
	}

    private void initializeMenuBar(){
        menuBar = new JMenuBar();
        menuBar.setBounds(0, 0, 800, 26);
        add(menuBar);

        mnUsers = new JMenu("Users");
        menuBar.add(mnUsers);

        mntmOnlineUsers = new JMenuItem("Online Users");
        mnUsers.add(mntmOnlineUsers);

        mntmContacts = new JMenuItem("Contacts");
        mnUsers.add(mntmContacts);
    }

    private void initializeSignedInLabel(){
        lblSignedAs = new JLabel("You are signed in as: ");
        lblSignedAs.setForeground(SystemColor.textHighlightText);
        lblSignedAs.setBackground(SystemColor.textHighlightText);
        lblSignedAs.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSignedAs.setBounds(10, 39, 253, 16);
        add(lblSignedAs);
    }

    private void initializeLeftScrollPane(){
        pnlScrollPaneAll = new JPanel();
        pnlScrollPaneAll.setLayout(new BoxLayout(pnlScrollPaneAll, BoxLayout.PAGE_AXIS));
//        userListLayout1.setMaximumSize(new Dimension(300, 100));
//        userListLayout2.setMaximumSize(new Dimension(300, 100));
//        pnlScrollPaneAll.add(userListLayout1);
//        pnlScrollPaneAll.add(userListLayout2);

        scrollPaneAll = new JScrollPane(pnlScrollPaneAll);
        scrollPaneAll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneAll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPaneAll.setBounds(10, 80, 250, 570);
        add(scrollPaneAll);
        System.out.println("UserListLayoutWidth: " + pnlScrollPaneAll.getWidth() + " UserListLayoutHeight: " + pnlScrollPaneAll.getHeight());
        System.out.println("ScrollPaneWidth: " + scrollPaneAll.getWidth() + " ScrollPaneHeight: " + scrollPaneAll.getHeight());
    }

    private void initializeRightScrollPane() {
        scrollPaneContacts = new JScrollPane();
        scrollPaneContacts.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneContacts.setBounds(290, 80, 250, 570);
        add(scrollPaneContacts);
    }

    private void fillLeftScrollPaneWithBullShit() {
        scrollPaneAll.updateUI();
    }


	public void clearList() {
		layoutList.clear();
		if(listOfAllUsers != null) {
			listOfAllUsers.clear();
		}
	}
}
