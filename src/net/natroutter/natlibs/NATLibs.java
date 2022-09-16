package net.natroutter.natlibs;

import net.natroutter.natlibs.events.PlayerJumpEvent.PlayerJumpEventListener;
import net.natroutter.natlibs.files.Config;
import net.natroutter.natlibs.handlers.VersionChecker;
import net.natroutter.natlibs.handlers.configuration.ConfigManager;
import net.natroutter.natlibs.handlers.gui.GUIListener;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.dependency.Libraries;
import org.bukkit.plugin.java.annotation.dependency.Library;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.LogPrefix;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.Website;
import org.bukkit.plugin.java.annotation.plugin.author.Author;

@Plugin(name = "NATLibs", version = "2.0.12")
@Description("NATroutter's library for plugin Developement")
@Author("NATroutter")
@Website("https://plugins.nat.gg/plugin/natlibs/")
@LogPrefix("NATLibs")
@Libraries({
		@Library("org.jsoup:jsoup:1.15.3")
})
public class NATLibs extends JavaPlugin {

	//TODO
	// lisäää wandin teko class jota voi käyttää missävaan pluginissa eri wandit tunnistaa idllä joka määritetään kun uus wandi luodaan
	// kun wandilla selectaa niin se call onwandselect event!
	//TODO
	// add new database handler

	private static Config config;
	public static Config getConf() {return config;}

	@Override
	public void onEnable() {

		ConsoleCommandSender console = Bukkit.getConsoleSender();
		config = new ConfigManager(this).load(Config.class);

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
