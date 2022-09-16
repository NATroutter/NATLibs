package net.natroutter.natlibs.handlers.langHandler.language;


import net.natroutter.natlibs.handlers.langHandler.HookedPlugin;
import net.natroutter.natlibs.handlers.langHandler.language.key.TranslationKey;
import net.natroutter.natlibs.handlers.langHandler.language.translation.Translation;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class Translator {

    private final Plugin plugin;
    private final Language defaultLanguage;

    private File fallback;
    private boolean debug;
    private final HookedPlugin hook;

    private Translator(@NotNull Plugin plugin, @NotNull String folderName, @NotNull Language defaultLanguage) {

        this.plugin = plugin;
        this.defaultLanguage = defaultLanguage;

        this.fallback = new File(plugin.getDataFolder() + File.separator + folderName + File.separator + defaultLanguage.getKey().getCode() + ".yml");

        File folder = fallback.getAbsoluteFile().getParentFile();
        this.hook = new HookedPlugin(plugin, folder, fallback);

        if (hook.isDebug()) plugin.getLogger().info(folder.toString());
        for (Language language : Language.values()) {
            File file = new File(folder.toString() + File.separator + language.getKey().getCode() + ".yml");
            if (!file.exists() || !file.getName().contains(language.getKey().getCode())) {
                String reason = !file.exists() ? "Does not exist" : "File name is incorrect";
                if (hook.isDebug()) Bukkit.getConsoleSender().sendMessage("["+plugin.getName()+"][Lang] Language file could not be loaded: " + file.getName() + ". Reason: " + reason);
                continue;
            }
            hook.addCachedLanguage(language, YamlConfiguration.loadConfiguration(file));
            debug("["+plugin.getName()+"][Lang] Loaded language '" + language.getKey().getCode() + "'.");
        }


    }

    /**
     * Create a new translator.
     * @param plugin your plugin instance
     * @see #of(Plugin, Language)
     * @see #of(Plugin, String, Language)
     */
    public static Translator of(@NotNull Plugin plugin) {
        return of(plugin, "lang", Language.ENGLISH);
    }

    /**
     * Create a new translator.
     * @param plugin your plugin instance
     * @param defaultLanguage the default language you wish to use
     * @see #of(Plugin)
     * @see #of(Plugin, String, Language)
     */
    public static Translator of(@NotNull final Plugin plugin, @NotNull final Language defaultLanguage) {
        return of(plugin, "lang", defaultLanguage);
    }

    /**
     * Create a new translator.
     * @param plugin your plugin instance
     * @param folderName the folder you wish to use for language files, this should match your resources folder
     * @param defaultLanguage the default language you wish to use
     * @see #of(Plugin)
     * @see #of(Plugin, Language)
     */
    public static Translator of(@NotNull Plugin plugin, @NotNull String folderName, @NotNull Language defaultLanguage) {
        return new Translator(plugin, folderName, defaultLanguage);
    }

    /**
     * Sets the fallback file
     * @param fallback fallback file
     * @return {@link Translator} instance
     */
    public Translator setFallback(File fallback) {
        this.fallback = fallback;
        return this;
    }

    @NotNull
    public HookedPlugin getHook() {
        return hook;
    }

    /**
     * Gets the translation for a player.
     * <p></p>
     * If the player's locale has no existing translation,
     *  then this will use the default language provided in the constructor.
     * <p></p>
     * This will also attempt to resolve the type within the config.
     * If it is a list, then that shall be used, otherwise it will be a normal string.
     * @param targetLanguage the language what you want to use
     * @param key the translation key
     * @return a provided translation
     */
    public Translation getTranslationFor(@NotNull Language targetLanguage, @NotNull TranslationKey key) {
        String lang = fallback.getAbsoluteFile().getParentFile().toString();
        File file = new File(lang + File.separator + targetLanguage.getKey().getCode() + ".yml");
        FileConfiguration config = file.exists()
                ? hook.getCachedLanguages().get(targetLanguage)
                : hook.getCachedLanguages().get(defaultLanguage);

        if (config == null || config.get(key.getKey()) == null) {
            if (hook.isDebug()) {
                String text = "["+plugin.getName()+"][Lang] Translation was requested, but path did not exist in '%s'! Try regenerating language files?";
                Bukkit.getConsoleSender().sendMessage(String.format(text, file.exists() ? targetLanguage : defaultLanguage));
            }
            Bukkit.getConsoleSender().sendMessage("§4["+plugin.getName()+"][Lang] §7Translation not found! | \"" + key.getKey() + "\" in " + targetLanguage.getKey().getCode() +".yml ");
            return Translation.of(targetLanguage, " §c[Translation not found!]§r ", false);
        }

        final Translation translation;
        if (config.isList(key.getKey())) {
            translation = Translation.of(targetLanguage, config.getStringList(key.getKey()), true);
        } else {
            translation = Translation.of(targetLanguage, config.getString(key.getKey()), true);
        }
        return translation;
    }


    @NotNull
    public File getFallback() {
        return fallback;
    }

    public boolean isDebug() {
        return debug;
    }

    public Translator debug(boolean debug) {
        this.debug = debug;
        return this;
    }

    private void debug(String info) {
        if (isDebug()) Bukkit.getConsoleSender().sendMessage("["+plugin.getName()+"][Lang] " + info);
    }


    private int getVersionNumber() {
        String[] split = Bukkit.getBukkitVersion().split("-")[0].split("\\.");
        return Integer.parseInt(split[1]);
    }
}
