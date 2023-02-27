package fi.natroutter.natlibs.config;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

public class SimpleYml extends YamlConfiguration {

    private String name;
    private JavaPlugin plugin;
    private File file;
    private YamlConfiguration defaults;

    /**
     * @param plugin of the plugin
     * @param name of the file
     */
    public SimpleYml(JavaPlugin plugin, String name) {
        init(plugin, name);
    }

    public SimpleYml(JavaPlugin plugin, CfgType file) {
        init(plugin, file.getName());
    }

    private void init(JavaPlugin plugin, String name) {
        this.plugin = plugin;
        this.name = name;
        this.file = new File(plugin.getDataFolder(), name);
        options().parseComments(true);
        options().copyDefaults(true);
        options().copyHeader(true);
        reload();
        save();
    }

    /**
     * Gets default value from jars yml file.
     *
     * @param path for value
     * @return value
     */
    public <T> T getOrDefault(String path) {
        T value = (T) get(path);
        T defaultVal = (T) defaults.get(path);
        if (value == null && defaultVal == null) {
            Bukkit.getLogger().warning("§4["+plugin.getName()+"] §cMissing entry \""+path+"\" in file " + name);
        } else if (value == null) {
            Bukkit.getLogger().warning("§4["+plugin.getName()+"] §cMissing entry \""+path+"\" in file " + name + " Using default: " + defaultVal);
        }
        return value == null ? defaultVal : value;
    }

    /**
     * Gets from user set value.
     *
     * @param path for value
     * @return value
     */
    public <T> T getOrDefault(String path, T def) {
        T value = (T) get(path);
        return value == null ? def : value;
    }

    //* Reloads config from file
    public void save() {
        try {
            save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //* Reloads config from file
    public void reload() {
        //Reloads loads file and sets defaults from source
        try {
            Files.createParentDirs(file);
            //noinspection ResultOfMethodCallIgnored
            file.createNewFile();
            load(file);
            InputStream defaultStream = plugin.getResource(file.getName());
            if (defaultStream != null) {
                //todo switch to use Files.bufferedreader
                defaults = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream, Charsets.UTF_8));
                //Copies header
                options().setHeader(defaults.options().getHeader());
                //Copies default values which havent been added yet & comments
                setDefaults(defaults);

            }
        } catch (IOException | InvalidConfigurationException ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Cannot load " + file, ex);
        }
    }

}

