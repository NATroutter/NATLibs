package net.natroutter.natlibs.handlers.Database;

import com.zaxxer.hikari.HikariConfig;
import net.natroutter.natlibs.handlers.Database.DataHandler.DataHandler;
import net.natroutter.natlibs.handlers.Database.DataHandler.YamlDataHandler;
import net.natroutter.natlibs.utilities.StringHandler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;


/**
 * Simple database handler for saving key and value
 * pairs to database and reciving them later
 * currently supports: Derpy,MariaDB,Yaml
 */
public class Database {

    JavaPlugin pl;
    String dbName;
    DatabaseDriver driver;
    DatabaseCredentials cred;
    HikariConfig hikConf;
    ConsoleCommandSender console;
    String ConfigError;
    YamlDataHandler yaml;
    DataHandler dataHand;
    Character LineSep = '/';
    Character keySep = ':';

    /**
     * Constructor for complex database
     * handling like MariaDB or Depry
     * this can not be used with simple
     * configuration like yaml
     *
     * @param  pl       JavaPlugin instance
     * @param  dbName   Database name
     * @param  driver   Database driver for defining database type
     * @param  cred     Credentials for authorizing database connection
     */
    public Database(JavaPlugin pl, String dbName, DatabaseDriver driver, DatabaseCredentials cred) {
        this.pl = pl;
        this.dbName = dbName;
        this.driver = driver;
        this.cred = cred;
        this.console = pl.getServer().getConsoleSender();
        this.dataHand = new DataHandler();
        if (!driver.equals(DatabaseDriver.Yaml)) {
            hikConf = new HikariConfig();
            hikConf.setJdbcUrl("jdbc:"+driver.get()+"://"+cred.getHost()+":"+cred.getPort()+"/" + dbName);
            hikConf.setUsername(cred.getUser());
            hikConf.setPassword(cred.getPass());
        } else {
            this.ConfigError = "§4[Database][ERROR] You dont have to specify credentials to when using Yaml driver!";
            console.sendMessage(ConfigError);
        }
    }

    /**
     * Constructor for simple configuration
     * like yaml cant be used when doing more
     * complex database handlins like mariaDB
     *
     * @param  pl       JavaPlugin instance
     * @param  dbName   Database name
     * @param  driver   Database driver for defining database type
     */
    public Database(JavaPlugin pl, String dbName, DatabaseDriver driver) {
        this.pl = pl;
        this.dbName = dbName;
        this.driver = driver;
        this.yaml = new YamlDataHandler(pl, ValidateYamlName(dbName));
        this.console = pl.getServer().getConsoleSender();
        if (!driver.equals(DatabaseDriver.Yaml)){
            this.ConfigError = "§4[Database][ERROR] Database credentials are not defined!";
            console.sendMessage(ConfigError);
        }
    }

    /**
     * Method for setting line separator Characher
     * this method gets used when serializing
     * lists of data to database default characher
     * for this operation is /
     *
     * @param  ch   Line separator
     */
    public Database setLineSeperator(Character ch) {
        this.LineSep = ch;
        return this;
    }

    /**
     * Method for setting key separator Characher
     * this method gets used when combining
     * identifier and key to one singlekey
     *
     * @param  ch   Key separator
     */
    public Database setKeySeperator(Character ch) {
        this.keySep = ch;
        return this;
    }

    /**
     * Method for saveing all kinds of data to database
     * with this you can literally save any form of data
     *
     * @param  id   Identifier for where to set data
     * @param  key  key for identifing where to set data
     * @param value data to save into database
     */
    public void save(Object id, String key, Object value) {
        if (!CheckConfigError()) {return;}

        String saveKey = getSingleKey(id,key);
        String saveVal = value.toString();
        if (value instanceof Location) {
            saveVal = SerializeLocation((Location)value);
        }
        if (value instanceof Collection<?>) {
            saveVal = SerializeList((Collection<?>)value);
        }

        if (driver.equals(DatabaseDriver.Yaml)) {
            yaml.save(saveKey,saveVal);
        } else {
            dataHand.save(saveKey, saveVal);
        }
    }

    /**
     * Method for taking data from database as string
     *
     * @param  id   Identifier for where to get data
     * @param  key  key for identifing what to get
     * @return      returns string
     */
    public String getString(Object id, String key) {
        if (!CheckConfigError()) {return null;}
        try {switch (driver) {
            case Yaml:
                return String.valueOf(yaml.get(getSingleKey(id,key)));
            default:
                return String.valueOf(dataHand.get(getSingleKey(id,key)));
        }} catch (Exception ignored) {}
        return null;
    }

    /**
     * Method for taking data from database as integer
     *
     * @param  id   Identifier for where to get data
     * @param  key  key for identifing what to get
     * @return      returns integer
     */
    public Integer getInt(Object id, String key) {
        if (!CheckConfigError()) {return null;}
        try {switch (driver) {
            case Yaml:
                return Integer.valueOf(yaml.get(getSingleKey(id,key)).toString());
            default:
                return Integer.valueOf(dataHand.get(getSingleKey(id,key)).toString());
        }} catch (Exception ignored) {}
        return null;
    }

