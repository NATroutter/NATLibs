package fi.natroutter.natlibs.Tools.magicwand;

import fi.natroutter.natlibs.utilities.Theme;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public class MWCommand extends Command {

    public MWCommand() {
        super("MagicWand");
        this.setAliases(Collections.singletonList("mw"));
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!(sender instanceof Player p)) {
            Theme.sendError(sender, Theme.Error.ONLY_INGAME);
            return false;
        }
        if (!p.hasPermission("natlibs.tools.magicwand")) {
            Theme.sendError(sender, Theme.Error.NO_PERMISSION);
            return false;
        }
        if (args.length == 0) {
            p.getInventory().addItem(MWHandler.wand(MagicMode.FRAME));
            p.sendMessage(Theme.prefixed("MagicWand added to your inventory!"));
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("debug")) {

                ItemStack item = p.getInventory().getItemInMainHand();
                ItemMeta meta = item.getItemMeta();
                PersistentDataContainer data = meta.getPersistentDataContainer();

                p.sendMessage("§4Debug data:");
                p.sendMessage("§6Has Key: §e" + data.has(MWHandler.wandKey));
                if (data.has(MWHandler.wandKey)) {
                    p.sendMessage("§6Key Value: §e" + data.get(MWHandler.wandKey, PersistentDataType.STRING));
                }


            } else {
                Theme.sendError(sender, Theme.Error.INVALID_ARGUMENTS);
            }
        } else {
            Theme.sendError(sender, Theme.Error.INVALID_ARGUMENTS);
        }
        return false;
    }

}
