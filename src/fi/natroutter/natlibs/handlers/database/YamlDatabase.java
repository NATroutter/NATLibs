package fi.natroutter.natlibs.handlers.database;

import java.awt.desktop.QuitEvent;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

public class YamlDatabase {

    final JavaPlugin plugin;
    final YamlDocument document;

    @SneakyThrows
    private YamlDatabase(Builder builder) {
        this.plugin = builder.getInstance();
        this.document = YamlDocument.create(
                new File(builder.getDataFolder(), builder.getFileName()),
                builder.getGeneralSettings(),
                builder.getLoaderSettings(),
                builder.getDumperSettings(),
                builder.getUpdaterSettings()
        );
    }

    @Getter
    public static class Builder {

        private JavaPlugin instance;

        public Builder(JavaPlugin plugin) {
            this.instance = plugin;
            this.dataFolder = plugin.getDataFolder();
        }

        private File dataFolder;
        private String fileName = "database.yml";
        private LoaderSettings loaderSettings = LoaderSettings.DEFAULT;
        private DumperSettings dumperSettings = DumperSettings.DEFAULT;
        private GeneralSettings generalSettings = GeneralSettings.DEFAULT;
        private UpdaterSettings updaterSettings = UpdaterSettings.DEFAULT;

        public Builder setDataFolder(File dataFolder) {
            this.dataFolder = dataFolder;
            return this;
        }

        public Builder setFileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public Builder setLoaderSettings(LoaderSettings loaderSettings) {
            this.loaderSettings = loaderSettings;
            return this;
        }

        public Builder setDumperSettings(DumperSettings dumperSettings) {
            this.dumperSettings = dumperSettings;
            return this;
        }

        public Builder setGeneralSettings(GeneralSettings generalSettings) {
            this.generalSettings = generalSettings;
            return this;
        }

        public Builder setUpdaterSettings(UpdaterSettings updaterSettings) {
            this.updaterSettings = updaterSettings;
            return this;
        }

        public YamlDatabase build() {
            return new YamlDatabase(this);
        }
    }


    @SneakyThrows
    public void reload() {
        document.reload();
    }

    public <T> T getObject(Object identifier, Class<T> clazz) {
        if (identifier instanceof OfflinePlayer p) {
            return document.getAs("PlayerData." + p.getUniqueId(), clazz);
        } else {
            return document.getAs(identifier.toString(), clazz);
        }
    }
    public <T> T getObject(Object identifier, String key, Class<T> clazz) {
        if (identifier instanceof OfflinePlayer p) {
            return document.getAs("PlayerData." + p.getUniqueId() + "." + key, clazz);
        } else {
            return document.getAs(identifier + "." + key, clazz);
        }
    }

    public <T> Map<String, T> getObjectList(Object identifier, Class<T> clazz) {
        Section section = document.getSection(identifier.toString());
        if (section == null) return null;

        Set<Object> keys = section.getKeys();
        return keys.stream().collect(Collectors.toMap(
                Object::toString,
                entry -> getObject(identifier + "." + entry, clazz)
        ));

    }
    public <T> Map<String, T> getObjectList(Object identifier, String key, Class<T> clazz) {
        Section section = document.getSection(key);
        if (section == null) return null;

        Set<Object> keys = section.getKeys();
        return keys.stream().collect(Collectors.toMap(
                Object::toString,
                entry -> getObject(identifier, key + "." + entry, clazz)
        ));
    }


    //Save data to config
    @SneakyThrows
    public void save() {
        document.save();
    }

    public void remove(Object identifier, String key){
        if (identifier instanceof Player p) {
            document.remove("PlayerData." + p.getUniqueId() + "." + key);
        } else {
            document.remove(identifier + "." + key);
        }
    }

    public void save(Object identifier, String key, Object value) {
        set(identifier, key, value);
        save();
    }
    public void set(Object identifier, String key, Object value) {
        if (value instanceof Location loc) {
            if (identifier instanceof OfflinePlayer p) {
                document.set("PlayerData." + p.getUniqueId() + "." + key + ".World", loc.getWorld().getName());
                document.set("PlayerData." + p.getUniqueId() + "." + key + ".X", loc.getX());
                document.set("PlayerData." + p.getUniqueId() + "." + key + ".Y", loc.getY());
                document.set("PlayerData." + p.getUniqueId() + "." + key + ".Z", loc.getZ());
                document.set("PlayerData." + p.getUniqueId() + "." + key + ".Pitch", loc.getPitch());
                document.set("PlayerData." + p.getUniqueId() + "." + key + ".Yaw", loc.getYaw());
            } else {
                document.set(identifier + "." + key + ".World", loc.getWorld().getName());
                document.set(identifier + "." + key + ".X", loc.getX());
                document.set(identifier + "." + key + ".Y", loc.getY());
                document.set(identifier + "." + key + ".Z", loc.getZ());
                document.set(identifier + "." + key + ".Pitch", loc.getPitch());
                document.set(identifier + "." + key + ".Yaw", loc.getYaw());
            }
        } else {
            if (identifier instanceof OfflinePlayer p) {
                document.set("PlayerData." + p.getUniqueId() + "." + key, value);
            } else {
                document.set(identifier + "." + key, value);
            }
        }
    }



    //Get config keys
    public List<String> getKeys(Object identifier, Object key) {
        return document.getSection(identifier + "." + key).getKeys().stream().map(Object::toString).toList();
    }

    //Check if contains value
    public boolean valueExists(String value) {
        return document.contains(value);
    }

    public boolean valueExists(Object identifier, String key) {
        if (identifier instanceof OfflinePlayer p) {
            return document.contains("PlayerData." + p.getUniqueId() + "." + key);
        } else {
            return document.contains(identifier + "." + key);
        }
    }

