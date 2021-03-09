package net.natroutter.natlibs.handlers.Database;

/**
 * Simple object that holds database credentials
 * for identifing later
 */
public class DatabaseCredentials {

    String host;
    Integer port;
    String user;
    String pass;

    /**
     * Constructor for creating new YamlDataHandler
     * with option to select file name
     *
     * @param host  Database servers ip address
     * @param port  Database servers port
     * @param user  User for database
     * @param pass  Password for database
     */
    public DatabaseCredentials(String host, Integer port, String user, String pass) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.pass = pass;
    }

    /**
     * Method for receiving database host
     *
     * @return returns database host as String
     */
    public String getHost() {
        return host;
    }

    /**
     * Method for receiving database port
     *
     * @return returns database port as Integer
     */
    public Integer getPort() {
        return port;
    }

    /**
     * Method for receiving database user
     *
     * @return returns database user as String
     */
    public String getUser() {
        return user;
    }

    /**
     * Method for receiving database pass
     *
     * @return returns database pass as String
     */
    public String getPass() {
        return pass;
    }
}
