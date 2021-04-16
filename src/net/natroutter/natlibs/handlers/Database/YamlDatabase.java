package net.natroutter.natlibs.handlers.Database;

import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import net.natroutter.natlibs.utilities.libs.FileManager;
import org.bukkit.plugin.java.JavaPlugin;

public class YamlDatabase {

    JavaPlugin pl;
    String fileName = "Database.yml";
    FileManager fileManager;

    public YamlDatabase(JavaPlugin pl) {
        this.pl = pl;
        this.fileManager = new FileManager(pl);
        fileManager.getConfig(fileName).copyDefaults(true).save();
    }

    public YamlDatabase(JavaPlugin pl, String fileName) {
        this.pl = pl;
        this.fileName = fileName;
        this.fileManager = new FileManager(pl);
        fileManager.getConfig(fileName).copyDefaults(true).save();
    }

    public void reload() {
        fileManager.getConfig(fileName).reload();
    }

    //Save data to config
    public void save(Object Identifier, String key, Object Value) {
        if (Identifier instanceof Player || Identifier instanceof OfflinePlayer) {
            OfflinePlayer p = (OfflinePlayer)Identifier;
            fileManager.getConfig(fileName).get().set("PlayerData." + p.getUniqueId().toString() + "." + key, Value);
            fileManager.getConfig(fileName).save();
        } else {
            fileManager.getConfig(fileName).get().set(Identifier + "." + key, Value);
            fileManager.getConfig(fileName).save();
        }
    }

    //Get config keys
    public Set<String> getKeys(String key) {
        return fileManager.getConfig(fileName).get().getConfigurationSection(key).getKeys(false);
    }

    //Check if contains value
    public boolean valueExits(String value) {
        if (fileManager.getConfig(fileName).get().contains(value)) {
            return true;
        } else {
            return false;
        }
    }

    //Save location to config
    public void saveLoc(Object Identifier, String key, Location loc) {
        if (Identifier instanceof Player || Identifier instanceof OfflinePlayer) {
            OfflinePlayer p = (OfflinePlayer)Identifier;
            if (loc == null) {
                fileManager.getConfig(fileName).get().set("PlayerData." + p.getUniqueId().toString() + "." + key + ".World", null);
                fileManager.getConfig(fileName).get().set("PlayerData." + p.getUniqueId().toString() + "." + key + ".X", null);
                fileManager.getConfig(fileName).get().set("PlayerData." + p.getUniqueId().toString() + "." + key + ".Y", null);
                fileManager.getConfig(fileName).get().set("PlayerData." + p.getUniqueId().toString() + "." + key + ".Z", null);
                fileManager.getConfig(fileName).get().set("PlayerData." + p.getUniqueId().toString() + "." + key + ".Pitch", null);
                fileManager.getConfig(fileName).get().set("PlayerData." + p.getUniqueId().toString() + "." + key + ".Yaw", null);
            } else {
                fileManager.getConfig(fileName).get().set("PlayerData." + p.getUniqueId().toString() + "." + key + ".World", loc.getWorld().getName());
                fileManager.getConfig(fileName).get().set("PlayerData." + p.getUniqueId().toString() + "." + key + ".X", loc.getX());
                fileManager.getConfig(fileName).get().set("PlayerData." + p.getUniqueId().toString() + "." + key + ".Y", loc.getY());
                fileManager.getConfig(fileName).get().set("PlayerData." + p.getUniqueId().toString() + "." + key + ".Z", loc.getZ());
                fileManager.getConfig(fileName).get().set("PlayerData." + p.getUniqueId().toString() + "." + key + ".Pitch", loc.getPitch());
                fileManager.getConfig(fileName).get().set("PlayerData." + p.getUniqueId().toString() + "." + key + ".Yaw", loc.getYaw());
            }
            fileManager.getConfig(fileName).save();
        } else {
            if (loc == null) {
                fileManager.getConfig(fileName).get().set(Identifier + "." + key + ".World", null);
                fileManager.getConfig(fileName).get().set(Identifier + "." + key + ".X", null);
                fileManager.getConfig(fileName).get().set(Identifier + "." + key + ".Y", null);
                fileManager.getConfig(fileName).get().set(Identifier + "." + key + ".Z", null);
                fileManager.getConfig(fileName).get().set(Identifier + "." + key + ".Pitch", null);
                fileManager.getConfig(fileName).get().set(Identifier + "." + key + ".Yaw", null);
            } else {
                fileManager.getConfig(fileName).get().set(Identifier + "." + key + ".World", loc.getWorld().getName());
                fileManager.getConfig(fileName).get().set(Identifier + "." + key + ".X", loc.getX());
                fileManager.getConfig(fileName).get().set(Identifier + "." + key + ".Y", loc.getY());
                fileManager.getConfig(fileName).get().set(Identifier + "." + key + ".Z", loc.getZ());
                fileManager.getConfig(fileName).get().set(Identifier + "." + key + ".Pitch", loc.getPitch());
                fileManager.getConfig(fileName).get().set(Identifier + "." + key + ".Yaw", loc.getYaw());
            }
            fileManager.getConfig(fileName).save();
        }
    }

