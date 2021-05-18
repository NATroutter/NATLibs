package net.natroutter.natlibs.utilities.Bungeecord.objects;

public class ServerData {

    private String serverName;
    private String ipAddress;
    private Integer port;
    private Integer playerCount;
    private ServerStatus serverStatue;

    public ServerData(String serverName) {
        serverName = serverName;
    }

    public ServerStatus getServerStatue() {
        return serverStatue;
    }

    public Integer getPlayerCount() {
        return playerCount;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public Integer getPort() {
        return port;
    }

    public void setServerStatue(ServerStatus serverStatue) {
        this.serverStatue = serverStatue;
    }

    public void setPlayerCount(Integer playerCount) {
        this.playerCount = playerCount;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}

