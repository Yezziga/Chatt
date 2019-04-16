package Server;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * The TrafficLogger class saves all the traffic within the system in a
 * text-file.
 * 
 * @author Jessica Quach
 *
 */
public class TrafficLogger {
	private static TrafficLogger instance = null;
	private static ArrayList<LogMessage> logMessageList = new ArrayList<LogMessage>();
	private String filename = "files/Server_log.txt";
	private static PrintWriter toFile;
	private ServerUI ui;

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

	public ArrayList<LogMessage> getLog() {
		return logMessageList;
	}

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

	public void log(String message) {
		logMessageList.add(new LogMessage(message));
		saveToLog();
		ui.append("[" + new Date() + "] " + message);
	}

	private void saveToLog() {

		for (LogMessage message : logMessageList) {
			toFile.write(message.toString() + "\n");
		}
		toFile.flush();

	}

}
