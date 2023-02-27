package fi.natroutter.natlibs.files;

import fi.natroutter.natlibs.NATLibs;
import fi.natroutter.natlibs.config.CfgType;
import fi.natroutter.natlibs.config.IConfig;
import fi.natroutter.natlibs.config.SimpleYml;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
public enum Config implements IConfig {

    CHECK_UPDATES("General.CheckUpdates"),
    ;


    @Getter
    final String path;

    @Override
    public JavaPlugin getPlugin() {
        return NATLibs.getInstance();
    }
}