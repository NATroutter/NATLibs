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

public final class NATLibs {

	private JavaPlugin plugin;
	private EventManager eventManager;

	public NATLibs(JavaPlugin plugin) {
		this.plugin	 = plugin;

		eventManager = new EventManager();

		eventManager.RegisterListeners(plugin,
				GUIListener.class,
				PlayerJumpEventListener.class
		);

	}

	public NATLibs enableSoundTester() {
		eventManager.RegisterCommands(plugin, SoundTester.class);
		return this;
	}
	
}
