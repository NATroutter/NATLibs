package fi.natroutter.natlibs.handlers.database;

import java.util.List;
import java.util.Map;
import java.util.Set;

import fi.natroutter.natlibs.config.SimpleYml;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

public class YamlDatabase {

    final JavaPlugin pl;
    final SimpleYml yml;

    public YamlDatabase(JavaPlugin pl) {
        this.pl = pl;
        this.yml = new SimpleYml(pl, "Database.yml");
    }
    
    public void reload() {
        yml.reload();
    }

    //Save data to config
    public void save(Object Identifier, String key, Object value) {
        if (value instanceof Location loc) {
            if (Identifier instanceof OfflinePlayer p) {
                yml.set("PlayerData." + p.getUniqueId() + "." + key + ".World", loc.getWorld().getName());
                yml.set("PlayerData." + p.getUniqueId() + "." + key + ".X", loc.getX());
                yml.set("PlayerData." + p.getUniqueId() + "." + key + ".Y", loc.getY());
                yml.set("PlayerData." + p.getUniqueId() + "." + key + ".Z", loc.getZ());
                yml.set("PlayerData." + p.getUniqueId() + "." + key + ".Pitch", loc.getPitch());
                yml.set("PlayerData." + p.getUniqueId() + "." + key + ".Yaw", loc.getYaw());
            } else {
                yml.set(Identifier + "." + key + ".World", loc.getWorld().getName());
                yml.set(Identifier + "." + key + ".X", loc.getX());
                yml.set(Identifier + "." + key + ".Y", loc.getY());
                yml.set(Identifier + "." + key + ".Z", loc.getZ());
                yml.set(Identifier + "." + key + ".Pitch", loc.getPitch());
                yml.set(Identifier + "." + key + ".Yaw", loc.getYaw());
            }
        } else {
            if (Identifier instanceof OfflinePlayer p) {
                yml.set("PlayerData." + p.getUniqueId() + "." + key, value);
            } else {
                yml.set(Identifier + "." + key, value);
            }
        }
        yml.save();
    }

    //Get config keys
    public Set<String> getKeys(Object Identifier, Object key) {
        return getKeys(Identifier + "." + key);
    }
    public @Nullable Set<String> getKeys(String key) {
        ConfigurationSection sect = yml.getConfigurationSection(key);
        if (sect != null) {
            return sect.getKeys(false);
        }
        return null;
    }

    //Check if contains value
    public boolean valueExists(String value) {
        return yml.contains(value);
    }

    public boolean valueExists(Object Identifier, String key) {
        if (Identifier instanceof OfflinePlayer p) {
            return yml.contains("PlayerData." + p.getUniqueId() + "." + key);
        } else {
            return yml.contains(Identifier + "." + key);
        }
    }

    //Get Location from config
    @Nullable
    public Location getLocation(Object Identifier, String key) {

        try {
            if (Identifier instanceof OfflinePlayer p) {
                String world = yml.getString("PlayerData." + p.getUniqueId() + "." + key + ".World");
                double X = yml.getDouble("PlayerData." + p.getUniqueId() + "." + key + ".X");
                double Y = yml.getDouble("PlayerData." + p.getUniqueId() + "." + key + ".Y");
                double Z = yml.getDouble("PlayerData." + p.getUniqueId() + "." + key + ".Z");
                float Pitch = Float.parseFloat(yml.getString("PlayerData." + p.getUniqueId() + "." + key + ".Pitch"));
                float Yaw = Float.parseFloat(yml.getString("PlayerData." + p.getUniqueId() + "." + key + ".Yaw"));
                return new Location(Bukkit.getWorld(world), X, Y, Z, Yaw, Pitch);

            } else {
                String world = yml.getString(Identifier + "." + key + ".World");
                double X = yml.getDouble(Identifier + "." + key + ".X");
                double Y = yml.getDouble(Identifier + "." + key + ".Y");
                double Z = yml.getDouble(Identifier + "." + key + ".Z");
                float Pitch = Float.parseFloat(yml.getString(Identifier + "." + key + ".Pitch"));
                float Yaw = Float.parseFloat(yml.getString(Identifier + "." + key + ".Yaw"));
                return new Location(Bukkit.getWorld(world), X, Y, Z, Yaw, Pitch);
            }
        } catch (Exception ignore) {}
        return null;
    }

    //Get String List from configs
    public List<String> getStringList(Object Identifier, String key) {
        if (Identifier instanceof OfflinePlayer p) {
            return yml.getStringList("PlayerData." + p.getUniqueId() + "." + key);
        } else {
            return yml.getStringList(Identifier + "." + key);
        }
    }

