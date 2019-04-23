package Server;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import Client.Message;

/**
 * This class represents a User.
 * @author Jessica & Henrik
 *
 */
public class User implements Serializable {
	private String name;
	private ImageIcon picture = null;

	/**
	 * Constructs a User-object without a chosen picture.
	 * @param username the name of the user
	 */
	public User(String username) {
		this.name = username;
	}

	/**
	 * Constructs a User-object with a chosen picture.
	 * @param name the name of the user
	 * @param pic the picture the user has chosen
	 */
	public User(String name, ImageIcon pic) {
		this.name = name;
		this.picture = pic;
	}

	/**
	 * Sets a picture to the user.
	 * @param picture the picture to use
	 */
	public void setPicture(ImageIcon picture) {
		this.picture = picture;
	}

	/**
	 * Returns the picture
	 * @return the ImageIcon
	 */
	public ImageIcon getPicture() {
		return picture;
	}

	/**
	 * Sets the name of the user
	 * @param name a new username
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the username
	 * @return the username
	 */
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return "User [name=" + name + ", picture=" + picture + "]";
	}

}
