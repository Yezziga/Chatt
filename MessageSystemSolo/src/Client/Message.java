package Client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ImageIcon;

import Server.User;

/**
 * This class represents a Message-object being sent between users. A message
 * can contain a text and/or a picture.
 * 
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

	/**
	 * Sets the sender of the message
	 * @param user which sends the message
	 */
	public void setSender(User user) {
		this.sender = user;
	}

	/**
	 * Sets the list of receivers
	 * @param receivers a list of Users
	 */
	public void setReceiver(ArrayList<User> receivers) {
		this.receivers = receivers;
	}

	/**
	 * Returns the sender of the message
	 * @return User which sent the message
	 */
	public User getSender() {
		return sender;
	}

	/**
	 * Returns the message(the text)
	 * @return a string of text
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Returns the user's image
	 * @return the user's image
	 */
	public ImageIcon getImage() {
		return image;
	}

	/**
	 * Sets the user's image
	 * @param image 
	 */
	public void setImage(ImageIcon image) {
		this.image = image;
	}

	/**
	 * Returns the list of receivers
	 * @return Arraylist of users(receivers)
	 */
	public ArrayList<User> getReceivers() {
		return receivers;
	}
/**
 * Sets the date the message was sent.
 * @param date the date the message was sent
 */
	public void setDateSent(Date date) {
		this.dateSent = date;
	}

	/**
	 * Sets the date the message was received
	 * @param date the date the message was received
	 */
	public void setDateReceived(Date date) {
		this.dateReceived = date;
	}

	/**
	 * Retrieves the date the message was sent
	 * @return the date the message was sent
	 */
	public Date getDateSend() {
		return dateSent;
	}

	/**
	 * Retrieves the date the message was received
	 * 
	 * @return the date the message was received
	 */
	public Date getDateReceived() {
		return dateReceived;
	}

	@Override
	public String toString() {
		return "Message [sender=" + sender + ", receivers=" + receivers + ", message=" + message + ", image=" + image
				+ ", dateSent=" + dateSent + ", dateReceived=" + dateReceived + "]";
	}

	/**
	 * Adds a receiver to the list of receivers
	 * @param receiver the user to add to the list
	 */
	public void addSingleReceiver(User receiver) {
		receivers.add(receiver);
	}
	
	public void removeReceiver(User receiver) {
		receivers.remove(receiver);
	}
}
