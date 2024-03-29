package fi.natroutter.natlibs.configuration;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;

public class SimpleYml extends YamlConfiguration {

    private Class clazz;
    private File file;
    private YamlConfiguration defaults;
    private String resourceLocation;


    /**
     * @param file of the file
     */
    public SimpleYml(Class clazz, File file) {
        this.clazz = clazz;
        this.file = file;
        this.resourceLocation = "/"+file.getName();
        options().parseComments(true);
        options().copyDefaults(true);
        options().copyHeader(true);
        reload();
        save();
    }

    public SimpleYml(Class clazz, File file, String resourceLocation) {
        this.clazz = clazz;
        this.file = file;
        this.resourceLocation = resourceLocation.startsWith("/") ? resourceLocation : "/" + resourceLocation;
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
            Bukkit.getLogger().warning("§4["+clazz.getSimpleName()+"] §cMissing entry \""+path+"\" in file " + file.getName());
        } else if (value == null) {
            Bukkit.getLogger().warning("§4["+clazz.getSimpleName()+"] §cMissing entry \""+path+"\" in file " + file.getName() + " Using default: " + defaultVal);
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


    public InputStream getResource(String filename) {
        try {
            URL url = clazz.getResource(filename);
            if (url == null) {
                return null;
            } else {
                URLConnection connection = url.openConnection();
                connection.setUseCaches(false);
                return connection.getInputStream();
            }
        } catch (IOException var4) {
            return null;
        }
    }

    //* Reloads config from file
    public void reload() {
        //Reloads loads file and sets defaults from source

        try {
            Files.createParentDirs(file);
            file.createNewFile();

            load(file);
            InputStream defaultStream = getResource(resourceLocation);

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

