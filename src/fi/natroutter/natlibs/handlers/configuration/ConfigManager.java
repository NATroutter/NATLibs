package fi.natroutter.natlibs.handlers.configuration;

import fi.natroutter.natlibs.utilities.RawFileManager;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigManager {

	private final JavaPlugin pl;
	private final String fileName = "config.json";
	private final RawFileManager rfm;
	private final String rawContent;


	public ConfigManager(JavaPlugin pl) {
		this.pl = pl;
		rfm = new RawFileManager(pl, fileName);
		rawContent = ChatColor.translateAlternateColorCodes('&', rfm.readFile());
	}


	public <T> T load(Class<T> type) {
		Serializer ser = new Serializer(pl, type);
		Object obj;
		Object instance;
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
