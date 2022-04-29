package net.natroutter.natlibs;

import net.natroutter.natlibs.files.Config;
import net.natroutter.natlibs.handlers.VersionChecker;
import net.natroutter.natlibs.handlers.configuration.ConfigManager;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import net.natroutter.natlibs.events.PlayerJumpEvent.PlayerJumpEventListener;
import net.natroutter.natlibs.handlers.gui.GUIListener;

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
