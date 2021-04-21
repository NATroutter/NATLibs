package net.natroutter.natlibs.handlers;

import java.lang.reflect.Field;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class EventManager {

	JavaPlugin pl;

	public EventManager(JavaPlugin pl) {
		this.pl = pl;
	}

	public final void RegisterListeners(Class<?>... classes) {
		PluginManager pm = Bukkit.getServer().getPluginManager();
		for (Class<?> clazz : classes) {
			if (Arrays.asList(clazz.getInterfaces()).contains(Listener.class)) {
				try {
					pm.registerEvents((Listener)clazz.getDeclaredConstructor().newInstance(), pl);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public final void RegisterCommands(Class<?>... classes) {
		for (Class<?> clazz : classes) {
			if (clazz.getSuperclass().equals(Command.class)) {
				try {
					Command cmd = (Command)clazz.getDeclaredConstructor().newInstance();
					cmd.setName(clazz.getSimpleName());

					Field bukkitCommandMap = pl.getServer().getClass().getDeclaredField("commandMap");
					bukkitCommandMap.setAccessible(true);
					CommandMap commandMap = (CommandMap) bukkitCommandMap.get(pl.getServer());
					commandMap.register(pl.getName(), cmd);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}










