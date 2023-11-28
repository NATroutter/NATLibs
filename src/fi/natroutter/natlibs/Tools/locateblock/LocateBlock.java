package fi.natroutter.natlibs.Tools.locateblock;

import fi.natroutter.natlibs.utilities.Theme;
import fi.natroutter.natlibs.utilities.Utilities;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class LocateBlock extends Command implements Listener {

    private BlockFinder blockFinder;

    public LocateBlock(JavaPlugin plugin) {
        super("LocateBlock");
        this.blockFinder = new BlockFinder(plugin);
    }

    private void errorMessage(Player p, String msg) {
        p.sendMessage(" ");
        p.sendMessage(Theme.prefixed(msg));
        p.sendMessage(Theme.prefixed("Type "+Theme.highlight("/locateblock help")+" for more information"));
        p.sendMessage(" ");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {

        if (!(sender instanceof Player p)) {
            sender.sendMessage(Theme.prefixed("This command can only be used ingame!"));
            return false;
        }

        if (!p.hasPermission("natlibs.tools.locateblock")) {
            Theme.sendError(sender, Theme.Error.NO_PERMISSION);
            return false;
        }

        if (args.length == 0) {
            errorMessage(p, "You have not specified range or block what you want to search!");
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("abort")) {
                blockFinder.abort(p);
            } else if (args[0].equalsIgnoreCase("info")) {
                blockFinder.info(p);
            } else if (args[0].equalsIgnoreCase("help")) {
                p.sendMessage(" ");
                p.sendMessage(Utilities.translateColors("<dark_gray><bold>&m━━━━━━━━━━━━|</bold></dark_gray> " +Theme.main("<bold>SearchInfo</bold>")+ " <dark_gray><bold>|&m━━━━━━━━━━━━</bold></dark_gray>"));
                p.sendMessage(Utilities.translateColors("&8<bold>»</bold> " + Theme.highlight("/locateblock abort")));
                p.sendMessage(Utilities.translateColors("&8<bold>»</bold> <color:#b3b3b3>Forcefully stop current search</color>"));
                p.sendMessage("");
                p.sendMessage(Utilities.translateColors("&8<bold>»</bold> " + Theme.highlight("/locateblock info")));
                p.sendMessage(Utilities.translateColors("&8<bold>»</bold> <color:#b3b3b3>show information about current search</color>"));
                p.sendMessage("");
                p.sendMessage(Utilities.translateColors("&8<bold>»</bold> " + Theme.highlight("/locateblock help")));
                p.sendMessage(Utilities.translateColors("&8<bold>»</bold> <color:#b3b3b3>Shows this help message"));
                p.sendMessage("");
                p.sendMessage(Utilities.translateColors("&8<bold>»</bold> " + Theme.highlight("/locateblock search <block> <radius>")));
                p.sendMessage(Utilities.translateColors("&8<bold>»</bold> <color:#b3b3b3>Locates all selected blocks in selected radius</color>"));
                p.sendMessage(Utilities.translateColors("<dark_gray><bold>&m━━━━━━━━━━━━|</bold></dark_gray> " +Theme.main("<bold>SearchInfo</bold>")+ " <dark_gray><bold>|&m━━━━━━━━━━━━</bold></dark_gray>"));
                p.sendMessage("");
            } else {
                errorMessage(p, "Invalid command argument");
            }
        } else if (args.length == 2) {
            errorMessage(p, "Invalid command argument");
        } else if (args.length == 3) {

            if (args[0].equalsIgnoreCase("search")) {
                Material mat = Material.getMaterial(args[1]);
                if (mat == null) {
                    errorMessage(p, "Invalid block!");
                    return false;
                }
                if (!mat.isBlock()) {
                    errorMessage(p, "Invalid block!");
                    return false;
                }
                Integer radius = null;
                try {
                    radius = Integer.parseInt(args[2]);
                } catch (Exception ignored) {}
                if (radius == null) {
                    errorMessage(p, "Invalid radius");
                    return false;
                }
                blockFinder.locateBlocks(p, mat, radius);
            } else {
                errorMessage(p, "Invalid command argument");
            }
        } else {
            errorMessage(p, "Invalid command argument");
        }
        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {

        if (args.length == 1) {
            return Utilities.getCompletes(sender, args[0], List.of(
                    "help","abort","info","search"
            ));

        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("search")) {
                return Utilities.getCompletes(sender, args[1], Arrays.stream(Material.values()).filter(Material::isBlock).map(Enum::name).collect(Collectors.toList()));
            }
        } else if (args.length == 3) {
            return Collections.singletonList("<radius>");
        }
        return null;
    }
}
