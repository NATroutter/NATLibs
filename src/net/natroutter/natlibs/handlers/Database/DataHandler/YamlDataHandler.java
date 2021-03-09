package net.natroutter.natlibs.handlers.Database.DataHandler;

import net.natroutter.natlibs.utilities.libs.FileManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Simple yaml file handler for handling
 * saving and receiving data from and to yaml files
 */
public class YamlDataHandler {

    String fileName = "Database.yaml";
    FileManager fileManager;

    /**
     * Constructor for creating new YamlDataHandler
     * with default file name "Database.yaml"
     *
     * @param  pl      JavaPlugin instance
     */
    public YamlDataHandler(JavaPlugin pl) {
        this.fileManager = new FileManager(pl);
        this.fileManager.getConfig(fileName).copyDefaults(true).save();
    }

    /**
     * Constructor for creating new YamlDataHandler
     * with option to select file name
     *
     * @param  pl       JavaPlugin instance
     * @param  fileName yaml filename (must end with .yaml)
     */
    public YamlDataHandler(JavaPlugin pl, String fileName) {
        this.fileManager = new FileManager(pl);;
        this.fileManager.getConfig(fileName).copyDefaults(true).save();
        this.fileName = fileName;
    }

    /**
     * Method for saving data to yaml file
     *
     * @param  key  key for identifying where to get data
     * @param value value what to save into yaml file
     */
    public void save(String key, String value) {
        fileManager.getConfig(fileName).set(key, value);
        fileManager.getConfig(fileName).save();
    }

    /**
     * Method for getting data from yaml file
     *
     * @param  key  key for identifying where to get data
     * @return      returns object from yaml file!
     */
    public Object get(String key) {
        return fileManager.getConfig(fileName).get(key);
    }

    /**
     * Method for reloading yaml file
     */
    public void reload() {
        fileManager.getConfig(fileName).reload();
    }

}
