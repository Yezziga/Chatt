package Server;

import java.util.Date;

public class LogMessage {
	private Date date;
	private String message;
	
	public LogMessage(String message) {
		this.date = new Date();
		this.message = message;
	}
	
	public Date getDate() {
		return date;
	}
	
	public String getMessage() {
		return message;
	}
	
	public String toString() {
		return date.toString() + " | " + message;
	}
}