    //Get Location from config
    public Location getLocation(Object Identifier, String key) {

        try {
            if (Identifier instanceof Player || Identifier instanceof OfflinePlayer) {
                OfflinePlayer p = (OfflinePlayer)Identifier;
                String world = fileManager.getConfig(fileName).get().getString("PlayerData." + p.getUniqueId().toString() + "." + key + ".World");
                Double X = fileManager.getConfig(fileName).get().getDouble("PlayerData." + p.getUniqueId().toString() + "." + key + ".X");
                Double Y = fileManager.getConfig(fileName).get().getDouble("PlayerData." + p.getUniqueId().toString() + "." + key + ".Y");
                Double Z = fileManager.getConfig(fileName).get().getDouble("PlayerData." + p.getUniqueId().toString() + "." + key + ".Z");
                Float Pitch = Float.valueOf(fileManager.getConfig(fileName).get().getString("PlayerData." + p.getUniqueId().toString() + "." + key + ".Pitch"));
                Float Yaw = Float.valueOf(fileManager.getConfig(fileName).get().getString("PlayerData." + p.getUniqueId().toString() + "." + key + ".Yaw"));
                Location getloc = new Location(Bukkit.getWorld(world), X, Y, Z, Yaw, Pitch);
                return getloc;

            } else {
                String world = fileManager.getConfig(fileName).get().getString(Identifier + "." + key + ".World");
                Double X = fileManager.getConfig(fileName).get().getDouble(Identifier + "." + key + ".X");
                Double Y = fileManager.getConfig(fileName).get().getDouble(Identifier + "." + key + ".Y");
                Double Z = fileManager.getConfig(fileName).get().getDouble(Identifier + "." + key + ".Z");
                Float Pitch = Float.valueOf(fileManager.getConfig(fileName).get().getString(Identifier + "." + key + ".Pitch"));
                Float Yaw = Float.valueOf(fileManager.getConfig(fileName).get().getString(Identifier + "." + key + ".Yaw"));
                Location getloc = new Location(Bukkit.getWorld(world), X, Y, Z, Yaw, Pitch);
                return getloc;
            }
        } catch (Exception e) {
            return null;
        }

    }

    //Get List from configs
    public List<?> getList(Object Identifier, String key) {
        return fileManager.getConfig(fileName).get().getList(Identifier + "." + key);
    }

    //Get String from configs
    public String getString(Object Identifier, String key) {

        if (Identifier instanceof Player || Identifier instanceof OfflinePlayer) {
            OfflinePlayer p = (OfflinePlayer)Identifier;
            return fileManager.getConfig(fileName).get().getString("PlayerData." + p.getUniqueId().toString() + "." + key);
        } else {
            return fileManager.getConfig(fileName).get().getString(Identifier + "." + key);
        }

    }

    //Get Boolean from config
    public Boolean getBoolean(Object Identifier, String key) {

        if (Identifier instanceof Player || Identifier instanceof OfflinePlayer) {
            OfflinePlayer p = (OfflinePlayer)Identifier;
            return fileManager.getConfig(fileName).get().getBoolean("PlayerData." + p.getUniqueId().toString() + "." + key);
        } else {
            return fileManager.getConfig(fileName).get().getBoolean(Identifier + "." + key);
        }
    }

    //Get Integer from config
    public Integer getInt(Object Identifier, String key) {
        if (Identifier instanceof Player || Identifier instanceof OfflinePlayer) {
            OfflinePlayer p = (OfflinePlayer)Identifier;
            return fileManager.getConfig(fileName).get().getInt("PlayerData." + p.getUniqueId().toString() + "." + key);
        } else {
            return fileManager.getConfig(fileName).get().getInt(Identifier + "." + key);
        }
    }

    //Get Double from config
    public Double getDouble(Object Identifier, String key) {
        if (Identifier instanceof Player || Identifier instanceof OfflinePlayer) {
            OfflinePlayer p = (OfflinePlayer)Identifier;
            return fileManager.getConfig(fileName).get().getDouble("PlayerData." + p.getUniqueId().toString() + "." + key);
        } else {
            return fileManager.getConfig(fileName).get().getDouble(Identifier + "." + key);
        }
    }

    public Long getLong(Object Identifier, String key) {
        if (Identifier instanceof Player || Identifier instanceof OfflinePlayer) {
            OfflinePlayer p = (OfflinePlayer)Identifier;
            return fileManager.getConfig(fileName).get().getLong("PlayerData." + p.getUniqueId().toString() + "." + key);
        } else {
            return fileManager.getConfig(fileName).get().getLong(Identifier + "." + key);
        }
    }
}
