package fi.natroutter.natlibs.config;

import fi.natroutter.natlibs.handlers.guibuilder.Rows;
import fi.natroutter.natlibs.utilities.Colors;
import fi.natroutter.natlibs.utilities.Utilities;
import lombok.SneakyThrows;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.*;
import org.bukkit.block.BlockFace;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

public interface IConfig {

    MiniMessage mm = MiniMessage.miniMessage();
    LegacyComponentSerializer lcs = LegacyComponentSerializer.legacyAmpersand();

    HashMap<String, SimpleYml> saved = new HashMap<>();

    String getPath();
    File getDataFolder();

    private String identifier() {
        return getDataFolder().getName().toLowerCase() + "-" + fileName();
    }

    default String fileName() {
        return getClass().getSimpleName().toLowerCase();
    }

    default File file() {
        return new File(getDataFolder(), fileName() + ".yml");
    }

    default void reloadFile() {
        if (resourceLocation() != null) {
            File file = new File(getDataFolder(), resourceLocation());
            SimpleYml simpleYml = new SimpleYml(getClass(), file, resourceLocation());

            Bukkit.getConsoleSender().sendMessage("§6USING CUSTOM RESOURCE FILE! §e" + resourceLocation());
            saved.put(identifier(), simpleYml);
        } else {
            saved.put(identifier(), new SimpleYml(getClass(), file()));
            Bukkit.getConsoleSender().sendMessage("§6USING DEFAULT RESOURCE FILE! §e" + file().getName());
        }
    }

    default String resourceLocation() { return null; }

    default SimpleYml yml() {
        if (!saved.containsKey(identifier())) {
            reloadFile();
        }
        return saved.get(identifier());
    }

    @SneakyThrows
    private <T> T getObj(String name, Class<T> clazz) {
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

    default float asFloat() {return (float)yml().getDouble(getPath());}
    default byte asByte() {return (byte)yml().getInt(getPath());}
    default short asShort() {return (short)yml().getInt(getPath());}

    default Material asMaterial() {
        return Utilities.findEnumValue(yml().getString(getPath()), Material.class);
    }

    default Sound asSound() {
        return Utilities.findEnumValue(yml().getString(getPath()), Sound.class);
    }

    default SoundCategory asSoundCategory() {
        return Utilities.findEnumValue(yml().getString(getPath()), SoundCategory.class);
    }

    default BlockFace asBlockFace() {
        return Utilities.findEnumValue(yml().getString(getPath()), BlockFace.class);
    }

    default Particle asParticle() {
        return Utilities.findEnumValue(yml().getString(getPath()), Particle.class);
    }

    default Rows asRows() {
        return Utilities.findEnumValue(yml().getString(getPath()), Rows.class);
    }

    default Component asComponent(){return asComponent(null);}
    default List<Component> asComponentList(){return asComponentList(null);}

    default String asLegacy(){return asLegacy(null);}
    default List<String> asLegacyList(){return asLegacyList(null);}

    default List<Component> asComponentList(TagResolver... tagResolvers) {
        List<String> values = yml().getStringList(getPath());

        return values.stream().map(entry-> Colors.translate(entry,tagResolvers)).toList();
    }

    default Component asSingleComponent(TagResolver... tagResolvers) {
        return Component.join(JoinConfiguration.newlines(),asComponentList(tagResolvers));
    }

    default String asJson() {
        return GsonComponentSerializer.gson().serialize(asComponent());
    }

    default Component asComponent(TagResolver... tagResolvers){
        String entry = yml().getString(getPath());
        if (entry != null) {
            return Colors.translate(entry, tagResolvers);
        }
        return Component.text(" [Invalid value in "+file().getName()+": " + getPath() + "] ");
    }

    default String asLegacy(TagResolver... tagResolvers){
        String entry = yml().getString(getPath());
        if (entry != null) {
            return Colors.legacy(Colors.translate(entry, tagResolvers));
        }
        return " [Invalid value in "+file().getName()+": " + getPath() + "] ";
    }

    default List<String> asLegacyList(TagResolver... tagResolvers){
        List<String> values = yml().getStringList(getPath());
        return values.stream().map(entry -> Colors.legacy(Colors.translate(entry, tagResolvers))).toList();
    }

}