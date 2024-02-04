package fi.natroutter.natlibs.configuration;

import fi.natroutter.natlibs.utilities.Colors;
import lombok.SneakyThrows;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class ConfigUtils {

    protected static boolean isValidPrefix(CommandSender sender, ILang prefix) {
        if (prefix != null) return true;

        Component message = Colors.translate("<red>Error in language configuration : Prefix is missing or null!");
        CommandSender console = Bukkit.getConsoleSender();
        if (console != sender) {
            console.sendMessage(message);
        }
        sender.sendMessage(message);
        return false;
    }
    protected static boolean isValidPrefix(ILang prefix) {
        if (prefix != null) return true;
        Component message = Colors.translate("<red>Error in language configuration : Prefix is missing or null!");
        Bukkit.getConsoleSender().sendMessage(message);
        return false;
    }

    @SneakyThrows
    public static InputStream getResource(Class<?> clazz, String fileName) {
        URL url = clazz.getResource(fileName);
        URLConnection connection = url.openConnection();
        connection.setUseCaches(false);
        return connection.getInputStream();
    }

}
