package net.natroutter.natlibs.handlers.configuration;

import com.google.gson.*;
import net.natroutter.natlibs.handlers.configuration.adapters.*;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Serializer {

	private final JavaPlugin pl;
	private final Gson gson;
	private Object Instance;
	private final ConsoleCommandSender console;
	private Class<?> clazz;

	ExclusionStrategy strategy = new ExclusionStrategy() {
		@Override
		public boolean shouldSkipClass(Class<?> clazz) {
			return false;
		}

		@Override
		public boolean shouldSkipField(FieldAttributes field) {
			return false;
		}
	};

	public Serializer(JavaPlugin pl, Class<?> clazz) {
		this.pl = pl;
		this.clazz = clazz;
		this.console = pl.getServer().getConsoleSender();

		GsonBuilder builder = new GsonBuilder();
		builder.setPrettyPrinting();
		builder.registerTypeAdapter(World.class, new WorldAdapter());
		builder.registerTypeAdapter(BlockState.class, new InterfaceAdapter());
		builder.registerTypeAdapter(ItemStack.class, new ItemStackAdapter());
		builder.registerTypeAdapter(clazz, new StrictSerializer<>(pl));
		builder.addDeserializationExclusionStrategy(new SuperclassExclusionStrategy());
		builder.addSerializationExclusionStrategy(new SuperclassExclusionStrategy());
		builder.addDeserializationExclusionStrategy(strategy);
		builder.enableComplexMapKeySerialization();
		builder.serializeNulls();
		this.gson = builder.create();
	}
	
	
	public Object toObject(String content) {
		try {
			Instance = clazz.newInstance();
			if (isValid(content)) {
				Instance = gson.fromJson(content, clazz);
			}
			return Instance;

		} catch (Exception e) {e.printStackTrace();}
		return null;
	}
	
	public String toString() {
		try {
			Instance = clazz.newInstance();
			return gson.toJson(Instance).replaceAll("§", "&");
		}catch(Exception ignored) {}
		return null;
	}

	private boolean isValid(String jsonString) {
		return jsonString.length() >= 1 && jsonString != null;
	}

}


