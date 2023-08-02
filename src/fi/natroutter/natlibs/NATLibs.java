package fi.natroutter.natlibs;

import fi.natroutter.natlibs.Tools.LocateBlock.LocateBlock;
import fi.natroutter.natlibs.Tools.MagicWand;
import fi.natroutter.natlibs.Tools.MiniMessageViewer;
import fi.natroutter.natlibs.events.PlayerJumpEvent;
import fi.natroutter.natlibs.files.Config;
import fi.natroutter.natlibs.handlers.CustomResolver;
import fi.natroutter.natlibs.handlers.Hook;
import fi.natroutter.natlibs.handlers.VersionChecker;

import fi.natroutter.natlibs.handlers.database.YamlDatabase;
import fi.natroutter.natlibs.handlers.fancyfont.FancyFont;
import fi.natroutter.natlibs.handlers.fancyfont.FontRegistery;
import fi.natroutter.natlibs.handlers.fancyfont.fonts.BlockyFont;
import fi.natroutter.natlibs.handlers.fancyfont.fonts.EmptyCircle;
import fi.natroutter.natlibs.handlers.fancyfont.fonts.FilledCircle;
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
	*/

	@Getter
	private static YamlDatabase database;

	@Getter
	private static NATLibs instance;

	@Getter
	private static Hook papiHook;

	@Override
	public void onEnable() {
		instance = this;
		database = new YamlDatabase(this);

		new CustomResolver();

		papiHook = new Hook.Builder(this, "PlaceholderAPI", true)
				.setHookedMessage(" §a+ §7<plugin> hooked succesfully!")
				.setHookingFailedMessage(" §4- §7<plugin> hooking failed!")
				.build();

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

		FancyFont.register(FontRegistery.BLOCKY, new BlockyFont());
		FancyFont.register(FontRegistery.FILLED_CIRCLE, new FilledCircle());
		FancyFont.register(FontRegistery.EMPTY_CIRCLE, new EmptyCircle());

		if (Config.USE_INTERNAL_TOOLS.asBoolean()) {
			MiniMessageViewer mmv = new MiniMessageViewer();
			MagicWand mw = new MagicWand();
			LocateBlock locateBlock = new LocateBlock(this);

			Bukkit.getCommandMap().register("natlibs", mmv);
			pm.registerEvents(mmv, this);

			Bukkit.getCommandMap().register("natlibs", mw);
			pm.registerEvents(mw, this);

			Bukkit.getCommandMap().register("natlibs", locateBlock);
			pm.registerEvents(locateBlock, this);
		}

		if (Demo.demo) Bukkit.getCommandMap().register("natlibs", new Demo());

		new Metrics(this, 15070);
	}

}
