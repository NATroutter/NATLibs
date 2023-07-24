package fi.natroutter.natlibs.files;

import fi.natroutter.natlibs.NATLibs;
import fi.natroutter.natlibs.config.IConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@AllArgsConstructor
public enum Config implements IConfig {

    CHECK_UPDATES("General.CheckUpdates"),
    USE_INTERNAL_TOOLS("General.useInternalTools"),
    ;


    @Getter
    final String path;

    @Override
    public JavaPlugin getPlugin() {
        return NATLibs.getInstance();
    }
}