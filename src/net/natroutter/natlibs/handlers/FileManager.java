package net.natroutter.natlibs.handlers;

import org.bukkit.plugin.java.JavaPlugin;

import net.natroutter.natlibs.utilities.libs.RawFileManager;
import net.natroutter.natlibs.utilities.serialize.Serializer;
import net.natroutter.natlibs.utilities.serialize.Serializer.Type;

@SuppressWarnings({"unused", "deprecation"})
public class FileManager {

	private final JavaPlugin pl;
	private String fileName = "lang.json";
	private RawFileManager rfm;
	private Object obj;
	private String rawContent;
	private Serializer serializer;
	private Class<?> clazz;
	private Object Instance;

	public enum CfgType {
		Config("Config.json"),
		Lang("Lang.json");

		private String file;
		CfgType(String file) { this.file = file; }
		public String getFile() { return file; }
	}

	public FileManager(JavaPlugin pl) {
		this.pl = pl;
		init();
	}

	public FileManager(JavaPlugin pl, String fileName) {
		this.pl = pl;

		if (!fileName.endsWith(".json")) {
			this.fileName = fileName + ".json";
		} else {
			this.fileName = fileName;
		}
		init();
	}

	public FileManager(JavaPlugin pl, CfgType type) {
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
		this.clazz = clazz;
		try {
			this.Instance = clazz.getDeclaredConstructor().newInstance();
			if (rfm.getFileCreated()) {
				rfm.writeFile(serializer.unSerialize(Type.GSON, clazz).replaceAll("§", "&"));
				obj = Instance;
			} else {
				obj = serializer.serialize(rawContent, Type.GSON, clazz);
			}
			return type.cast(obj);
		} catch(Exception e) {
			Instance = null;
			obj = null;
		}
		return null;
	}
	
	
}
