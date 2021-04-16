package net.natroutter.natlibs.handlers.Database;

import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import net.natroutter.natlibs.utilities.libs.FileManager;
import org.bukkit.plugin.java.JavaPlugin;

public class YamlDatabase {

    JavaPlugin pl;
    String fileName = "Database.yml";
    FileManager fileManager;
    FileManager.Config data;

    public YamlDatabase(JavaPlugin pl) {
        this.pl = pl;
        this.fileManager = new FileManager(pl);
        this.data = data;

        data.copyDefaults(true).save();
    }

    public YamlDatabase(JavaPlugin pl, String fileName) {
        this.pl = pl;
        this.fileName = fileName;
        this.fileManager = new FileManager(pl);
        this.data = data;

        data.copyDefaults(true).save();
    }

    public void reload() {
        data.reload();
    }

    //Save data to config
    public void save(Object Identifier, String key, Object Value) {
        if (Identifier instanceof Player || Identifier instanceof OfflinePlayer) {
            OfflinePlayer p = (OfflinePlayer)Identifier;
            data.get().set("PlayerData." + p.getUniqueId().toString() + "." + key, Value);
        } else {
            data.get().set(Identifier + "." + key, Value);
        }
        data.save();
    }

    //Get config keys
    public Set<String> getKeys(String key) {
        return data.get().getConfigurationSection(key).getKeys(false);
    }

    //Check if contains value
    public boolean valueExits(String value) {
        if (data.get().contains(value)) {
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
                data.get().set("PlayerData." + p.getUniqueId().toString() + "." + key + ".World", null);
                data.get().set("PlayerData." + p.getUniqueId().toString() + "." + key + ".X", null);
                data.get().set("PlayerData." + p.getUniqueId().toString() + "." + key + ".Y", null);
                data.get().set("PlayerData." + p.getUniqueId().toString() + "." + key + ".Z", null);
                data.get().set("PlayerData." + p.getUniqueId().toString() + "." + key + ".Pitch", null);
                data.get().set("PlayerData." + p.getUniqueId().toString() + "." + key + ".Yaw", null);
            } else {
                data.get().set("PlayerData." + p.getUniqueId().toString() + "." + key + ".World", loc.getWorld().getName());
                data.get().set("PlayerData." + p.getUniqueId().toString() + "." + key + ".X", loc.getX());
                data.get().set("PlayerData." + p.getUniqueId().toString() + "." + key + ".Y", loc.getY());
                data.get().set("PlayerData." + p.getUniqueId().toString() + "." + key + ".Z", loc.getZ());
                data.get().set("PlayerData." + p.getUniqueId().toString() + "." + key + ".Pitch", loc.getPitch());
                data.get().set("PlayerData." + p.getUniqueId().toString() + "." + key + ".Yaw", loc.getYaw());
            }
            data.save();
        } else {
            if (loc == null) {
                data.get().set(Identifier + "." + key + ".World", null);
                data.get().set(Identifier + "." + key + ".X", null);
                data.get().set(Identifier + "." + key + ".Y", null);
                data.get().set(Identifier + "." + key + ".Z", null);
                data.get().set(Identifier + "." + key + ".Pitch", null);
                data.get().set(Identifier + "." + key + ".Yaw", null);
            } else {
                data.get().set(Identifier + "." + key + ".World", loc.getWorld().getName());
                data.get().set(Identifier + "." + key + ".X", loc.getX());
                data.get().set(Identifier + "." + key + ".Y", loc.getY());
                data.get().set(Identifier + "." + key + ".Z", loc.getZ());
                data.get().set(Identifier + "." + key + ".Pitch", loc.getPitch());
                data.get().set(Identifier + "." + key + ".Yaw", loc.getYaw());
            }
            data.save();
        }
    }

    //Get Location from config
    public Location getLocation(Object Identifier, String key) {

        try {
            if (Identifier instanceof Player || Identifier instanceof OfflinePlayer) {
                OfflinePlayer p = (OfflinePlayer)Identifier;
                String world = data.get().getString("PlayerData." + p.getUniqueId().toString() + "." + key + ".World");
                Double X = data.get().getDouble("PlayerData." + p.getUniqueId().toString() + "." + key + ".X");
                Double Y = data.get().getDouble("PlayerData." + p.getUniqueId().toString() + "." + key + ".Y");
                Double Z = data.get().getDouble("PlayerData." + p.getUniqueId().toString() + "." + key + ".Z");
                Float Pitch = Float.valueOf(data.get().getString("PlayerData." + p.getUniqueId().toString() + "." + key + ".Pitch"));
                Float Yaw = Float.valueOf(data.get().getString("PlayerData." + p.getUniqueId().toString() + "." + key + ".Yaw"));
                Location getloc = new Location(Bukkit.getWorld(world), X, Y, Z, Yaw, Pitch);
                return getloc;

            } else {
                String world = data.get().getString(Identifier + "." + key + ".World");
                Double X = data.get().getDouble(Identifier + "." + key + ".X");
                Double Y = data.get().getDouble(Identifier + "." + key + ".Y");
                Double Z = data.get().getDouble(Identifier + "." + key + ".Z");
                Float Pitch = Float.valueOf(data.get().getString(Identifier + "." + key + ".Pitch"));
                Float Yaw = Float.valueOf(data.get().getString(Identifier + "." + key + ".Yaw"));
                Location getloc = new Location(Bukkit.getWorld(world), X, Y, Z, Yaw, Pitch);
                return getloc;
            }
        } catch (Exception e) {
            return null;
        }

    }

    //Get List from configs
    public List<?> getList(Object Identifier, String key) {
        return data.get().getList(Identifier + "." + key);
    }

    //Get String from configs
    public String getString(Object Identifier, String key) {

        if (Identifier instanceof Player || Identifier instanceof OfflinePlayer) {
            OfflinePlayer p = (OfflinePlayer)Identifier;
            return data.get().getString("PlayerData." + p.getUniqueId().toString() + "." + key);
        } else {
            return data.get().getString(Identifier + "." + key);
        }

    }

    //Get Boolean from config
    public Boolean getBoolean(Object Identifier, String key) {

        if (Identifier instanceof Player || Identifier instanceof OfflinePlayer) {
            OfflinePlayer p = (OfflinePlayer)Identifier;
            return data.get().getBoolean("PlayerData." + p.getUniqueId().toString() + "." + key);
        } else {
            return data.get().getBoolean(Identifier + "." + key);
        }
    }

    //Get Integer from config
    public Integer getInt(Object Identifier, String key) {
        if (Identifier instanceof Player || Identifier instanceof OfflinePlayer) {
            OfflinePlayer p = (OfflinePlayer)Identifier;
            return data.get().getInt("PlayerData." + p.getUniqueId().toString() + "." + key);
        } else {
            return data.get().getInt(Identifier + "." + key);
        }
    }

    //Get Double from config
    public Double getDouble(Object Identifier, String key) {
        if (Identifier instanceof Player || Identifier instanceof OfflinePlayer) {
            OfflinePlayer p = (OfflinePlayer)Identifier;
            return data.get().getDouble("PlayerData." + p.getUniqueId().toString() + "." + key);
        } else {
            return data.get().getDouble(Identifier + "." + key);
        }
    }

    public Long getLong(Object Identifier, String key) {
        if (Identifier instanceof Player || Identifier instanceof OfflinePlayer) {
            OfflinePlayer p = (OfflinePlayer)Identifier;
            return data.get().getLong("PlayerData." + p.getUniqueId().toString() + "." + key);
        } else {
            return data.get().getLong(Identifier + "." + key);
        }
    }
}
