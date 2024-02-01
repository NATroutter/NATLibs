package fi.natroutter.natlibs.config;

import fi.natroutter.natlibs.utilities.Colors;
import fi.natroutter.natlibs.utilities.Utilities;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

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

}
