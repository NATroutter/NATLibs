package net.natroutter.natlibs.handlers.database;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import net.natroutter.natlibs.utilities.libs.FileHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class YamlDatabase {

    final JavaPlugin pl;
    String fileName = "Database.yml";
    final FileHandler fileManager;
    final FileHandler.Config data;

    public YamlDatabase(JavaPlugin pl) {
        this.pl = pl;
        this.fileManager = new FileHandler(pl);
        this.data = fileManager.getConfig(fileName);

        File file = new File(pl.getDataFolder(), fileName);
        if (!file.exists()) {
            data.copyDefaults(true).save();
            data.saveDefaultConfig();
        }

    }

    public YamlDatabase(JavaPlugin pl, String fileName) {
        this.pl = pl;
        this.fileName = fileName;
        this.fileManager = new FileHandler(pl);
        this.data = fileManager.getConfig(fileName);

        File file = new File(pl.getDataFolder(), fileName);
        if (!file.exists()) {
            data.copyDefaults(true).save();
            data.saveDefaultConfig();
        }
    }

    public YamlConfiguration get() {
        return data.get();
    }

    public void reload() {
        data.reload();
    }

    //Save data to config
    public void save(Object Identifier, String key, Object Value) {
        if (Identifier instanceof OfflinePlayer) {
            OfflinePlayer p = (OfflinePlayer)Identifier;
            data.get().set("PlayerData." + p.getUniqueId() + "." + key, Value);
        } else {
            data.get().set(Identifier + "." + key, Value);
        }
        data.save();
    }

    //Get config keys
    public Set<String> getKeys(Object Identifier, Object key) {
        return getKeys(Identifier + "." + key);
    }
    public Set<String> getKeys(String key) {
        if (data == null) {return null;}
        if (data.get() == null) {return null;}
        ConfigurationSection sect = data.get().getConfigurationSection(key);
        if (sect != null) {
            return sect.getKeys(false);
        }
        return null;
    }

    //Check if contains value
    public boolean valueExits(String value) {
        return data.get().contains(value);
    }

    //Save location to config
    public void saveLoc(Object Identifier, String key, Location loc) {
        if (Identifier instanceof OfflinePlayer) {
            OfflinePlayer p = (OfflinePlayer)Identifier;
            if (loc == null) {
                data.get().set("PlayerData." + p.getUniqueId() + "." + key + ".World", null);
                data.get().set("PlayerData." + p.getUniqueId() + "." + key + ".X", null);
                data.get().set("PlayerData." + p.getUniqueId() + "." + key + ".Y", null);
                data.get().set("PlayerData." + p.getUniqueId() + "." + key + ".Z", null);
                data.get().set("PlayerData." + p.getUniqueId() + "." + key + ".Pitch", null);
                data.get().set("PlayerData." + p.getUniqueId() + "." + key + ".Yaw", null);
            } else {
                data.get().set("PlayerData." + p.getUniqueId() + "." + key + ".World", loc.getWorld().getName());
                data.get().set("PlayerData." + p.getUniqueId() + "." + key + ".X", loc.getX());
                data.get().set("PlayerData." + p.getUniqueId() + "." + key + ".Y", loc.getY());
                data.get().set("PlayerData." + p.getUniqueId() + "." + key + ".Z", loc.getZ());
                data.get().set("PlayerData." + p.getUniqueId() + "." + key + ".Pitch", loc.getPitch());
                data.get().set("PlayerData." + p.getUniqueId() + "." + key + ".Yaw", loc.getYaw());
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
            if (Identifier instanceof OfflinePlayer) {
                OfflinePlayer p = (OfflinePlayer)Identifier;
                String world = data.get().getString("PlayerData." + p.getUniqueId() + "." + key + ".World");
                double X = data.get().getDouble("PlayerData." + p.getUniqueId() + "." + key + ".X");
                double Y = data.get().getDouble("PlayerData." + p.getUniqueId() + "." + key + ".Y");
                double Z = data.get().getDouble("PlayerData." + p.getUniqueId() + "." + key + ".Z");
                float Pitch = Float.parseFloat(data.get().getString("PlayerData." + p.getUniqueId() + "." + key + ".Pitch"));
                float Yaw = Float.parseFloat(data.get().getString("PlayerData." + p.getUniqueId() + "." + key + ".Yaw"));
                return new Location(Bukkit.getWorld(world), X, Y, Z, Yaw, Pitch);

            } else {
                String world = data.get().getString(Identifier + "." + key + ".World");
                double X = data.get().getDouble(Identifier + "." + key + ".X");
                double Y = data.get().getDouble(Identifier + "." + key + ".Y");
                double Z = data.get().getDouble(Identifier + "." + key + ".Z");
                float Pitch = Float.parseFloat(data.get().getString(Identifier + "." + key + ".Pitch"));
                float Yaw = Float.parseFloat(data.get().getString(Identifier + "." + key + ".Yaw"));
                return new Location(Bukkit.getWorld(world), X, Y, Z, Yaw, Pitch);
            }
        } catch (Exception e) {
            return null;
        }

    }

    //Get String List from configs
    public List<String> getStringList(Object Identifier, String key) {
        return data.get().getStringList(Identifier + "." + key);
    }

    //Get Boolean List from configs
    public List<Boolean> getBooleanList(Object Identifier, String key) {
        return data.get().getBooleanList(Identifier + "." + key);
    }

    //Get Byte List from configs
    public List<Byte> getByteList(Object Identifier, String key) {
        return data.get().getByteList(Identifier + "." + key);
    }

    //Get Character List from configs
    public List<Character> getCharacterList(Object Identifier, String key) {
        return data.get().getCharacterList(Identifier + "." + key);
    }

    //Get Double List from configs
    public List<Double> getDoubleList(Object Identifier, String key) {
        return data.get().getDoubleList(Identifier + "." + key);
    }

    //Get Float List from configs
    public List<Float> getFloatList(Object Identifier, String key) {
        return data.get().getFloatList(Identifier + "." + key);
    }

    //Get Integer List from configs
    public List<Integer> getIntegerList(Object Identifier, String key) {
        return data.get().getIntegerList(Identifier + "." + key);
    }

    //Get Long List from configs
    public List<Long> getLongList(Object Identifier, String key) {
        return data.get().getLongList(Identifier + "." + key);
    }

    //Get Map<?, ?> List from configs
    public List<Map<?,?>> getMapList(Object Identifier, String key) {
        return data.get().getMapList(Identifier + "." + key);
    }

    //Get Short List from configs
    public List<Short> getShortList(Object Identifier, String key) {
        return data.get().getShortList(Identifier + "." + key);
    }

    //Get ? List from configs
    public List<?> getList(Object Identifier, String key) {
        return data.get().getList(Identifier + "." + key);
    }


    //Get String from configs
    public String getString(Object Identifier, String key) {

        if (Identifier instanceof OfflinePlayer) {
            OfflinePlayer p = (OfflinePlayer)Identifier;
            return data.get().getString("PlayerData." + p.getUniqueId() + "." + key);
        } else {
            return data.get().getString(Identifier + "." + key);
        }

    }

    //Get Boolean from config
    public Boolean getBoolean(Object Identifier, String key) {

        if (Identifier instanceof OfflinePlayer) {
            OfflinePlayer p = (OfflinePlayer)Identifier;
            return data.get().getBoolean("PlayerData." + p.getUniqueId() + "." + key);
        } else {
            return data.get().getBoolean(Identifier + "." + key);
        }
    }

    //Get Integer from config
    public Integer getInt(Object Identifier, String key) {
        if (Identifier instanceof OfflinePlayer) {
            OfflinePlayer p = (OfflinePlayer)Identifier;
            return data.get().getInt("PlayerData." + p.getUniqueId() + "." + key);
        } else {
            return data.get().getInt(Identifier + "." + key);
        }
    }

    //Get Double from config
    public Double getDouble(Object Identifier, String key) {
        if (Identifier instanceof OfflinePlayer) {
            OfflinePlayer p = (OfflinePlayer)Identifier;
            return data.get().getDouble("PlayerData." + p.getUniqueId() + "." + key);
        } else {
            return data.get().getDouble(Identifier + "." + key);
        }
    }

    public Long getLong(Object Identifier, String key) {
        if (Identifier instanceof OfflinePlayer) {
            OfflinePlayer p = (OfflinePlayer)Identifier;
            return data.get().getLong("PlayerData." + p.getUniqueId() + "." + key);
        } else {
            return data.get().getLong(Identifier + "." + key);
        }
    }
}
