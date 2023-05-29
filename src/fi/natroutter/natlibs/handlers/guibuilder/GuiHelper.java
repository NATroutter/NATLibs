package fi.natroutter.natlibs.handlers.guibuilder;

import org.bukkit.entity.Player;

public class GuiHelper {

    public static boolean hasOpen(Player p) {
        return GUIListener.guis.containsKey(p.getUniqueId());
    }

    public static GUI getOpen(Player p) {
        if (GUIListener.guis.containsKey(p.getUniqueId())) {
            GUIListener.guis.get(p.getUniqueId());
        }
        return null;
    }

    public static void close(Player p) {
        if (GUIListener.guis.containsKey(p.getUniqueId())) {
            p.closeInventory();
        }
    }

}
