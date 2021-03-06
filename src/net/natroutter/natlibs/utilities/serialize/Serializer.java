package net.natroutter.natlibs.utilities.serialize;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import net.natroutter.natlibs.NATLibs;
import net.natroutter.natlibs.utilities.Utilities;

@SuppressWarnings({ "rawtypes", "deprecation", "serial"})
public class Serializer {

	public enum Type {
		GSON,YAML
	}
	
	private JavaPlugin pl;
	private Gson gson;
	private Object Instance;
	private ConsoleCommandSender console;
	
	public Serializer(JavaPlugin pl) {
		this.pl = pl;
		this.gson = new GsonBuilder().setPrettyPrinting().create();
		this.console = pl.getServer().getConsoleSender();
	}
	
	
	public Object serialize(String content, Type type, Class<?> clazz) {
		if (type == Type.GSON) {
			return serializeGSON(content, clazz);
			
		} else if (type == Type.YAML) {
			return serializeYAML(content, clazz);
		}
		return null;
	}
	
	public String unSerialize(Type type, Class<?> clazz) {
		if (type == Type.GSON) {
			return unSerializeGSON(clazz);
			
		} else if (type == Type.YAML) {
			return unSerializeYAML(clazz);
		}
		return null;
	}
	
	
	
	//YAML Serialize/Unserialize/Validation
	
	private String unSerializeYAML(Class<?> clazz) {

		return null;
	}
	
	private Object serializeYAML(String content, Class<?> clazz) {

		return null;
	}
	
	
	
	
	
	
	//GSON Serialize/Unserialize/Validation
	
	private String unSerializeGSON(Class<?> clazz) {
		try {
			
			Instance = clazz.newInstance();
			return gson.toJson(Instance);
			
		}catch(Exception e) {}
		return null;
	}
	
	private Object serializeGSON(String content, Class<?> clazz) {
		try {
			
			Instance = clazz.newInstance();
			if (isValid(clazz, content)) {
				Instance = gson.fromJson(content, clazz);
			}
			return Instance;
			
		} catch (Exception e) {e.printStackTrace();}
		return null;
	}

	
    private List<String> getClassPathsPart2(List<String> list, Class startClass, Class tempclass) {
        try {
        	for (Class c : tempclass.getClasses()) {
                String path = c.getCanonicalName().substring(c.getCanonicalName().lastIndexOf("." + startClass.getSimpleName() + "."));
                path = path.replaceFirst(startClass.getSimpleName() + ".", "");
                path = path.replaceFirst(".", "");
                Object obj = c.newInstance();

                for (Field field : obj.getClass().getFields()) {
                    String fieldName = field.getName();
                   	if (validFieldType(field.getType().getTypeName())) {
                   		list.add(path + "." + fieldName);
                   	}
                }
                
                if (c.getClasses().length != 0) {
                	getClassPathsPart2(list, startClass, c);
                }
            }
        } catch (Exception e) {}
        return list;
    }
	
	private List<String> getClassPathsPart1(List<String> list, Class<?> cls, String last) {
		try {
			Object obj = cls.newInstance();
			for(Field field : obj.getClass().getDeclaredFields()) {
				if (validFieldType(field.getType().getTypeName())) {
					list.add(field.getName());
				}
			}
		} catch(Exception e) {}
        return list;
    }
	
	private List<String> getClassPaths(Class<?> cls) {
		List<String> list = new ArrayList<String>();
		list.addAll(getClassPathsPart1(new ArrayList<String>(), cls, ""));
		list.addAll(getClassPathsPart2(new ArrayList<String>(), cls, cls));
		return list;
	}

	private class NamedObject {
		private String name;
		private JsonObject obj;
		NamedObject(String name, JsonObject obj) {
			this.name = name;this.obj = obj;
		}
		public String getName() { return name; }
		public JsonObject getObj() { return obj; }
	}
	
	private List<NamedObject> getJsonObjects(List<NamedObject> list, JsonObject obj) {
		for(Entry<String, JsonElement> el : obj.entrySet()) {
			JsonElement root = obj.get(el.getKey());
			if (root.isJsonObject()) {
				list.add(new NamedObject(el.getKey(), root.getAsJsonObject()));
			}
		}
		return list;
	}
	
	private List<String> getJsonPaths(List<String> list, JsonObject obj, String str) {
		for (NamedObject ob : getJsonObjects(new ArrayList<>(), obj)) {
			
			for(Entry<String, JsonElement> el : ob.getObj().entrySet()) {
				
				if (el.getValue().isJsonObject()) {
					JsonObject nOBJ = el.getValue().getAsJsonObject();
					
					str = str + ob.getName() + "." + el.getKey();
					getJsonPaths(list, nOBJ, str);
					
				} else {
					list.add(str);
					str = "";
				}
			}
		}
        return list;
    }

	private boolean isValid(Class<?> cls, String jsonString) {

		if (jsonString.length() < 1 || jsonString == null) {
			return false;
		}
		
		return true;
    }
	
	private Boolean validFieldType(String type) {
		switch (type) {
	        case "java.lang.String":
	            return true;
	        case "java.lang.Double":
	        	return true;
	        case "java.lang.Boolean":
	        	return true;
	        case "java.lang.Long":
	        	return true;
	        case "java.lang.Float":
	        	return true;
	        case "java.lang.Integer":
	        	return true;
	        case "java.lang.Short":
	        	return true;
	        case "java.lang.Byte":
	        	return true;
	        case "java.util.List":
	        	return true;
	    }
		return false;
	}
	
	private void JsonValitdationError(Exception e) {
		Utilities utils = NATLibs.getUtilities();
    	
		if (e instanceof JsonSyntaxException) {
			String line = utils.getRegExMatch("(?<=line )([0-9]{1,})(?= column)", e.getMessage());
	    	String column = utils.getRegExMatch("(?<= column )([0-9]{1,})(?= )", e.getMessage());
	    	
	    	console.sendMessage(" ");
	    	
	    	if (line != null && column != null) {
	    		console.sendMessage("§cInvalid configuraion in §4" + pl.getName() + "/Lang.json §con line §4" + line + "§c column §4" + column);
	    	}
	    	
		} else if (e instanceof GsonFieldExeption) {
			GsonFieldExeption ex = (GsonFieldExeption)e;
			
			console.sendMessage("§cInvalid configuraion in §4" + pl.getName() + "/Lang.json on " + ex.getField().getName());
	    	
		} else {
			console.sendMessage("§cInvalid configuraion in §4" + pl.getName() + "/Lang.json");
		}
    	
		console.sendMessage("§c(Using default config until fixed!)");
		console.sendMessage(" ");
	}
}


