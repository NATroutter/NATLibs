package fi.natroutter.natlibs;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Demo extends Command {

    public static boolean demo = false;

    public Demo() {
        super("demo");
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        Player player = (Player) sender;

        return true;
    }

}
