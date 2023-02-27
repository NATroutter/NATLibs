package fi.natroutter.natlibs.helpers;

import fi.natroutter.natlibs.config.IConfig;
import fi.natroutter.natlibs.objects.Placeholder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LangHelper {

    private MiniMessage mm = MiniMessage.miniMessage();
    private LegacyComponentSerializer lcs = LegacyComponentSerializer.legacyAmpersand();

    private final IConfig prefix;
    private final boolean supportLegacy;

    public LangHelper(IConfig prefix) {
        this.prefix = prefix;
        this.supportLegacy = false;
    }

    public LangHelper(IConfig prefix, boolean supportLegacy) {
        this.prefix = prefix;
        this.supportLegacy = supportLegacy;
    }


    public void prefix(CommandSender sender, IConfig... langs){prefix(sender,null,langs);}
    public void prefix(CommandSender sender, List<Placeholder> placeholders, IConfig... langs){
        langs = addItemToFrontOfArray(langs, prefix);
        send(sender, placeholders, langs);
    }

    public void send(CommandSender sender, IConfig... items){send(sender, null, items);}
    public void send(CommandSender sender, List<Placeholder> placeholders, IConfig... langs){
        String msg = Arrays.stream(langs).map(IConfig::asString).collect(Collectors.joining());
        sendMsg(sender, placeholders, msg);
    }

    public void sendList(CommandSender sender, IConfig... items){sendList(sender, null, items);}
    public void sendList(CommandSender sender, List<Placeholder> placeholders, IConfig... langs){
        String msg = Arrays.stream(langs).map(IConfig::asStringList).map(l->String.join("\n",l)).collect(Collectors.joining());
        sendMsg(sender, placeholders, msg);
    }

    private void sendMsg(CommandSender sender, List<Placeholder> placeholders, String msg) {
        if (supportLegacy) {
            if (placeholders != null && placeholders.size() >0) {
                Component comp = mm.deserialize(msg, placeholders.stream().map(Placeholder::getResolver).toArray(TagResolver[]::new));
                sender.sendMessage(lcs.deserialize(lcs.serialize(comp)));
                return;
            }
            sender.sendMessage(lcs.deserialize(lcs.serialize(mm.deserialize(msg))));
        } else {
            if (placeholders != null && placeholders.size() >0) {
                Component comp = mm.deserialize(msg, placeholders.stream().map(Placeholder::getResolver).toArray(TagResolver[]::new));
                sender.sendMessage(comp);
                return;
            }
            sender.sendMessage(mm.deserialize(msg));
        }
    }

    private <T> T[] addItemToFrontOfArray(T[] originalArray, T itemToAdd) {
        T[] newArray = Arrays.copyOf(originalArray, originalArray.length + 1);
        System.arraycopy(originalArray, 0, newArray, 1, originalArray.length);
        newArray[0] = itemToAdd;
        return newArray;
    }


}
