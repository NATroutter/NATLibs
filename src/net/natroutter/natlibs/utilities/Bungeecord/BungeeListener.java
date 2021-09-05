package net.natroutter.natlibs.utilities.Bungeecord;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import net.natroutter.natlibs.utilities.Bungeecord.objects.ServerData;
import net.natroutter.natlibs.utilities.Bungeecord.objects.ServerStatus;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class BungeeListener implements PluginMessageListener {

    private BungeeHandler handler;
    private ConsoleCommandSender console = Bukkit.getConsoleSender();

    public BungeeListener(BungeeHandler handler) {
        this.handler = handler;
    }

    @Override
    public void onPluginMessageReceived(String channel, Player p, byte[] message) {
        if (!channel.equals("BungeeCord")) {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subchannel = in.readUTF();
        switch (subchannel) {
            case "GetServers":
                try {
                    for (String server : in.readUTF().split(", ")) {
                        if (handler.getDebug()) {
                            console.sendMessage(server);
                        }

                        if (server == null) {continue;}

                        if (!handler.getMap().containsKey(server)) {
                            handler.getMap().put(server, new ServerData(server));
                        }
                    }
                } catch (Exception e) {
                    if (handler.getDebug()) {
                        e.printStackTrace();
                    }
                }
                break;
            case "ServerIP":
                try {
                    String server = in.readUTF();
                    String ip = in.readUTF();
                    int port = in.readUnsignedShort();
                    if (handler.getMap().containsKey(server)) {
                        ServerData data = handler.getMap().get(server);
                        data.setIpAddress(ip);
                        data.setPort(port);
                        handler.getMap().put(server, data);

                        //online checking!
                        if (data.getIpAddress() != null && data.getPort() != null) {
                            ServerStatus statues = handler.pingServer(data);
                            data.setServerStatue(statues);
                        }

                    }
                } catch (Exception e) {
                    if (handler.getDebug()) {
                        e.printStackTrace();
                    }
                }
                break;
            case "PlayerCount":
                try {
                    String server = in.readUTF();
                    int playercount = in.readInt();
                    if (handler.getMap().containsKey(server)) {
                        ServerData data = handler.getMap().get(server);
                        data.setPlayerCount(playercount);
                        handler.getMap().put(server, data);
                    }
                } catch (Exception e) {
                    if (handler.getDebug()) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }
}