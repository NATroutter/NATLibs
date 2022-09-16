package net.natroutter.natlibs.handlers;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Licencing {

    private final JavaPlugin plugin;
    private final String CheckURL;
    private final String licenceKey;
    private final String securityKey;

    public Licencing(JavaPlugin plugin, String CheckURL, String licenceKey, String securityKey) {
        this.plugin = plugin;
        this.CheckURL = CheckURL;
        this.licenceKey = licenceKey;
        this.securityKey = securityKey;
    }

    public boolean check() {
        ConsoleCommandSender console = Bukkit.getConsoleSender();
        AdvancedLicense licence = new AdvancedLicense(licenceKey, CheckURL, plugin);
        licence.setSecurityKey(securityKey);
        switch (licence.isValid()) {
            case URL_ERROR -> {console.sendMessage("§4["+plugin.getName()+"][Licencing] §cConnecting error : Internal url error, contact developer! (code:1)");}
            case PAGE_ERROR -> {console.sendMessage("§4["+plugin.getName()+"][Licencing] §cConnecting error : Internal url error, contact developer! (code:2)");}
            case WRONG_RESPONSE -> {console.sendMessage("§4["+plugin.getName()+"][Licencing] §cConnecting error : Internal url error, contact developer! (code:3)");}
            case KEY_OUTDATED -> {console.sendMessage("§4["+plugin.getName()+"][Licencing] §cYour licence key is not valid anymore!");}
            case KEY_NOT_FOUND -> {console.sendMessage("§4["+plugin.getName()+"][Licencing] §cYour Licnce key doesn't exists!");}
            case NOT_VALID_IP -> {console.sendMessage("§4["+plugin.getName()+"][Licencing] §cThis Licence key is already use for maximium amount of ip:s!");}
            case INVALID_PLUGIN -> {console.sendMessage("§4["+plugin.getName()+"][Licencing] §cThis licence cant not be used in this plugin!");}
            case VALID -> {return true;}
        }
        return false;
    }

}
