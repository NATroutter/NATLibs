package net.natroutter.natlibs.utilities.libs.NATlogger;

import java.util.Date;

public class LogEntry {

	String info;
	Date date;
	NATlogger.Type type;
	
	public LogEntry(String info, Date date, NATlogger.Type type) {
		this.info = info;
		this.type = type;
		this.date = date;
	}
	
	
	public String getInfo() {
		return info;
	}
	
	public NATlogger.Type getType() {
		return type;
	}
	
	public Date getDate() {
		return date;
	}
}
