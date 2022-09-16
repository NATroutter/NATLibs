package net.natroutter.natlibs.utilities.libs.natlogger;

public class LoggerSettings {

	//Constructor variables
	private final String path;
	private final Integer interval;
	private final Integer pruneInterval;
	private final Boolean silent;
	
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
