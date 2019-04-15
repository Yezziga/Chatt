package Server;



import javax.swing.ImageIcon;

public class Contact extends User {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private ImageIcon picture = null;

	public Contact(String name, ImageIcon pic) {
		super(name, pic);

	}

}
