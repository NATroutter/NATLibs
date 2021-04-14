package net.natroutter.natlibs;

import net.natroutter.natlibs.handlers.Database.*;
import net.natroutter.natlibs.handlers.Database.enums.DatabaseDriver;
import net.natroutter.natlibs.handlers.Database.enums.FieldType;
import net.natroutter.natlibs.handlers.Database.objects.*;
import org.bukkit.command.ConsoleCommandSender;
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

import java.util.ArrayList;

//TODO
//Lisää eventhandleriin tarkistus  - IllegalAccessException
//

public class NATLibs {

	private static JavaPlugin Instance;

	public static JavaPlugin getInstance() {return Instance;}

	public NATLibs(JavaPlugin plugin, boolean useSoundTester) {
		Instance = plugin;

		EventManager eventManager = new EventManager();
		eventManager.RegisterListeners(plugin,
				GUIListener.class, PlayerJumpEventListener.class
		);

		if (useSoundTester) {
			eventManager.RegisterCommands(plugin, SoundTester.class);
		}

	}
	
}
