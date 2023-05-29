package fi.natroutter.natlibs.config;

import fi.natroutter.natlibs.handlers.guibuilder.Rows;
import fi.natroutter.natlibs.utilities.Utilities;
import lombok.SneakyThrows;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface IConfig {

    MiniMessage mm = MiniMessage.miniMessage();
    LegacyComponentSerializer lcs = LegacyComponentSerializer.legacyAmpersand();

    HashMap<String, SimpleYml> saved = new HashMap<>();

    String getPath();
    JavaPlugin getPlugin();

    private String identifier() {
        return getPlugin().getName().toLowerCase() + "-" + fileName();
    }

    default String fileName() {
        return getClass().getSimpleName().toLowerCase();
    }

    default File file() {
        return new File(getPlugin().getDataFolder(), fileName() + ".yml");
    }

    default void reloadFile() {
        saved.put(identifier(), new SimpleYml(getPlugin(), file()));
    }

    default SimpleYml yml() {
        if (!saved.containsKey(identifier())) {
            saved.put(identifier(), new SimpleYml(getPlugin(), file()));
        }
        return saved.get(identifier());

//        Bukkit.broadcastMessage("§dTest: " +fileName() + " - " + file().getName());
//        return new SimpleYml(getPlugin(), file());
    }

    @SneakyThrows
    private <T> T getObj(String name, Class<T> clazz){
        T obj = clazz.getConstructor().newInstance();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            Object val = yml().get(getPath() + "." + name + "." + field.getName());
            field.set(obj, val);
        }
        return obj;
    }

    @SneakyThrows
    default <T> List<T> asObjectList(Class<T> clazz) {
        return yml().getConfigurationSection(getPath()).getKeys(false)
                .stream()
                .map(key -> getObj(key, clazz))
                .toList();
    }

    default String asString() {return yml().getString(getPath());}
    default int asInteger() {return yml().getInt(getPath());}
    default long asLong() {return yml().getLong(getPath());}
    default double asDouble() {return yml().getDouble(getPath());}
    default boolean asBoolean() {return yml().getBoolean(getPath());}
    default List<String> asStringList() {return yml().getStringList(getPath());}
    default Material asMaterial() {return Material.getMaterial(yml().getString(getPath()));}

    default Rows asRows() {return Rows.fromString(yml().getString(getPath()));}

    default Component asComponent(){return asComponent(null);}
    default List<Component> asComponentList(){return asComponentList(null);}

    default String asLegacy(){return asLegacy(null);}
    default List<String> asLegacyList(){return asLegacyList(null);}

    default List<Component> asComponentList(List<TagResolver> tagResolvers) {
        List<String> values = yml().getStringList(getPath());

        return values.stream().map(entry-> {
            if (tagResolvers != null && !tagResolvers.isEmpty()) {
                return Utilities.translate(entry, tagResolvers);
            }
            return Utilities.translate(entry);
        }).toList();
    }

    default Component asComponent(List<TagResolver> tagResolvers){
        String entry = yml().getString(getPath());
        if (entry != null) {
            return Utilities.translate(entry, tagResolvers);
        }
        return Component.text(" [Invalid value in config: " + this.getPath() + "] ");
    }

    default String asLegacy(List<TagResolver> tagResolvers){
        String entry = yml().getString(getPath());
        if (entry != null) {
            return Utilities.legacy(Utilities.translate(entry, tagResolvers));
        }
        return " [Invalid value in config: " + this.getPath() + "] ";
    }

    default List<String> asLegacyList(List<TagResolver> tagResolvers){
        List<String> values = yml().getStringList(getPath());
        return values.stream().map(entry -> Utilities.legacy(Utilities.translate(entry, tagResolvers))).toList();
    }

}