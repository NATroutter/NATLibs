package net.natroutter.natlibs.handlers.LangHandler.language;

import net.natroutter.natlibs.NATLibs;
import net.natroutter.natlibs.handlers.LangHandler.TranslationTemplate;
import net.natroutter.natlibs.handlers.LangHandler.language.translation.Translation;
import net.natroutter.natlibs.handlers.gui.Consumer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.List;
import java.util.logging.Level;

public class LangManager {

    private ConsoleCommandSender console;
    private JavaPlugin plugin;
    private Translator translator;
    private Language language;
    private boolean messages = true;

    public void send(CommandSender sender, TranslationTemplate... trans) {
        StringBuilder message = new StringBuilder();
        for (TranslationTemplate temp : trans) {
            message.append(get(temp));
        }
        sender.sendMessage(message.toString());
    }

    public void sendList(CommandSender sender, TranslationTemplate trans) {
        for (String line : getList(trans)) {
            sender.sendMessage(line);
        }
    }

    public List<String> getList(TranslationTemplate trans) {
        return getTranslation(trans).colour();
    }

    public String get(TranslationTemplate trans) {
        return getTranslation(trans).colour().get(0);
    }

    public Translation getTranslation(TranslationTemplate trans) {
        return translator.getTranslationFor(language, trans.getKey());
    }

    public void setMessages(boolean messages) {
        this.messages = messages;
    }

    public LangManager(JavaPlugin plugin, Language lang) {
        this.plugin = plugin;
        this.language = lang;
        this.console = Bukkit.getConsoleSender();

        File file1 = new File(plugin.getDataFolder() + "/lang/");
        file1.mkdirs();

        for (Language language : Language.values()) {
            try {
                saveResource("lang/" + language.getKey().getCode() + ".yml", false, c->{
                    if (!c){return;}
                    if (!messages) {return;}
                    console.sendMessage("§4["+plugin.getName()+"][Lang] §cGenerated " + language.getKey().getCode() + ".yml");
                });
            } catch (IllegalArgumentException e) {
                if (NATLibs.getConf().debug) {
                    e.printStackTrace();
                }
            }
        }

        this.translator = Translator.of(plugin);
    }
    public void saveResource(String resourcePath, boolean replace, Consumer<Boolean> consumer) {
        if (resourcePath == null || resourcePath.equals("")) {
            throw new IllegalArgumentException("ResourcePath cannot be null or empty");
        }

        resourcePath = resourcePath.replace('\\', '/');
        InputStream in = plugin.getResource(resourcePath);
        if (in == null) {
            throw new IllegalArgumentException("The embedded resource '" + resourcePath + "' cannot be found in " + plugin.getName());
        }

        File outFile = new File(plugin.getDataFolder(), resourcePath);
        int lastIndex = resourcePath.lastIndexOf('/');
        File outDir = new File(plugin.getDataFolder(), resourcePath.substring(0, lastIndex >= 0 ? lastIndex : 0));

        if (!outDir.exists()) {
            outDir.mkdirs();
        }

        try {
            if (!outFile.exists() || replace) {
                OutputStream out = new FileOutputStream(outFile);
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                out.close();
                in.close();
                consumer.accept(true);
            }
        } catch (IOException ex) {
            plugin.getLogger().log(Level.SEVERE, "Could not save " + outFile.getName() + " to " + outFile, ex);
            consumer.accept(false);
        }
    }
}
