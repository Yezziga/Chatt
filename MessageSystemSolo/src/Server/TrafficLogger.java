package Server;

import java.io.*;
import java.util.Calendar;
import java.util.Date;

import Client.Message;

/**
 * The TrafficLogger class saves all the traffic within the system in a
 * text-file.
 * 
 * @author Jessica Quach
 *
 */
public class TrafficLogger {
	private static TrafficLogger instance = null;
	private String filename = "files/Server_log.txt";
	private static Writer toFile;
	private ServerUI ui;

	private TrafficLogger() {
		try {
			toFile = new OutputStreamWriter(new FileOutputStream(filename, true), "ISO-8859-1");
			ui = new ServerUI();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Constructs a TrafficLogger-instance if there is none, otherwise it returns a
	 * instance of this class.
	 * 
	 * @return the instance of this class
	 */
	public static synchronized TrafficLogger getInstance() {
		if (instance == null) {
			instance = new TrafficLogger();
		}
		return instance;
	}

	/**
	 * Adds and saves the message-object in a text-file.
	 * 
	 * @param message
	 *            a message-object
	 */
	public synchronized void saveMessageToLog(Message message) {
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		String temp = "[" + date + "]: " + "Date sent: " + message.getDateSend() + ", sender: "
				+ message.getSender().getName() + ", date received: " + message.getDateReceived() + ", receivers: "
				+ message.getReceivers() + ", message: " + message.getMessage();

		try {
			toFile.write(temp + "\n");
			toFile.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public synchronized void saveToLog(String s) {
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		String temp = "[" + date + "]: " + s;

		try {
			toFile.write(temp + "\n");
			toFile.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @return
	 */
	public String getLog() {
		String temp = "";
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String str = br.readLine();
			while (str != null) {
				temp += "\n" + str;
				str = br.readLine();
			}

		} catch (FileNotFoundException e) {
			System.out.println("File could not be found");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Exception Occurred:");
			e.printStackTrace();
		}
		return temp;
	}


}
