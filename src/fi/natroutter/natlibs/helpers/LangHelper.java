package fi.natroutter.natlibs.helpers;

import fi.natroutter.natlibs.config.IConfig;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class LangHelper {

    private MiniMessage mm = MiniMessage.builder().build();
    private LegacyComponentSerializer lcs = LegacyComponentSerializer.legacyAmpersand();

    private final IConfig prefix;
    private final boolean supportLegacy;

    public LangHelper(IConfig prefix) {
        this.prefix = prefix;
        this.supportLegacy = true;
    }

    public LangHelper(IConfig prefix, boolean supportLegacy) {
        this.prefix = prefix;
        this.supportLegacy = supportLegacy;
    }


    public void prefix(CommandSender sender, IConfig... langs){prefix(sender,null,langs);}
    public void prefix(CommandSender sender, List<TagResolver> placeholders, IConfig... langs){
        langs = addItemToFrontOfArray(langs, prefix);
        send(sender, placeholders, langs);
    }

    public void sendList(CommandSender sender, IConfig... items){sendList(sender, null, items);}
    public void sendList(CommandSender sender, List<TagResolver> tagResolvers, IConfig... langs){
        Component comp = Arrays.stream(langs)
                .map(IConfig::asStringList)
                .flatMap(Collection::stream)
                .map(i-> tagResolvers != null ? mm.deserialize(i, tagResolvers.toArray(TagResolver[]::new)) : mm.deserialize(i))
                .reduce(Component.empty(), (c1, c2) -> c1.append(c2).append(Component.newline()));

        if (supportLegacy) {
            sender.sendMessage(lcs.deserialize(lcs.serialize(comp)));
            return;
        }
        sender.sendMessage(comp);
    }

    public void send(CommandSender sender, IConfig... items){send(sender, null, items);}
    private void send(CommandSender sender, List<TagResolver> tagResolvers, IConfig... langs) {
        Component comp = Arrays.stream(langs)
                .map(IConfig::asString)
                .map(i-> tagResolvers != null ? mm.deserialize(i, tagResolvers.toArray(TagResolver[]::new)) : mm.deserialize(i))
                .reduce(Component.empty(), Component::append);

        if (supportLegacy) {
            sender.sendMessage(lcs.deserialize(lcs.serialize(comp)));
            return;
        }
        sender.sendMessage(comp);
    }

    public void sendStringPrefix(CommandSender sender, String value){sendStringPrefix(sender, null, value);}
    private void sendStringPrefix(CommandSender sender, List<TagResolver> tagResolvers, String value) {
        Component comp = tagResolvers != null ? mm.deserialize(value, tagResolvers.toArray(TagResolver[]::new)) : mm.deserialize(value);
        if (supportLegacy) {
            sender.sendMessage(lcs.deserialize(lcs.serialize(prefix.asComponent().append(comp))));
            return;
        }
        sender.sendMessage(prefix.asComponent().append(comp));
    }

    public void sendString(CommandSender sender, String value){sendString(sender, null, value);}
    private void sendString(CommandSender sender, List<TagResolver> tagResolvers, String value) {
        Component comp = tagResolvers != null ? mm.deserialize(value, tagResolvers.toArray(TagResolver[]::new)) : mm.deserialize(value);
        if (supportLegacy) {
            sender.sendMessage(lcs.deserialize(lcs.serialize(comp)));
            return;
        }
        sender.sendMessage(comp);
    }

    private <T> T[] addItemToFrontOfArray(T[] originalArray, T itemToAdd) {
        T[] newArray = Arrays.copyOf(originalArray, originalArray.length + 1);
        System.arraycopy(originalArray, 0, newArray, 1, originalArray.length);
        newArray[0] = itemToAdd;
        return newArray;
    }


}