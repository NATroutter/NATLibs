package net.natroutter.natlibs.handlers;

import java.lang.reflect.Field;

import org.apache.commons.lang.ObjectUtils.Null;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.internal.Primitives;

import net.natroutter.natlibs.NATLibs;
import net.natroutter.natlibs.utilities.libs.RawFileManager;
import net.natroutter.natlibs.utilities.serialize.Serializer;
import net.natroutter.natlibs.utilities.serialize.Serializer.Type;

@SuppressWarnings({"unused", "deprecation"})

public class LangManager {

	private JavaPlugin pl;
	private String fileName = "lang.json";
	private RawFileManager rfm;
	private Object Lang;
	private String rawContent;
	private Serializer serializer;
	private Class<?> clazz;
	private Object Instance;
	
	public LangManager(JavaPlugin pl) {
		this.pl = pl;
		init();
	}
	
	public LangManager(JavaPlugin pl, String fileName) {
		this.pl = pl;
		this.fileName = fileName;
		init();
	}
	
	private void init() {
		rfm = new RawFileManager(pl, fileName);
		rawContent = rfm.readFile().replaceAll("&", "§");
		serializer = new Serializer(pl);
	}
	
	public Object get() {
		return Instance;
	}
	
	public LangManager load(Class<?> clazz) {
		this.clazz = clazz;
		try {
			this.Instance = clazz.newInstance();
			if (rfm.getFileCreated()) {
				rfm.writeFile(serializer.unSerialize(Type.GSON, clazz).replaceAll("§", "&"));
				Lang = Instance;
			} else {
				Lang = serializer.serialize(rawContent, Type.GSON, clazz);
			}
		} catch(Exception e) {
			Instance = null;
			Lang = null;
		}
		return this;
	}
	
	
}
