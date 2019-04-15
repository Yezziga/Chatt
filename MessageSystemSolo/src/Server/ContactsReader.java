package Server;

import java.io.*;
import java.util.ArrayList;

/**
 * En lista av kontakter för varje användare som skrivs ner av servern
 * 
 * @author henke
 *
 */

public class ContactsReader {

	/**
	 * Creates and/or writes User-objects to a file. The file is the given user's
	 * contact list.
	 * 
	 * @param user
	 *            the user who wishes to add another user to his/her contact list.
	 * @param userToAdd
	 *            the user to add as a contact.
	 * @throws IOException
	 */
	public static void addContact(User user, User userToAdd) {
		Contact c = new Contact(userToAdd.getName(), userToAdd.getPicture());
		ArrayList<Contact> list = new ArrayList<>();
		if (readContacts(user) != null) {
			list = readContacts(user);
		}
		list.add(c);

		try (ObjectOutputStream toFile = new ObjectOutputStream(
				new BufferedOutputStream(new FileOutputStream("files/" + user.getName() + "_contacts.txt")))) {
			toFile.writeObject(list);
			toFile.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Reads and extracts the User-objects from the given user's contacts file into
	 * an Array-List.
	 * 
	 * @param user
	 *            the user whose contact list is being read.
	 * @return an ArrayList<User> with a user's contact list.
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<Contact> readContacts(User user) {
		ArrayList<Contact> list = null;
		String filePath = "files/" + user.getName() + "_contacts.txt";
		File f = new File(filePath);
		if (f.isFile() && !f.isDirectory()) {
			try (ObjectInputStream fromFile = new ObjectInputStream(
					new BufferedInputStream(new FileInputStream(filePath)))) {
				list = (ArrayList<Contact>) fromFile.readObject();
			} catch (FileNotFoundException e) {
				System.err.println("Filen finns inte");
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		return list;
	}

	public static void main(String[] args) {
		User u1 = new User("Kalle", null);
		User u2 = new User("Sven", null);
		User u3 = new User("Jessi", null);
		User u6 = new User("OK", null);
		User u4 = new User("Nes", null);
		User u5 = new User("Wuh", null);
		 addContact(u5, u1);
		 addContact(u5, u2);
		 addContact(u5, u3);
		 addContact(u5, u6);
		ArrayList<Contact> arr = readContacts(u5);
		for (Contact c : arr) {
			System.out.println(c.getName());
		}

	}

}