    /**
     * Method for taking data from database as long
     *
     * @param  id   Identifier for where to get data
     * @param  key  key for identifing what to get
     * @return      returns long
     */
    public Long getLong(Object id, String key) {
        if (!CheckConfigError()) {return null;}
        try {switch (driver) {
            case Yaml:
                return Long.valueOf(yaml.get(getSingleKey(id,key)).toString());
            default:
                return Long.valueOf(dataHand.get(getSingleKey(id,key)).toString());
        }} catch (Exception ignored) {}
        return null;
    }

    /**
     * Method for taking data from database as double
     *
     * @param  id   Identifier for where to get data
     * @param  key  key for identifing what to get
     * @return      returns double
     */
    public Double getDouble(Object id, String key) {
        if (!CheckConfigError()) {return null;}
        try {switch (driver) {
            case Yaml:
                return Double.valueOf(yaml.get(getSingleKey(id,key)).toString());
            default:
                return Double.valueOf(dataHand.get(getSingleKey(id,key)).toString());
        }} catch (Exception ignored) {}
        return null;
    }

    /**
     * Method for taking data from database as boolean
     *
     * @param  id   Identifier for where to get data
     * @param  key  key for identifing what to get
     * @return      returns boolean
     */
    public Boolean getBoolean(Object id, String key) {
        if (!CheckConfigError()) {return null;}
        try {switch (driver) {
            case Yaml:
                return Boolean.valueOf(yaml.get(getSingleKey(id,key)).toString());
            default:
                return Boolean.valueOf(dataHand.get(getSingleKey(id,key)).toString());
        }} catch (Exception ignored) {}
        return null;
    }

    /**
     * Method for taking data from database as location
     *
     * @param  id   Identifier for where to get data
     * @param  key  key for identifing what to get
     * @return      returns location
     */
    public Location getLocation(Object id, String key) {
        if (!CheckConfigError()) {return null;}
        try {switch (driver) {
            case Yaml:
                return UnserializeLocation(yaml.get(getSingleKey(id,key)).toString());
            default:
                return UnserializeLocation(dataHand.get(getSingleKey(id,key)).toString());
        }} catch (Exception ignored) {}
        return null;
    }

    /**
     * Method for taking data from database as list
     * unfortunately only sting lists are supported at the time
     * but you can parse list to any format if you need to
     *
     * @param  id   Identifier for where to get data
     * @param  key  key for identifing what to get
     * @return      returns String list
     */
    public List<String> getList(Object id, String key) {
        if (!CheckConfigError()) {return null;}
        try {switch (driver) {
            case Yaml:
                return UnserializeList(yaml.get(getSingleKey(id,key)).toString());
            default:
                return UnserializeList(dataHand.get(getSingleKey(id,key)).toString());
        }} catch (Exception ignored) {}
        return null;
    }









    private String getSingleKey(Object id, String key) {
        String SingleID = id.toString().replaceAll(keySep.toString(), "");
        String SingleKey = key.replaceAll(keySep.toString(), "");
        return SingleID + keySep + SingleKey;
    }

    private String ValidateYamlName(String name) {
        if (name.endsWith(".yaml")) {
            return name;
        }
        return name + ".yaml";
    }

    private boolean CheckConfigError() {
        if (ConfigError != null) {
            console.sendMessage(ConfigError);
            return false;
        }
        return false;
    }

    private List<String> UnserializeList(String list) {
        StringHandler sh = new StringHandler(list);
        return sh.split(LineSep);
    }

    private String SerializeList(Collection<?> list) {
        StringHandler sh = new StringHandler(list, LineSep);
        return sh.build();
    }

    private Location UnserializeLocation(String loc) {
        List<String> list = Arrays.asList(loc.split(LineSep.toString()));
        try {
            if (Bukkit.getWorld(list.get(0)) == null) { return null; }
            World world = Bukkit.getWorld(list.get(0));
            int x = Integer.parseInt(list.get(1));
            int y = Integer.parseInt(list.get(2));
            int z = Integer.parseInt(list.get(3));
            float yaw = Float.parseFloat(list.get(4));
            float pitch = Float.parseFloat(list.get(5));
            return new Location(world,x, y, z,yaw,pitch);
        } catch (Exception ingored) {}
        return  null;
    }

    private String SerializeLocation(Location loc) {
        return loc.getWorld().getName() + LineSep.toString() + loc.getX() + LineSep.toString() + loc.getY() + LineSep.toString() + loc.getZ() + LineSep.toString() + loc.getYaw() + LineSep.toString() + loc.getPitch();
    }

    private String getIdentifier(Object id){
        String identifier = id.toString();
        if (id instanceof OfflinePlayer) {
            identifier = ((OfflinePlayer) id).getUniqueId().toString();
        }
        return  identifier;
    }

}
