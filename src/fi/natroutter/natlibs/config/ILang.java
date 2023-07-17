package fi.natroutter.natlibs.config;

import fi.natroutter.natlibs.utilities.Utilities;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.serializer.json.JSONComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public interface ILang extends IConfig {

    Language lang();
    ILang prefix();

    @Override
    default File file() {
        File langFolder = new File(getPlugin().getDataFolder(), "langs");
        if (!langFolder.exists()) {
            langFolder.mkdirs();
        }
        return new File(langFolder, lang().getLangKey() + ".yml");
    }

    default Component prefixed(TagResolver... placeholders){
        if (!ConfigUtils.isValidPrefix(prefix())) return this.asComponent(placeholders);
        Component comp = Component.join(JoinConfiguration.separator(Component.empty()),
                Arrays.asList(
                        prefix().asComponent(),
                        this.asComponent(placeholders)
                )
        );
        return comp;
    }

    private <T> T[] addItemToFrontOfArray(T[] originalArray, T itemToAdd) {
        T[] newArray = Arrays.copyOf(originalArray, originalArray.length + 1);
        System.arraycopy(originalArray, 0, newArray, 1, originalArray.length);
        newArray[0] = itemToAdd;
        return newArray;
    }
}