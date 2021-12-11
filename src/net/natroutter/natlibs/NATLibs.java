package net.natroutter.natlibs;

import net.natroutter.natlibs.utilities.Bungeecord.BungeeHandler;
import net.natroutter.natlibs.utilities.Bungeecord.BungeeListener;
import org.bukkit.plugin.java.JavaPlugin;

import net.natroutter.natlibs.events.PlayerJumpEvent.PlayerJumpEventListener;
import net.natroutter.natlibs.handlers.EventManager;
import net.natroutter.natlibs.handlers.gui.GUIListener;
import org.bukkit.plugin.messaging.Messenger;

public interface NATLibs {

	default void registerLibrary(JavaPlugin plugin) {
		EventManager evm = new EventManager(plugin);
		evm.RegisterListeners(
				GUIListener.class,
				PlayerJumpEventListener.class
		);
	}

	default BungeeHandler createBungeecordHandler(JavaPlugin plugin) {
		return createBungeecordHandler(plugin, null, false);
	}
	default BungeeHandler createBungeecordHandler(JavaPlugin plugin, Integer updateIntervalTick) {
		return createBungeecordHandler(plugin, updateIntervalTick, false);
	}
	default BungeeHandler createBungeecordHandler(JavaPlugin plugin, Integer updateIntervalTick, boolean debug) {
		BungeeHandler handler = new BungeeHandler(plugin, updateIntervalTick);
		Messenger messanger = plugin.getServer().getMessenger();
		messanger.registerOutgoingPluginChannel(plugin, "BungeeCord");
		messanger.registerIncomingPluginChannel(plugin, "BungeeCord", new BungeeListener(handler));
		return handler;
	}
}
