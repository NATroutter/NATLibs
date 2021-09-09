package net.natroutter.natlibs;

import net.natroutter.natlibs.commands.SoundTester;
import net.natroutter.natlibs.handlers.CommonHandler;

import net.natroutter.natlibs.utilities.Bungeecord.BungeeHandler;
import net.natroutter.natlibs.utilities.Bungeecord.BungeeListener;
import org.bukkit.plugin.java.JavaPlugin;

import net.natroutter.natlibs.events.PlayerJumpEvent.PlayerJumpEventListener;
import net.natroutter.natlibs.handlers.EventManager;
import net.natroutter.natlibs.handlers.gui.GUIListener;
import org.bukkit.plugin.messaging.Messenger;


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
		evm.RegisterListeners(CommonHandler.class);
		return this;
	}
}