    //Get Boolean List from configs
    public List<Boolean> getBooleanList(Object Identifier, String key) {
        if (Identifier instanceof OfflinePlayer p) {
            return yml.getBooleanList("PlayerData." + p.getUniqueId() + "." + key);
        } else {
            return yml.getBooleanList(Identifier + "." + key);
        }
    }

    //Get Byte List from configs
    public List<Byte> getByteList(Object Identifier, String key) {
        if (Identifier instanceof OfflinePlayer p) {
            return yml.getByteList("PlayerData." + p.getUniqueId() + "." + key);
        } else {
            return yml.getByteList(Identifier + "." + key);
        }
    }

    //Get Character List from configs
    public List<Character> getCharacterList(Object Identifier, String key) {
        if (Identifier instanceof OfflinePlayer p) {
            return yml.getCharacterList("PlayerData." + p.getUniqueId() + "." + key);
        } else {
            return yml.getCharacterList(Identifier + "." + key);
        }
    }

    //Get Double List from configs
    public List<Double> getDoubleList(Object Identifier, String key) {
        if (Identifier instanceof OfflinePlayer p) {
            return yml.getDoubleList("PlayerData." + p.getUniqueId() + "." + key);
        } else {
            return yml.getDoubleList(Identifier + "." + key);
        }
    }

    //Get Float List from configs
    public List<Float> getFloatList(Object Identifier, String key) {
        if (Identifier instanceof OfflinePlayer p) {
            return yml.getFloatList("PlayerData." + p.getUniqueId() + "." + key);
        } else {
            return yml.getFloatList(Identifier + "." + key);
        }
    }

    //Get Integer List from configs
    public List<Integer> getIntegerList(Object Identifier, String key) {
        if (Identifier instanceof OfflinePlayer p) {
            return yml.getIntegerList("PlayerData." + p.getUniqueId() + "." + key);
        } else {
            return yml.getIntegerList(Identifier + "." + key);
        }
    }

    //Get Long List from configs
    public List<Long> getLongList(Object Identifier, String key) {
        if (Identifier instanceof OfflinePlayer p) {
            return yml.getLongList("PlayerData." + p.getUniqueId() + "." + key);
        } else {
            return yml.getLongList(Identifier + "." + key);
        }
    }

    //Get Map<?, ?> List from configs
    public List<Map<?,?>> getMapList(Object Identifier, String key) {
        if (Identifier instanceof OfflinePlayer p) {
            return yml.getMapList("PlayerData." + p.getUniqueId() + "." + key);
        } else {
            return yml.getMapList(Identifier + "." + key);
        }
    }

    //Get Short List from configs
    public List<Short> getShortList(Object Identifier, String key) {
        if (Identifier instanceof OfflinePlayer p) {
            return yml.getShortList("PlayerData." + p.getUniqueId() + "." + key);
        } else {
            return yml.getShortList(Identifier + "." + key);
        }
    }

    //Get ? List from configs
    public List<?> getList(Object Identifier, String key) {
        if (Identifier instanceof OfflinePlayer p) {
            return yml.getList("PlayerData." + p.getUniqueId() + "." + key);
        } else {
            return yml.getList(Identifier + "." + key);
        }
    }


    //Get String from configs
    public String getString(Object Identifier, String key) {
        if (Identifier instanceof OfflinePlayer p) {
            return yml.getString("PlayerData." + p.getUniqueId() + "." + key);
        } else {
            return yml.getString(Identifier + "." + key);
        }

    }

    //Get Boolean from config
    public Boolean getBoolean(Object Identifier, String key) {
        if (Identifier instanceof OfflinePlayer p) {
            return yml.getBoolean("PlayerData." + p.getUniqueId() + "." + key);
        } else {
            return yml.getBoolean(Identifier + "." + key);
        }
    }

    //Get Integer from config
    public Integer getInt(Object Identifier, String key) {
        if (Identifier instanceof OfflinePlayer p) {
            return yml.getInt("PlayerData." + p.getUniqueId() + "." + key);
        } else {
            return yml.getInt(Identifier + "." + key);
        }
    }

    //Get Double from config
    public Double getDouble(Object Identifier, String key) {
        if (Identifier instanceof OfflinePlayer p) {
            return yml.getDouble("PlayerData." + p.getUniqueId() + "." + key);
        } else {
            return yml.getDouble(Identifier + "." + key);
        }
    }

    public Long getLong(Object Identifier, String key) {
        if (Identifier instanceof OfflinePlayer p) {
            return yml.getLong("PlayerData." + p.getUniqueId() + "." + key);
        } else {
            return yml.getLong(Identifier + "." + key);
        }
    }
}
