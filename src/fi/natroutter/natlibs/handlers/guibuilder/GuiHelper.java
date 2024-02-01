package fi.natroutter.natlibs.handlers.guibuilder;

import fi.natroutter.natlibs.config.IConfig;
import fi.natroutter.natlibs.utilities.Colors;
import fi.natroutter.natlibs.utilities.Utilities;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
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

    private static MiniMessage mm = MiniMessage.miniMessage();

    public static Component pagedTitle(Component component, int page, int max) {
        String title = mm.serialize(component);
        return Colors.translate(title,
                Placeholder.parsed("page",String.valueOf(page)),
                Placeholder.parsed("max_page",String.valueOf(max))
        );
    }
    public static Component pagedTitle(IConfig config, int page, int max) {
        return config.asComponent(
                Placeholder.parsed("page",String.valueOf(page)),
                Placeholder.parsed("max_page",String.valueOf(max))
        );
    }
}
