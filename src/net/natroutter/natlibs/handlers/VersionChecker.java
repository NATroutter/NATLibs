package net.natroutter.natlibs.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.natroutter.natlibs.objects.VersionData;
import org.bukkit.plugin.java.JavaPlugin;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

public class VersionChecker {
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private String server = "https://plugins.nat.gg/version.php?plugin=%s&version=%s";
    private final String plugin;
    private final String version;
    private final VersionData data;

    public VersionChecker(JavaPlugin plugin) {
        this.plugin = plugin.getName();
        this.version = plugin.getDescription().getVersion();
        this.data = get();
    }
    public VersionChecker(String server, JavaPlugin plugin) {
        this.server = server;
        this.plugin = plugin.getName();
        this.version = plugin.getDescription().getVersion();
        this.data = get();
    }

    public VersionData get() {
        VersionData data = new VersionData(false, false, "Failed to get data from server", "error", "Unknown");
        Connection con = Jsoup.connect(String.format(server, plugin, version));
        con.userAgent("NATLibs-VersionChecker");
        con.header("Content-Type", "application/json; charset=utf-8");
        con.header("Accept", "application/json");
        con.ignoreContentType(true);
        con.method(Connection.Method.GET);
        try {
            String resp = con.get().body().text();
            data = gson.fromJson(resp, VersionData.class);
        } catch (Exception ignored) {}
        return data ;
    }

    public String getCurrentVersion() { return version; }

    public boolean isSuccess() {return data != null && data.isSuccess();}
    public boolean hasUpdate() {return data.hasUpdate();}
    public String getNewVersion() {return data.getVersion();}
    public String getError() {return data.getError();}
    public String getURL() {return data.getUpdateURL();}
}
