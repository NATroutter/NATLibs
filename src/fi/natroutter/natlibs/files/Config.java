package fi.natroutter.natlibs.files;

import fi.natroutter.natlibs.NATLibs;
import fi.natroutter.natlibs.configuration.IConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.File;

@AllArgsConstructor
public enum Config implements IConfig {

    CHECK_UPDATES("General.CheckUpdates"),
    USE_INTERNAL_TOOLS("General.useInternalTools"),
    ;


    @Getter
    final String path;

    @Override
    public File getDataFolder() {
        return NATLibs.getInstance().getDataFolder();
    }
}