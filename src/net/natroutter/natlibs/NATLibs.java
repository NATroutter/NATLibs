package net.natroutter.natlibs;

import net.natroutter.natlibs.files.Config;
import net.natroutter.natlibs.handlers.LangHandler.TranslationTemplate;
import net.natroutter.natlibs.handlers.LangHandler.language.Language;
import net.natroutter.natlibs.handlers.LangHandler.language.Translator;
import net.natroutter.natlibs.handlers.LangHandler.language.key.TranslationKey;
import net.natroutter.natlibs.handlers.LangHandler.language.langManager;
import net.natroutter.natlibs.handlers.LangHandler.language.translation.Translation;
import net.natroutter.natlibs.handlers.VersionChecker;
import net.natroutter.natlibs.handlers.configuration.ConfigManager;
import net.natroutter.natlibs.handlers.gui.Consumer;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import net.natroutter.natlibs.events.PlayerJumpEvent.PlayerJumpEventListener;
import net.natroutter.natlibs.handlers.gui.GUIListener;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.Locale;
import java.util.logging.Level;

public class NATLibs extends JavaPlugin {

	//TODO
	// lisäää wandin teko class jota voi käyttää missävaan pluginissa eri wandit tunnistaa idllä joka määritetään kun uus wandi luodaan
	// kun wandilla selectaa niin se call onwandselect event!
	//TODO
	// add new database handler

	@Override
	public void onEnable() {

		ConsoleCommandSender console = Bukkit.getConsoleSender();
		Config config = new ConfigManager(this).load(Config.class);

		if (config.checkUpdates) {
			VersionChecker checker = new VersionChecker(this);
			if (checker.isSuccess()) {
				if (checker.hasUpdate()) {
					console.sendMessage("§4["+this.getName()+"][Updater] §7New update aviable §c" + checker.getNewVersion() + " §8(§7You are using§8: §c"+checker.getCurrentVersion()+"§8)");
					console.sendMessage("§4["+this.getName()+"][Updater] §7Grap the newest version from§8: §c" + checker.getURL());
				}
			}
		}

		PluginManager pm = Bukkit.getPluginManager();

		pm.registerEvents(new GUIListener(), this);
		pm.registerEvents(new PlayerJumpEventListener(), this);

		new Metrics(this, 15070);
	}

}
