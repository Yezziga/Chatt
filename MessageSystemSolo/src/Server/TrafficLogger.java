package Server;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

/**
 * The TrafficLogger class saves all the traffic within the system in a
 * text-file.
 * 
 * @author Jessica 
 *
 */
public class TrafficLogger {
	private static TrafficLogger instance = null;
	private static ArrayList<LogMessage> logMessageList = new ArrayList<LogMessage>();
	private String filename = "files/Server_log.txt";
	private static PrintWriter toFile;
	private ServerUI ui;

	/**
	 * Creates an outputstream to write to the server log and opens an UI for the server log.
	 */
	private TrafficLogger() {
		try {
			toFile = new PrintWriter((filename), "ISO-8859-1");
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
	 * Iterates through the list of LogMessages and fetches all messages between an interval in text-format.
	 * @param to the date to fetch to
	 * @param from the date to start fetching from
	 * @return a String of all the events between two dates
	 */
	public static String getLog(Calendar to, Calendar from) {
		String temp = "";
		for (LogMessage message : logMessageList) {
			Date messageDate = message.getDate();
			if (messageDate.compareTo(from.getTime()) >= 0 && messageDate.compareTo(to.getTime()) <= 0) {
				temp += "[" + messageDate + "] " + message.getMessage() + "\n";
			}
		}
		return temp;
	}

	/**
	 * Creates a new LogMessage-object for the string to log and saves it to the server log
	 * @param message the string-message to log
	 */
	public void log(String message) {
		logMessageList.add(new LogMessage(message));
		saveToLog();
		ui.append("[" + new Date() + "] " + message);
	}

	/**
	 * Writes all logmessages to the server log.
	 */
	private void saveToLog() {

		for (LogMessage message : logMessageList) {
			toFile.write(message.toString() + "\n");
		}
		toFile.flush();

	}

}
