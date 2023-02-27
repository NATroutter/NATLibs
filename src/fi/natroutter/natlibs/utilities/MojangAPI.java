package fi.natroutter.natlibs.utilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fi.natroutter.natlibs.objects.PlayerInfo;
import fi.natroutter.natlibs.objects.UUIDTypeAdapter;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jsoup.Jsoup;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

public class MojangAPI {

    private final JavaPlugin pl;
    private final ConsoleCommandSender console;

    public MojangAPI(JavaPlugin pl) {
        this.pl = pl;
        console = pl.getServer().getConsoleSender();
    }

    public UUID getUUID(String name) {
        return UUID.fromString(getData(name, false).id());
    }
    public String getName(UUID uuid) {
        return getData(uuid, true).name();
    }

    private MinecraftData getData(Object nameOrUUID, boolean isUUID) {
        String api = "https://api.mojang.com/users/profiles/minecraft/" + nameOrUUID;;
        if (isUUID) {
            api = "https://sessionserver.mojang.com/session/minecraft/profile/" + nameOrUUID;
        }
        try {
            String json = Jsoup.connect(api).ignoreContentType(true).userAgent("MotiPiste/1.0").execute().body();
            return new Gson().fromJson(json, MinecraftData.class);
        } catch (Exception e) {
            console.sendMessage("§4["+pl.getName()+"] §cFailed to retrieve miencraft user data : Invalid name or uuid? >> " + nameOrUUID);
        }
        return new MinecraftData("Unknown", "Unknown");
    }

    public record MinecraftData(String name, String id) {
        public MinecraftData(){
            this("Unknown", null);
        }
    }

}
