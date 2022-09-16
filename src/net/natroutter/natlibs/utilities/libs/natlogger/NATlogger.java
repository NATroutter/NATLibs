package net.natroutter.natlibs.utilities.libs.natlogger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class NATlogger {
	//Log Type list
	protected enum Type {
		ERROR, WARN, INFO, NONE
	}
	
	//Constructor variables
	String path;
	Integer interval;
	Integer pruneInterval;
	boolean silent;
	boolean SaveMode;
	
	//Variables
	Timer timer;
	File logFolder; 
	ArrayList<LogEntry> logEntries;
		
	//Constructors
	public NATlogger(String logFolderPath, Integer SaveInterval, Integer pruneOlderThanDays, boolean silent) {
		this.path = logFolderPath + "/logs/";
		this.interval = SaveInterval;
		this.pruneInterval = pruneOlderThanDays;
		this.silent = silent;
		SaveMode = true;
		InitLogger();
	}
	
	public NATlogger(LoggerSettings set) {
		this.path = set.getPath() + "/logs/";
		this.interval = set.getInterval();
		this.pruneInterval = set.getPruneInterval();
		this.silent = set.getSilent();
		SaveMode = true;
		InitLogger();
	}
	
	public NATlogger() {
		SaveMode = false;
	}
	
	
	
	
	private void InitLogger() {
		this.timer = new Timer();
		long timerInterval = this.interval * 1000;
		this.timer.schedule(LogSaveTask, timerInterval, timerInterval);
		this.logFolder = new File(this.path);
		logEntries = new ArrayList<LogEntry>();
	}
	
	
	//Log saving timer
	private final TimerTask LogSaveTask = new TimerTask() {
	    @Override
	    public void run() {
	       prune();
	       save();
	    }	
	};
	
	public void error(String log) {Addlog(log, true, Type.ERROR);}
	public void warn(String log) {Addlog(log, true, Type.ERROR);}
	public void info(String log) {Addlog(log, true, Type.ERROR);}
	public void none(String log) {Addlog(log, true, Type.NONE);}
	
	public void error(String log, boolean Broadcast) {Addlog(log, Broadcast, Type.ERROR);}
	public void warn(String log, boolean Broadcast) {Addlog(log, Broadcast, Type.ERROR);}
	public void info(String log, boolean Broadcast) {Addlog(log, Broadcast, Type.ERROR);}
	public void none(String log, boolean Broadcast) {Addlog(log, Broadcast, Type.NONE);}
	
	
	private void Addlog(String log, boolean Broadcast, Type type) {
		Date date = new Date();
		
		if (SaveMode) {
			logEntries.add(new LogEntry(log, date, type));
		}
		
		if (Broadcast) {
			if (type == Type.NONE) {
				System.out.println("[" +TimeStampFormat(date)+ "] " + log);
			} else {
				System.out.println("[" +TimeStampFormat(date)+ "][" +type+ "] " + log);
			}
		}
	}
		
	
	public void save() {
		if (!SaveMode) {return;}
		
		if (logEntries.isEmpty()) {
        	return;
        }
		
		String timeStamp = TimeStampFormat(new Date());
		if (!silent) {
			System.out.println("[NATLogger]["+timeStamp+"] Saving logs...");
		}
		long start = System.currentTimeMillis();
		
		try {
			if (!logFolder.exists()) {
            	logFolder.mkdir();
            }

            ZonedDateTime now = ZonedDateTime.now();
            String fileName = "Log_" + now.getDayOfMonth() + "-" + now.getMonthValue() + "-" + now.getYear() + ".log";
            File saveTo = new File(logFolder, fileName);
            
            if (!saveTo.exists()) {
                saveTo.createNewFile();
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(saveTo, true));
            
            int SaveCount = 0;
			for (LogEntry log : logEntries) {

				String timeStamp2 = TimeStampFormat(log.date);

				String logString = "[" + timeStamp2 + "][" + log.getType().name() + "] " + log.getInfo();

				if (log.getType().equals(Type.NONE)) {
					logString = "[" + timeStamp2 + "] " + log.getInfo();
				}

				writer.append(logString);
				writer.newLine();

				SaveCount++;
			}
            logEntries.clear();
          
            writer.flush();
            writer.close();
            	
            long timeTook = System.currentTimeMillis() - start;
            
            if (!silent) {
            	System.out.println("[NATLogger]["+timeStamp+"] Saved " + SaveCount + " lines to logs in " + timeTook + "ms");
            }	
            
        } catch (IOException e) {
        	System.out.println("[NATLogger]["+timeStamp+"] Failed write log file: " + e.getStackTrace());
        }
    }
	
    
    
    
	public void prune() {
		if (!SaveMode) {return;}
		
		String timeStamp = TimeStampFormat(new Date());
		
		if (pruneInterval < 0) {
			if (!silent) {
        		System.out.println("[NATLogger]["+timeStamp+"] Log file pruning disable!");
        		return;
    		}
		}
		
		long start = System.currentTimeMillis();
		
    	try {
    		
    		if (!logFolder.exists()) {
        		logFolder.mkdirs();
        	}
        	
    		int pruneCount = 0;
        	for (File file : logFolder.listFiles()) {
        		if (file.isDirectory()) { continue; }
        		if (!file.getName().endsWith(".log")) { continue; }
        		
        		String fileName = file.getName();
        		String filenameNoExt = fileName.substring(0, fileName.length() - 4);
        		String dateString = filenameNoExt.split("_")[1];
        		
        	
        		Date date = new SimpleDateFormat("dd-MM-yyyy").parse(dateString);
        		ZonedDateTime Ago = ZonedDateTime.now().plusDays(-pruneInterval);
        		if (date.toInstant().isBefore(Ago.toInstant())) {
        			file.delete();
        			pruneCount++;
        		}
        	}
        	
        	long timeTook = System.currentTimeMillis() - start;
        	if (pruneCount > 0 ) {
        		if (!silent) {
        			System.out.println("[NATLogger]["+timeStamp+"] Pruning logs...");
            		System.out.println("[NATLogger]["+timeStamp+"] Pruned " + pruneCount + " old log files in " + timeTook + "ms");
        		}
        	}
        	
		} catch (Exception e) {
			System.out.println("[NATLogger]["+timeStamp+"] Failed to pruned log files: " + e.getStackTrace());
		}
    }
	
	private String TimeStampFormat(Date date) {
		return new SimpleDateFormat("dd.MM.yyyy-HH:mm:ss").format(date);
	}
	
}
