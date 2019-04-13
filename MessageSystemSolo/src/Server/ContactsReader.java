package Server;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

/**
 * En lista av kontakter för varje användare som skrivs ner av servern
 * 
 * @author henke
 *
 */

public class ContactsReader {
	private static Writer toFile;

	public static void addContact(String username, String userToAdd) throws IOException {
	
			toFile = new OutputStreamWriter(new FileOutputStream("files/" + username + "_contacts.txt", true), "ISO-8859-1");
			toFile.write(userToAdd);

	}

	public static void readContacts() {
		// TODO Auto-generated method stub

	}

}
