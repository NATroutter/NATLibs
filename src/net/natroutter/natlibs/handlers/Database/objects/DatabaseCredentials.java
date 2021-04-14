package net.natroutter.natlibs.handlers.Database.objects;

public class DatabaseCredentials {

    private String name;
    private String host;
    private Integer port;
    private String user;
    private String pass;


    public DatabaseCredentials(String name) {
        this.name = name;
        this.host = null;
        this.port = null;
        this.user = null;
        this.pass = null;
    }

    public DatabaseCredentials(String name, String host, Integer port, String user, String pass) {
        this.name = name;
        this.host = host;
        this.port = port;
        this.user = user;
        this.pass = pass;
    }

    public String getName() {
        return name;
    }
    public String getHost() {
        return host;
    }
    public Integer getPort() {
        return port;
    }
    public String getUser() { return user; }
    public String getPass() { return pass; }
}
