package net.natroutter.natlibs.utilities.libs.NATlogger;

public class LoggerSettings {

	//Constructor variables
	private String path;
	private Integer interval;
	private Integer pruneInterval;
	private Boolean silent;
	
	public LoggerSettings(String logFolderPath, Integer SaveInterval, Integer pruneOlderThanDays, boolean silent) {
		this.path = logFolderPath;
		this.interval = SaveInterval;
		this.pruneInterval = pruneOlderThanDays;
		this.silent = silent;
	}
	
	public Integer getInterval() {
		return interval;
	}
	
	public String getPath() {
		return path;
	}
	
	public Integer getPruneInterval() {
		return pruneInterval;
	}
	
	public Boolean getSilent() {
		return silent;
	}
	
}
