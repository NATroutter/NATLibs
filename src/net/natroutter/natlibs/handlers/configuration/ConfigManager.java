package net.natroutter.natlibs.handlers.configuration;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import net.natroutter.natlibs.utilities.libs.RawFileManager;

public class ConfigManager {

	private final JavaPlugin pl;
	private final String fileName = "config.json";
	private RawFileManager rfm;
	private String rawContent;


	public ConfigManager(JavaPlugin pl) {
		this.pl = pl;
		rfm = new RawFileManager(pl, fileName);
		rawContent = ChatColor.translateAlternateColorCodes('&', rfm.readFile());
	}


	public <T> T load(Class<T> type) {
		Serializer ser = new Serializer(pl, type);
		Object obj = null;
		Object instance = null;
		try {
			instance = type.getDeclaredConstructor().newInstance();
			if (rfm.getFileCreated()) {
				rfm.writeFile(ser.toString());
				obj = instance;
			} else {
				obj = ser.toObject(rawContent);
			}
			return type.cast(obj);
		} catch(Exception ignored) {}
		return null;
	}
	
	
}
