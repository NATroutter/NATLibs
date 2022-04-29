package net.natroutter.natlibs.handlers.configuration.adapters;

import com.google.common.reflect.ClassPath;
import com.google.gson.*;
import com.google.gson.annotations.SerializedName;
import net.natroutter.natlibs.handlers.configuration.configExtension;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StrictSerializer< T > implements JsonDeserializer<T> {

    private JavaPlugin plugin;
    private ConsoleCommandSender console;

    public StrictSerializer(JavaPlugin plugin) {
        this.plugin = plugin;
        this.console = plugin.getServer().getConsoleSender();
    }

    @Override
    public T deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
        T pojo = new Gson().fromJson(je, type);
        //System.out.println("test: " + new Gson().toJson(pojo));
        return pojo;
    }

    public static void checkConfig(Object obj) {
        for (Field declaredField : obj.getClass().getDeclaredFields()) {
            if (declaredField == null) {
                continue;
            }
            if (!obj.getClass().isAssignableFrom(configExtension.class)) {
                System.out.println(declaredField.getName());
            }
        }
        for (Class<?> declaredClass : obj.getClass().getDeclaredClasses()) {
            if (declaredClass.isAssignableFrom(configExtension.class)) {
                checkConfig(declaredClass);
            }
        }
    }

    public static void aaaa(Object pojo) {
        try {
            ClassPath cp = ClassPath.from(pojo.getClass().getClassLoader());
            //Commands
            for (ClassPath.ClassInfo classInfo : cp.getTopLevelClassesRecursive(pojo.getClass().getPackageName())) {
                Class<?> c = classInfo.load();
                System.out.println("DEBUG: " + c.getPackageName() + " - " + c.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    List<String> list = new ArrayList<>();
    private void process(Object pojo) {
        String packageName = pojo.getClass().getPackageName();
        for (Field field : pojo.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                if (field.getType().getPackageName().startsWith(packageName)) {
                    list.add(field.getName());

                    if (field.get(pojo) == null) {
                        list.add(field.getName());
                        console.sendMessage("§4["+plugin.getName()+"][Config] §7Missing config at: §c" + String.join(".", list));
                        list.clear();
                        return;
                    }

                    process(field.get(pojo));
                } else {
                    if (field.get(pojo) == null || field.get(pojo).toString().length() < 1) {
                        list.add(field.getName());
                        console.sendMessage("§4["+plugin.getName()+"][Config] §7Missing config at: §c" + String.join(".", list));
                        list.clear();
                    }
                }
            } catch (IllegalAccessException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}