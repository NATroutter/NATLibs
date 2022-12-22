package fi.natroutter.natlibs.utilities.natlogger;

import java.util.Date;

public class LogEntry {

	final String info;
	final Date date;
	final NATlogger.TYPE type;
	
	public LogEntry(String info, Date date, NATlogger.TYPE type) {
		this.info = info;
		this.type = type;
		this.date = date;
	}
	
	
	public String getInfo() {
		return info;
	}
	
	public NATlogger.TYPE getType() {
		return type;
	}
	
	public Date getDate() {
		return date;
	}
}
