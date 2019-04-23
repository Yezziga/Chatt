package Client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ImageIcon;

import Server.User;

/**
 * This class represents a Message-object being sent between users. A message can contain a text and/or a picture.
 * @author Henrik & Jessica
 *
 */
public class Message implements Serializable {
	private User sender;
	private ArrayList<User> receivers;
	private String message;
	private ImageIcon image;
	private Date dateSent;
	private Date dateReceived;
	private User receiver;

	public Message(User sender, ArrayList<User> receivers, String message) {
		this.sender = sender;
		this.receivers = receivers;
		this.message = message;
	}

	public Message(User sender, User receiver, String message) {
		this.sender = sender;
		this.message = message;
		this.receiver = receiver;
		receivers = new ArrayList<>();
		receivers.add(receiver);
	}
	
	public Message(User sender, ArrayList<User> receivers, String message, ImageIcon img) {
		this.sender = sender;
		this.receivers = receivers;
		this.message = message;
		this.image = img;
	}

	public void setSender(User user) {
		this.sender = user;
	}

	public void setReceiver(ArrayList<User> receivers) {
		this.receivers = receivers;
	}

	public User getSender() {
		return sender;
	}

	public String getMessage() {
		return message;
	}

	public ImageIcon getImage() {
		return image;
	}

	public void setImage(ImageIcon image) {
		this.image = image;
	}

	public ArrayList<User> getReceivers() {
		return receivers;
	}

	public void setDateSent(Date date) {
		this.dateSent = date;
	}

	public void setDateReceived(Date date) {
		this.dateReceived = date;
	}

	public Date getDateSend() {
		return dateSent;
	}

	public Date getDateReceived() {
		return dateReceived;
	}

	public void remove(String name) {
		receivers.remove(name);
	}

	@Override
	public String toString() {
		return "Message [sender=" + sender + ", receivers=" + receivers + ", message=" + message + ", image=" + image
				+ ", dateSent=" + dateSent + ", dateReceived=" + dateReceived + "]";
	}


	public void addSingleReceiver(User receiver) {
		receivers.add(receiver);
	}
}
