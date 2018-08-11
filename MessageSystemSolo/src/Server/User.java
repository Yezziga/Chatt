package Server;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import Client.Message;

//Skriv gärna vad klassen gör och vem som skrivit den
public class User implements Serializable {
	private String name;
	private ImageIcon picture;

	public User(String username) {
		this.name = username;
	}

	public User(String name, ImageIcon pic) {
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
