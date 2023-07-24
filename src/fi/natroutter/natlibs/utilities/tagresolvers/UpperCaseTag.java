package fi.natroutter.natlibs.utilities.tagresolvers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.tag.Modifying;
import org.jetbrains.annotations.NotNull;

public class UpperCaseTag implements Modifying {
    @Override
    public Component apply(@NotNull Component current, int depth) {
        if (current instanceof TextComponent component) {
            return Component.text(component.content().toUpperCase(), current.style());
        }
        return current;
    }
}
