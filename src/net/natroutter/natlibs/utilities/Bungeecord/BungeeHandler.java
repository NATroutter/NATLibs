package net.natroutter.natlibs.utilities.Bungeecord;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.natroutter.natlibs.utilities.Bungeecord.objects.ServerData;
import net.natroutter.natlibs.utilities.Bungeecord.objects.ServerStatus;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class BungeeHandler {

    private HashMap<String, ServerData> servers = new HashMap<>();

    private JavaPlugin pl;
    private Integer updateIntervalTick = 20 * 5;
    private Boolean debug = false;

    public BungeeHandler(JavaPlugin pl) {
        this.pl = pl;
        init();
    }
    public BungeeHandler(JavaPlugin pl, Integer updateIntervalTick) {
        this.pl = pl;
        if (updateIntervalTick != null) {
            this.updateIntervalTick = updateIntervalTick;
        }
        init();
    }

    public BungeeHandler(JavaPlugin pl, Integer updateIntervalTick, Boolean debug) {
        this.pl = pl;
        if (updateIntervalTick != null) {
            this.updateIntervalTick = updateIntervalTick;
        }
        if (debug != null) {
            this.debug = debug;
        }
        init();
    }

    private void init(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(pl, ()->{
            if (Bukkit.getOnlinePlayers().size() > 0) {
                loadServers();
                for (Map.Entry<String, ServerData> server : servers.entrySet()) {
                    getServerAddress(server.getKey());
                    ServerStatus statues = server.getValue().getServerStatue();
                    if (statues != null) {
                        if (server.getValue().getServerStatue().equals(ServerStatus.ONLINE)) {
                            getPalyerCount(server.getKey());
                        }
                    }
                }
            }
        }, 0, updateIntervalTick);
    }

    protected HashMap<String, ServerData> getMap() {return servers;}

    public boolean getDebug(){return debug;}

    public Collection<ServerData> getServers() {
        return servers.values();
    }

    public ServerData getServers(String name) {
        return servers.getOrDefault(name, null);
    }

    private void loadServers() {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("GetServers");
        Bukkit.getServer().sendPluginMessage(pl, "BungeeCord", out.toByteArray());
    }

    public void getServerAddress(String server) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("ServerIP");
        out.writeUTF(server);
        Bukkit.getServer().sendPluginMessage(pl, "BungeeCord", out.toByteArray());
    }

    private void getPalyerCount(String server) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("PlayerCount");
        out.writeUTF(server);
        Bukkit.getServer().sendPluginMessage(pl, "BungeeCord", out.toByteArray());
    }

    public ServerStatus pingServer(ServerData data) {
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(data.getIpAddress(), data.getPort()), 2);
            socket.close();
            return ServerStatus.ONLINE;
        } catch (Exception e) {
            if (debug) {
                e.printStackTrace();
            }
        }
        return ServerStatus.OFFLINE;
    }

    public void switchServer(Player p, String server) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(server);
        p.sendPluginMessage(pl, "BungeeCord", out.toByteArray());
    }

}
