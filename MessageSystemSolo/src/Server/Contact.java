package Server;

import java.io.Serializable;

import javax.swing.ImageIcon;

public class Contact implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private ImageIcon picture = null;

	public Contact(String name, ImageIcon pic) {
		this.name = name;
		this.picture = pic;

	}

	public void setPicture(ImageIcon picture) {
		this.picture = picture;
	}

	public ImageIcon getPicture() {
		return picture;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
}