    //Get Location from config
    @Nullable
    public Location getLocation(Object identifier, String key) {

        try {
            if (identifier instanceof OfflinePlayer p) {
                String world = document.getString("PlayerData." + p.getUniqueId() + "." + key + ".World");
                double X = document.getDouble("PlayerData." + p.getUniqueId() + "." + key + ".X");
                double Y = document.getDouble("PlayerData." + p.getUniqueId() + "." + key + ".Y");
                double Z = document.getDouble("PlayerData." + p.getUniqueId() + "." + key + ".Z");
                float Pitch = Float.parseFloat(document.getString("PlayerData." + p.getUniqueId() + "." + key + ".Pitch"));
                float Yaw = Float.parseFloat(document.getString("PlayerData." + p.getUniqueId() + "." + key + ".Yaw"));
                return new Location(Bukkit.getWorld(world), X, Y, Z, Yaw, Pitch);

            } else {
                String world = document.getString(identifier + "." + key + ".World");
                double X = document.getDouble(identifier + "." + key + ".X");
                double Y = document.getDouble(identifier + "." + key + ".Y");
                double Z = document.getDouble(identifier + "." + key + ".Z");
                float Pitch = Float.parseFloat(document.getString(identifier + "." + key + ".Pitch"));
                float Yaw = Float.parseFloat(document.getString(identifier + "." + key + ".Yaw"));
                return new Location(Bukkit.getWorld(world), X, Y, Z, Yaw, Pitch);
            }
        } catch (Exception ignore) {}
        return null;
    }

    //Get String List from configs
    public List<String> getStringList(Object identifier, String key) {
        if (identifier instanceof OfflinePlayer p) {
            return document.getStringList("PlayerData." + p.getUniqueId() + "." + key);
        } else {
            return document.getStringList(identifier + "." + key);
        }
    }

    //Get Byte List from configs
    public List<Byte> getByteList(Object identifier, String key) {
        if (identifier instanceof OfflinePlayer p) {
            return document.getByteList("PlayerData." + p.getUniqueId() + "." + key);
        } else {
            return document.getByteList(identifier + "." + key);
        }
    }

    //Get Double List from configs
    public List<Double> getDoubleList(Object identifier, String key) {
        if (identifier instanceof OfflinePlayer p) {
            return document.getDoubleList("PlayerData." + p.getUniqueId() + "." + key);
        } else {
            return document.getDoubleList(identifier + "." + key);
        }
    }

    //Get Float List from configs
    public List<Float> getFloatList(Object identifier, String key) {
        if (identifier instanceof OfflinePlayer p) {
            return document.getFloatList("PlayerData." + p.getUniqueId() + "." + key);
        } else {
            return document.getFloatList(identifier + "." + key);
        }
    }

    //Get Integer List from configs
    public List<Integer> getIntegerList(Object identifier, String key) {
        if (identifier instanceof OfflinePlayer p) {
            return document.getIntList("PlayerData." + p.getUniqueId() + "." + key);
        } else {
            return document.getIntList(identifier + "." + key);
        }
    }

    //Get Long List from configs
    public List<Long> getLongList(Object identifier, String key) {
        if (identifier instanceof OfflinePlayer p) {
            return document.getLongList("PlayerData." + p.getUniqueId() + "." + key);
        } else {
            return document.getLongList(identifier + "." + key);
        }
    }

    //Get Map<?, ?> List from configs
    public List<Map<?,?>> getMapList(Object identifier, String key) {
        if (identifier instanceof OfflinePlayer p) {
            return document.getMapList("PlayerData." + p.getUniqueId() + "." + key);
        } else {
            return document.getMapList(identifier + "." + key);
        }
    }

    //Get Short List from configs
    public List<Short> getShortList(Object identifier, String key) {
        if (identifier instanceof OfflinePlayer p) {
            return document.getShortList("PlayerData." + p.getUniqueId() + "." + key);
        } else {
            return document.getShortList(identifier + "." + key);
        }
    }

    //Get ? List from configs
    public List<?> getList(Object identifier, String key) {
        if (identifier instanceof OfflinePlayer p) {
            return document.getList("PlayerData." + p.getUniqueId() + "." + key);
        } else {
            return document.getList(identifier + "." + key);
        }
    }


    //Get String from configs
    public String getString(Object identifier, String key) {
        if (identifier instanceof OfflinePlayer p) {
            return document.getString("PlayerData." + p.getUniqueId() + "." + key);
        } else {
            return document.getString(identifier + "." + key);
        }

    }

    //Get Boolean from config
    public Boolean getBoolean(Object identifier, String key) {
        if (identifier instanceof OfflinePlayer p) {
            return document.getBoolean("PlayerData." + p.getUniqueId() + "." + key);
        } else {
            return document.getBoolean(identifier + "." + key);
        }
    }

    //Get Integer from config
    public Integer getInt(Object identifier, String key) {
        if (identifier instanceof OfflinePlayer p) {
            return document.getInt("PlayerData." + p.getUniqueId() + "." + key);
        } else {
            return document.getInt(identifier + "." + key);
        }
    }

    //Get Double from config
    public Double getDouble(Object identifier, String key) {
        if (identifier instanceof OfflinePlayer p) {
            return document.getDouble("PlayerData." + p.getUniqueId() + "." + key);
        } else {
            return document.getDouble(identifier + "." + key);
        }
    }

    public Long getLong(Object identifier, String key) {
        if (identifier instanceof OfflinePlayer p) {
            return document.getLong("PlayerData." + p.getUniqueId() + "." + key);
        } else {
            return document.getLong(identifier + "." + key);
        }
    }
}
