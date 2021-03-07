package net.natroutter.natlibs;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import net.natroutter.natlibs.commands.SoundTester;
import net.natroutter.natlibs.events.PlayerJumpEvent.PlayerJumpEventListener;
import net.natroutter.natlibs.handlers.EventManager;
import net.natroutter.natlibs.handlers.gui.GUIListener;
import net.natroutter.natlibs.utilities.SkullCreator;
import net.natroutter.natlibs.utilities.Utilities;
import net.natroutter.natlibs.utilities.libs.NATlogger.LoggerSettings;
import net.natroutter.natlibs.utilities.libs.NATlogger.NATlogger;

public class NATLibs extends JavaPlugin {
	
	private static JavaPlugin Instance; 
	private static PluginDescriptionFile pdf;
	
	private static EventManager eventManager;
	private static Utilities utils;
	private static SkullCreator SkullCreator;
	
	
	public static JavaPlugin getInstance() {return Instance;}
	public static PluginDescriptionFile getInfo() {return pdf;}
	
	public static EventManager getEventManager() {return eventManager;}
	public static Utilities getUtilities() {return utils;}
	public static NATlogger getNATLogger(LoggerSettings set) {return new NATlogger(set);}
	public static SkullCreator getSkullCreator() {return SkullCreator;}
	
	@Override
	public void onEnable() {
		Instance = this;
		pdf = this.getDescription();
		
		utils = new Utilities(this);
		eventManager = new EventManager();
		SkullCreator = new SkullCreator();
		
		eventManager.RegisterListeners(this,
				GUIListener.class, PlayerJumpEventListener.class
		);
		
		eventManager.RegisterCommands(this, SoundTester.class);

		utils.printBanner();
	}  
	 
	@Override
	public void onDisable() {
		NATLibs.Instance = null;
	}

	
	
}
