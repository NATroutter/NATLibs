package fi.natroutter.natlibs.utilities.natlogger;

import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;

public class NATlogger {

	protected enum TYPE {ERROR,INFO,WARN,NONE}

	private JavaPlugin plugin;
	private String path;
	private int pruneOlderThanDays;
	private boolean consoleLog;
	private boolean saveLogs;
	@Setter private String logTimeFormat = "dd.MM.yyyy-HH:mm:ss";
	
	//Variables
	private File logFolder ;
	private ArrayList<LogEntry> logEntries = new ArrayList<>();
		
	//Constructors
	public NATlogger(JavaPlugin plugin, int SaveIntervalSec, int pruneOlderThanDays, boolean consoleLog, boolean saveLogs) {
		this.plugin = plugin;
		this.path = plugin.getDataFolder().getAbsolutePath() + "/logs/";
		this.logFolder = new File(path);
		this.pruneOlderThanDays = pruneOlderThanDays;
		this.consoleLog = consoleLog;
		this.saveLogs = saveLogs;

		int interval = SaveIntervalSec * 20;
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, ()->{
			save();
			prune();
		}, interval, interval);
	}

	public void error(String log) {Addlog(log, TYPE.ERROR);}
	public void warn(String log) {Addlog(log, TYPE.WARN);}
	public void info(String log) {Addlog(log, TYPE.INFO);}
	public void none(String log) {Addlog(log, TYPE.NONE);}
	
	private void send(String msg, TYPE type) {
		switch (type) {
			case NONE -> plugin.getLogger().log(Level.ALL, msg);
			case INFO -> plugin.getLogger().info(msg);
			case WARN -> plugin.getLogger().warning(msg);
			case ERROR -> plugin.getLogger().log(Level.ALL, "[ERROR] " + msg);
		}
	}

	private void Addlog(String log, TYPE type) {
		Date date = new Date();
		if (saveLogs) {
			logEntries.add(new LogEntry(log, date, type));
		}
		if (consoleLog) {
			send(log, type);
		}
	}

	public void close() {
		save();
		prune();
	}
	
	private void save() {
		if (!saveLogs) {return;}
		if (logEntries.isEmpty()) {return;}
		
		String timeStamp = TimeStampFormat(new Date());
		if (consoleLog) {
			send("Saving logs...", TYPE.INFO);
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

				if (log.getType().equals(TYPE.NONE)) {
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
            
            if (consoleLog) {
            	send("Saved " + SaveCount + " lines to logs in " + timeTook + "ms", TYPE.INFO);
            }	
            
        } catch (IOException e) {
        	send("Failed write log file: " + e.getStackTrace(), TYPE.ERROR);
        }
    }

	private void prune() {
		if (!saveLogs) {return;}

		String timeStamp = TimeStampFormat(new Date());

		if (pruneOlderThanDays < 0) {
			if (consoleLog) {
        		send("Log file pruning disable!", TYPE.INFO);
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
        		ZonedDateTime Ago = ZonedDateTime.now().plusDays(-pruneOlderThanDays);
        		if (date.toInstant().isBefore(Ago.toInstant())) {
        			file.delete();
        			pruneCount++;
        		}
        	}
        	
        	long timeTook = System.currentTimeMillis() - start;
        	if (pruneCount > 0 ) {
        		if (consoleLog) {
        			send("Pruning logs...", TYPE.INFO);
            		send("Pruned " + pruneCount + " old log files in " + timeTook + "ms", TYPE.INFO);
        		}
        	}
        	
		} catch (Exception e) {
			send("Failed to pruned log files: " + e.getMessage(), TYPE.ERROR);
		}
    }

	private String TimeStampFormat(Date date) {
		return new SimpleDateFormat(logTimeFormat).format(date);
	}

}
