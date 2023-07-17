package fi.natroutter.natlibs;

import fi.natroutter.natlibs.events.PlayerJumpEvent;
import fi.natroutter.natlibs.files.Config;
import fi.natroutter.natlibs.handlers.VersionChecker;

import fi.natroutter.natlibs.handlers.guibuilder.GUIListener;
import fi.natroutter.natlibs.handlers.wandmanager.WandSelectEvent;
import lombok.Getter;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class NATLibs extends JavaPlugin {

	/*
	TODO
	 2. add new SQL database handler
	 3. add sqlite database mojangAPI:n
	*/


	@Getter
	private static NATLibs instance;

	@Override
	public void onEnable() {
		instance = this;

		ConsoleCommandSender console = Bukkit.getConsoleSender();

		if (Config.CHECK_UPDATES.asBoolean()) {
			VersionChecker checker = new VersionChecker(this);
			if (checker.isSuccess() && checker.hasUpdate()) {
				console.sendMessage("§4["+this.getName()+"][Updater] §7New update aviable §c" + checker.getNewVersion() + " §8(§7You are using§8: §c"+checker.getCurrentVersion()+"§8)");
				console.sendMessage("§4["+this.getName()+"][Updater] §7Grap the newest version from§8: §c" + checker.getURL());
			}
		}

		PluginManager pm = Bukkit.getPluginManager();

		pm.registerEvents(new PlayerJumpEvent.PlayerJumpEventListener(), this);
		pm.registerEvents(new WandSelectEvent.WandSelectEventListener(), this);
		pm.registerEvents(new GUIListener(), this);

		new Metrics(this, 15070);

		if (Demo.demo) Bukkit.getCommandMap().register("natlibs", new Demo());

	}


}
