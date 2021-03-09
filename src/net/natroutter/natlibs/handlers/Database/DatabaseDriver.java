package net.natroutter.natlibs.handlers.Database;


/**
 * Database driver defination!
 */
public enum DatabaseDriver {
    Derby("Derby"),
    MariaDB("MariaDB"),
    Yaml("Yaml");

    private String driver;
    DatabaseDriver(String driver) {
        this.driver = driver;
    }

    /**
     * Method for getting selected database drivers
     * name what is used to construct connection strings
     */
    public String get() {
        return driver;
    }
}
