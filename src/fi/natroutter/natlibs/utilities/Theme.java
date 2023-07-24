package fi.natroutter.natlibs.utilities;

import fi.natroutter.natlibs.objects.DualString;
import lombok.AllArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Theme {

    @AllArgsConstructor
    public enum Error {
        ONLY_INGAME("This command can only be used ingame!"),
        INVALID_ARGUMENTS("Invalid arguments!"),
        INVALID_ITEM("Invalid item, you need to hold an item in your hand!"),
        NO_PERMISSION("You don't have permission to use this!"),

        ;
        public String message;
    }

    public static void sendError(CommandSender sender, Error error) {
        if (sender instanceof Player p) {
            p.sendMessage(prefixed(error.message));
        } else {
            sender.sendMessage(error.message);
        }
    }

    public static String highlight(String value) {
        return "<color:#dd5555>"+value+"</color>";
    }

    public static Component main(String value) {
        return Utilities.translateColors("<color:#ff0000>"+value+"</color>");
    }

    public static Component prefixed(String value) {
        return Utilities.translateColors("<bold><color:#454545><color:#ff0000>⚡</color></color></bold> <bold><color:#ff3333>NATLibs</color></bold><color:#454545> <bold>➤</bold> <color:#b3b3b3>"+value+"</color></color>");
    }

    public static Component multiline(DualString... helps) {
        return multiline("|", false, false, helps);
    }
    public static Component multiline(String separator, boolean invertColor, boolean removeStartPrefix, DualString... helps) {
        String main = invertColor ? "#b3b3b3" : "#dd5555";
        String high = invertColor ? "#dd5555" : "#b3b3b3";
        List<Component> comps = new ArrayList<>();
        comps.add(Utilities.translateColors("<bold><color:#454545><st>━━━━━━━━━━━━</st>|</color> <color:#ff3333>NATLibs</color> <color:#454545>|<st>━━━━━━━━━━━━</st></color></bold>"));

        comps.addAll(Arrays.stream(helps).map(h-> {

            if (h.getFirst().isBlank() && h.getSecond().isBlank()) {
                return Component.empty();
            } else {
                return Utilities.translateColors((removeStartPrefix ? "" : "<bold><color:#454545>»</color></bold> ") + "<color:"+main+">"+h.getFirst()+"</color> <color:#454545>"+separator+"</color> <color:"+high+">"+h.getSecond()+"</color>");
            }

        }).toList());

        comps.add(Utilities.translateColors("<bold><color:#454545><st>━━━━━━━━━━━━</st>|</color> <color:#ff3333>NATLibs</color> <color:#454545>|<st>━━━━━━━━━━━━</st></color></bold>"));
        return Component.join(JoinConfiguration.newlines(), comps);
    }


}
