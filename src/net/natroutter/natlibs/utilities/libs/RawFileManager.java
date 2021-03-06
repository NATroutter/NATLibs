package net.natroutter.natlibs.utilities.libs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import org.bukkit.plugin.java.JavaPlugin;

public class RawFileManager {
    
    private File rawFile;
    private File Directory;
    private Boolean FileCreated = false;
    private JavaPlugin pl;
    
    public RawFileManager(JavaPlugin pl, String configName) {
    	this.pl = pl;
    	this.rawFile = new File(pl.getDataFolder().getAbsolutePath(), configName);
    	this.Directory = new File(pl.getDataFolder().getAbsolutePath());
    	init();
    }

    public RawFileManager(JavaPlugin pl, File rawFile) {
        this.pl = pl;
    	this.rawFile = rawFile;
        this.Directory = new File(pl.getDataFolder().getAbsolutePath());
    	init();
    }
    
    private void init() {
    	try {
    		if (!rawFile.exists()) {
    			Directory.mkdirs();
    			rawFile.createNewFile();
    			FileCreated = true;
        	}
		} catch (IOException e) {e.printStackTrace();}
    }
    
    public File getFile() {
		return rawFile;
	}
    
    public Boolean getFileCreated() {
		return FileCreated;
	}

    public String readFile() {
        try {
        	BufferedReader br = new BufferedReader(new FileReader(rawFile));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }

            String Content = sb.toString();
            br.close();
            return Content;
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return null;
    }

    public boolean writeFile(String content) { return writeFile(content, true); }
    public boolean writeFile(String content, Boolean override) {
        try {
            if (!rawFile.exists()) {
            	Directory.mkdirs();
            	rawFile.createNewFile();
            }

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(rawFile), StandardCharsets.UTF_8));

            if (!override) {
                String Old = readFile();
                bw.write(Old + content);
            } else {
                bw.write(content);
            }
            bw.close();
            return true;
        } catch (Exception e) {e.printStackTrace();}
        return false;
    }
    
   
	
}
