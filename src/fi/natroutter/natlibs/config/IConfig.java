package fi.natroutter.natlibs.config;

import fi.natroutter.natlibs.objects.Placeholder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public interface IConfig {
    MiniMessage mm = MiniMessage.miniMessage();

    String getPath();
    JavaPlugin getPlugin();

    default SimpleYml yml() {
        return new SimpleYml(getPlugin(), getClass().getSimpleName().toLowerCase() + ".yml");
    }

    default String asString() {return yml().getString(getPath());}
    default int asInteger() {return yml().getInt(getPath());}
    default long asLong() {return yml().getLong(getPath());}
    default double asDouble() {return yml().getDouble(getPath());}
    default boolean asBoolean() {return yml().getBoolean(getPath());}
    default List<String> asStringList() {return yml().getStringList(getPath());}

    default Component asComponent(){return asComponent(null);}
    default List<Component> asComponentList(){return asComponentList(null);}

    default List<Component> asComponentList(List<Placeholder> placeholders) {
        List<String> values = yml().getStringList(getPath());
        return values.stream().map(value -> {
            if (placeholders != null && placeholders.size() >0) {
                return mm.deserialize(value, placeholders.stream().map(Placeholder::getResolver).toArray(TagResolver[]::new));
            }
            return mm.deserialize(value);
        }).toList();
    }

    default Component asComponent(List<Placeholder> placeholders){
        String value = yml().getString(getPath());
        if (placeholders != null && placeholders.size() >0) {
            return mm.deserialize(value, placeholders.stream().map(Placeholder::getResolver).toArray(TagResolver[]::new));
        }
        return mm.deserialize(value);
    }

}