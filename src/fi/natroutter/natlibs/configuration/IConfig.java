package fi.natroutter.natlibs.configuration;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import fi.natroutter.natlibs.handlers.guibuilder.Rows;
import fi.natroutter.natlibs.utilities.Colors;
import fi.natroutter.natlibs.utilities.Utilities;
import lombok.SneakyThrows;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.bukkit.*;
import org.bukkit.block.BlockFace;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;

public interface IConfig {

    HashMap<String, YamlDocument> documents = new HashMap<>();

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

    // "langs/en_us.yml"
    default String resourceLocation() { return fileName() + ".yml"; }

    //Yaml handling

    @SneakyThrows
    default YamlDocument createFile() {
        YamlDocument doc = YamlDocument.create(file(), ConfigUtils.getResource(getClass(), "/"+resourceLocation()));
//        doc.setDumperSettings(dumperSettings());
//        doc.setGeneralSettings(generalSettings());
//        doc.setUpdaterSettings(updaterSettings());
//        doc.setLoaderSettings(loaderSettings());
        doc.setSettings(dumperSettings(),generalSettings(),updaterSettings(),loaderSettings());
        documents.put(identifier(), doc);
        return doc;
    }

    default LoaderSettings loaderSettings() { return LoaderSettings.DEFAULT; }
    default DumperSettings dumperSettings() {
        return DumperSettings.DEFAULT;
    }
    default GeneralSettings generalSettings() {
        return GeneralSettings.DEFAULT;
    }
    default UpdaterSettings updaterSettings() {
        return UpdaterSettings.DEFAULT;
    }

    default YamlDocument yml() {
        if (!documents.containsKey(identifier())) {
            return createFile();
        }
        return documents.get(identifier());
    }

    @SneakyThrows
    default void reload() {
        yml().reload();
    }

    //Methods

    @SneakyThrows
    default <T> List<T> asObjectList(Class<T> clazz) {
        return yml().getSection(getPath()).getKeys()
                .stream()
                .map(key -> yml().getAs(getPath() +"."+ key.toString(), clazz))
                .toList();
    }

    default String asString() {return yml().getString(getPath());}
    default int asInteger() {return yml().getInt(getPath());}
    default long asLong() {return yml().getLong(getPath());}
    default double asDouble() {return yml().getDouble(getPath());}
    default boolean asBoolean() {return yml().getBoolean(getPath());}
    default List<String> asStringList() {return yml().getStringList(getPath());}

    default float asFloat() {return yml().getFloat(getPath());}
    default byte asByte() {return yml().getByte(getPath());}
    default short asShort() {return yml().getShort(getPath());}

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