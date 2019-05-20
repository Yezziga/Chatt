package Server;

import java.time.LocalDateTime;

public class LogMessage {
	private LocalDateTime date;
	private String message;
	
	public LogMessage(String message) {
		 
		LocalDateTime nan = LocalDateTime.now();
		this.date = nan.minusNanos(nan.getNano());
		
		this.message = message;
	}
	
	public LocalDateTime getDate() {
		return date;
	}
	
	public String getMessage() {
		return message;
	}
	
	public String toString() {
		return date.toString() + " | " + message;
	}
}
