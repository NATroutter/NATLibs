package fi.natroutter.natlibs.config;

import java.io.File;

public interface ILang extends IConfig {

    Language lang();

    @Override
    default File file() {
        File langFolder = new File(getPlugin().getDataFolder(), "langs");
        if (!langFolder.exists()) {
            langFolder.mkdirs();
        }
        return new File(langFolder, lang().getLangKey() + ".yml");
    }
}