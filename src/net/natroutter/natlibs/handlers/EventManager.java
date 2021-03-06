package net.natroutter.natlibs.handlers;

import java.lang.reflect.Field;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("deprecation")
public class EventManager {

	
	public void RegisterListeners(JavaPlugin pl, Class<?>... classes) {
		PluginManager pm = Bukkit.getServer().getPluginManager();
		
		for (Class<?> clazz : classes) {
			if (Arrays.asList(clazz.getInterfaces()).contains(Listener.class)) {

				try {
					pm.registerEvents((Listener)clazz.newInstance(), pl);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void RegisterCommands(JavaPlugin pl, Class<?>... classes) {
		
		for (Class<?> clazz : classes) {
			if (clazz.getSuperclass().equals(Command.class)) {
				
				try {
					Command cmd = (Command)clazz.newInstance();
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










