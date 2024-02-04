package fi.natroutter.natlibs.utilities;

import fi.natroutter.natlibs.configuration.IConfig;
import fi.natroutter.natlibs.handlers.CustomResolver;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class Colors {

    private static MiniMessage mm = MiniMessage.builder()
            .build();
    private static LegacyComponentSerializer lcs = LegacyComponentSerializer
            .builder()
            .hexColors()
            .character('&')
            .build();

    /**
     * Translates component to minimessage string
     *
     * @param component component to translate
     * @return minimessag formating string
     */
    public static String serialize(Component component) {
        return mm.serialize(component);
    }

    /**
     * Translates component to legacy string without colors
     *
     * @param component component to translate
     * @return plain string
     */
    public static String plain(Component component) {
        return PlainTextComponentSerializer.plainText().serialize(component);
    }

    /**
     * Translates component to legacy string with colors
     *
     * @param component component to translate
     * @return legacy chat mesage
     */
    public static String legacy(Component component)  {
        return lcs.serialize(component);
    }

    /**
     * Translates string with color codes and minimessage to chat component
     *
     * @param config config entry to translate
     * @param placeholders list of placeholders to parse
     * @return component
     */
    public static Component translate(IConfig config, TagResolver... placeholders) {
        return translate(config.asString(), true, placeholders);
    }

    /**
     * Translates string with color codes and minimessage to chat component
     *
     * @param str string to translate
     * @param placeholders list of placeholders to parse
     * @return component
     */
    public static Component translate(String str, TagResolver... placeholders) {
        return translate(str, true, placeholders);
    }

    /**
     * Translates string with color codes and minimessage to chat component
     *
     * @param str string to translate
     * @param placeholders list of placeholders to parse
     * @return component
     */
    public static Component translate(String str, List<TagResolver> placeholders) {
        return translate(str, true, placeholders.toArray(new TagResolver[0]));
    }

    /**
     * Translates string with color codes and minimessage to chat component
     *
     * @param str string to translate
     * @param useCustom Do you want to use custom resolvers
     * @param placeholders list of placeholders to parse
     * @return component
     */
    public static Component translate(String str, boolean useCustom, @Nullable TagResolver... placeholders) {
        List<TagResolver> list = new ArrayList<>();
        if (placeholders != null) {
            list.addAll(List.of(placeholders));
            if (useCustom) {
                list.addAll(CustomResolver.resolvers());
            }
        }

        //TODO fix this its so fucking stupid :D
        Component italicFix = Component.text("").style(style -> style.decoration(TextDecoration.ITALIC, false));

        TextComponent deserialize = lcs.deserialize(str.replace("§", "&"));
        String serialize = mm.serialize(deserialize).replace("\\<", "<");
        return italicFix.append(mm.deserialize(serialize, list.toArray(new TagResolver[0])));
    }

}
