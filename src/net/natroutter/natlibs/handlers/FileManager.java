package net.natroutter.natlibs.handlers;

import net.natroutter.natlibs.objects.ConfType;
import org.bukkit.plugin.java.JavaPlugin;

import net.natroutter.natlibs.utilities.libs.RawFileManager;
import net.natroutter.natlibs.utilities.serialize.Serializer;
import net.natroutter.natlibs.utilities.serialize.Serializer.Type;

public class FileManager {

	private final JavaPlugin pl;
	private final String fileName;
	private RawFileManager rfm;
	private String rawContent;
	private Serializer serializer;


	public FileManager(JavaPlugin pl, String fileName) {
		this.pl = pl;

		if (!fileName.endsWith(".json")) {
			this.fileName = fileName + ".json";
		} else {
			this.fileName = fileName;
		}
		init();
	}

	public FileManager(JavaPlugin pl, ConfType type) {
		this.pl = pl;
		this.fileName = type.getFile();
		init();
	}
	
	private void init() {
		rfm = new RawFileManager(pl, fileName);
		rawContent = rfm.readFile().replaceAll("&", "§");
		serializer = new Serializer(pl);
	}


	public <T> T load(Class<T> type) {
		Object obj = null;
		Object instance = null;
		try {
			instance = type.getDeclaredConstructor().newInstance();
			if (rfm.getFileCreated()) {
				rfm.writeFile(serializer.unSerialize(Type.GSON, type).replaceAll("§", "&"));
				obj = instance;
			} else {
				obj = serializer.serialize(rawContent, Type.GSON, type);
			}
			return type.cast(obj);
		} catch(Exception ignored) {}
		return null;
	}
	
	
}
