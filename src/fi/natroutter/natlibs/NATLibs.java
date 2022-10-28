package fi.natroutter.natlibs;

import fi.natroutter.natlibs.events.PlayerJumpEvent;
import fi.natroutter.natlibs.files.Config;
import fi.natroutter.natlibs.handlers.VersionChecker;
import fi.natroutter.natlibs.handlers.gui.GUIListener;
import fi.natroutter.natlibs.handlers.configuration.ConfigManager;

import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class NATLibs extends JavaPlugin {

	/*
	TODO
	 1. lisäää wandin teko class jota voi käyttää missävaan pluginissa eri wandit tunnistaa idllä joka määritetään kun uus wandi luodaan kun wandilla selectaa niin se call onwandselect event!

	 	WandManager manager = NATLibs.getWandManager();
		Wand wand = new wand(new UUID(), "name", material.wooden_shovel);
		manager.register(plugin, wand);

		public onWandSelect(WandSelectEvent e) {
			Player p = e.getPlayer();
			Wand wand = e.getWand();
			Side side = e.getSide();
			Selection area = e.getSelection();

			if (side.equals(Side.LEFT)) {

			} else if (side.equals(Side.RIGHT)) {

			}

		}

	 2. add new SQL database handler
	 3. add sqlite database mojangAPI:n
	*/


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
		pm.registerEvents(new PlayerJumpEvent.PlayerJumpEventListener(), this);

		new Metrics(this, 15070);
	}

}
