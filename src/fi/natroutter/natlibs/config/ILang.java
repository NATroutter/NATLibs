package fi.natroutter.natlibs.config;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

import java.io.File;
import java.util.Arrays;

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

    @Override
    default String resourceLocation() {
        return "langs/" + lang().getLangKey() + ".yml";
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

}