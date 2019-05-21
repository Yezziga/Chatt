package Server;

import java.io.*;
import java.util.ArrayList;

/**
 * The class reads and writes ArrayList<Contact> to users' file. Each user gets
 * a separate .txt file for storing their contacts.
 * 
 * @author Jessica
 *
 */
public class ContactsReader {

	/**
	 * Creates and/or writes a list of contacts to a file. The file is the given
	 * user's contact list.
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
		boolean isContact = false;
		for (Contact contact : list) {
			if (contact.getName().equals(c.getName())) {
				isContact = true;
				break;
			}
		}

		if (!isContact) {
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

	}

	/**
	 * Reads the given user's contact file if it exists and returns a list of
	 * contacts.
	 * 
	 * @param user
	 *            the user whose contacts are to be extracted.
	 * @return a list of contacts
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

}
