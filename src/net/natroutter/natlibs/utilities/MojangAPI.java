package net.natroutter.natlibs.utilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.natroutter.natlibs.objects.MojangApiInfo;
import net.natroutter.natlibs.objects.UUIDTypeAdapter;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

public class MojangAPI {

    private final Gson gson = new GsonBuilder().registerTypeAdapter(UUID.class, new UUIDTypeAdapter()).create();

    private final String UUID_URL = "https://api.mojang.com/users/profiles/minecraft/%s";
    private final String NAME_URL = "https://api.mojang.com/user/profile/%s";

    private final JavaPlugin pl;
    private final ConsoleCommandSender console;

    public MojangAPI(JavaPlugin pl) {
        this.pl = pl;
        console = pl.getServer().getConsoleSender();
    }

    public UUID getUUID(String name) {
        name = name.toLowerCase();
        try {
            URL url = new URL(String.format(UUID_URL, name, System.currentTimeMillis() / 1000));
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setReadTimeout(5000);

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            MojangApiInfo player = gson.fromJson(br, MojangApiInfo.class);
            return player.getUUID();

        } catch (Exception e) {
            console.sendMessage("§4["+pl.getName()+"] §cFailed to fletch data from mojang servers!");
            e.printStackTrace();
            return null;
        }
    }

    public String getName(UUID uuid) {

        try {
            if (Bukkit.getOnlineMode()) {
                URL url = new URL(String.format(NAME_URL, UUIDTypeAdapter.fromUUID(uuid)));
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setReadTimeout(5000);

                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                MojangApiInfo[] allUserNames = gson.fromJson(br, MojangApiInfo[].class);
                MojangApiInfo currentName = allUserNames[allUserNames.length - 1];

                return currentName.getName();
            } else {
                return Bukkit.getOfflinePlayer(uuid).getName();
            }
        } catch (Exception e) {
            console.sendMessage("§4["+pl.getName()+"] §cFailed to fletch data from mojang servers!");
            e.printStackTrace();
            return null;
        }

    }

}
