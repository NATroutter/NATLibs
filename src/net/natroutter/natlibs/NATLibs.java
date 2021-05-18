package net.natroutter.natlibs;

import net.natroutter.natlibs.commands.SoundTester;
import net.natroutter.natlibs.handlers.Database.*;
import net.natroutter.natlibs.handlers.Database.enums.DatabaseDriver;
import net.natroutter.natlibs.handlers.Database.enums.FieldType;
import net.natroutter.natlibs.handlers.Database.objects.*;
import net.natroutter.natlibs.utilities.Bungeecord.BungeeHandler;
import net.natroutter.natlibs.utilities.Bungeecord.BungeeListener;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import net.natroutter.natlibs.events.PlayerJumpEvent.PlayerJumpEventListener;
import net.natroutter.natlibs.handlers.EventManager;
import net.natroutter.natlibs.handlers.gui.GUIListener;
import net.natroutter.natlibs.utilities.SkullCreator;
import net.natroutter.natlibs.utilities.Utilities;
import net.natroutter.natlibs.utilities.libs.NATlogger.LoggerSettings;
import net.natroutter.natlibs.utilities.libs.NATlogger.NATlogger;
import org.bukkit.plugin.messaging.Messenger;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.ArrayList;

public final class NATLibs {

	private EventManager evm;
	private JavaPlugin plugin;

	public NATLibs(JavaPlugin plugin) {
		this.plugin = plugin;
		evm = new EventManager(plugin);
		evm.RegisterListeners(
				GUIListener.class,
				PlayerJumpEventListener.class
		);
	}

	public BungeeHandler createBungeecordHandler() {return createBungeecordHandler(null, false);}
	public BungeeHandler createBungeecordHandler(Integer updateIntervalTick) {return createBungeecordHandler(updateIntervalTick, false);}
	public BungeeHandler createBungeecordHandler(Integer updateIntervalTick, boolean debug) {
		BungeeHandler handler = new BungeeHandler(plugin, updateIntervalTick);
		Messenger messanger = plugin.getServer().getMessenger();
		messanger.registerOutgoingPluginChannel(plugin, "BungeeCord");
		messanger.registerIncomingPluginChannel(plugin, "BungeeCord", new BungeeListener(handler));
		return handler;
	}

	public NATLibs enableSoundTester() {
		evm.RegisterCommands(SoundTester.class);
		return this;
	}
}
