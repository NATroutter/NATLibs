package fi.natroutter.natlibs.utilities;

import fi.natroutter.natlibs.objects.Complete;
import org.bukkit.command.CommandSender;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TabUtils {

    public static List<String> empty(){return Collections.singletonList("");}

    public static List<String> completesWithPerms(CommandSender sender, String arg, List<Complete> list) {
        List<String> newList = list.stream()
                .filter(c -> sender.hasPermission(c.getPermission()))
                .map(Complete::getArg)
                .collect(Collectors.toList());
        return completes(sender, arg, newList);
    }

    public static List<String> completes(CommandSender sender, String arg, List<String> list) {
        List<String> shorted = new ArrayList<>();
        StringUtil.copyPartialMatches(arg, list, shorted);
        Collections.sort(shorted);
        return shorted;
    }

}
