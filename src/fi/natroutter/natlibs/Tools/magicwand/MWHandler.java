package fi.natroutter.natlibs.Tools.magicwand;

import fi.natroutter.natlibs.NATLibs;
import fi.natroutter.natlibs.objects.BaseItem;
import fi.natroutter.natlibs.utilities.Utilities;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class MWHandler {

    protected static NamespacedKey frameKey = new NamespacedKey(NATLibs.getInstance(), "FrameKey");
    protected static NamespacedKey standKey = new NamespacedKey(NATLibs.getInstance(), "StandKey");
    protected static NamespacedKey wandKey = new NamespacedKey(NATLibs.getInstance(), "WandKey");
    protected static NamespacedKey wandMode = new NamespacedKey(NATLibs.getInstance(), "WandMode");

    protected static boolean isWand(ItemStack item) {
        if (item == null || item.getItemMeta() == null) return false;
        return (item.getItemMeta().getPersistentDataContainer().has(wandKey));
    }
    protected static BaseItem wand(MagicMode mode) {
        BaseItem item = new BaseItem(Material.BLAZE_ROD);
        item.name(Utilities.translateColors("<gradient:#3a34eb:#eb34e8><bold>MagicWand</bold></gradient><color:#f174ef> ("+mode.getFriendlyName()+")</color>"));
        item.lore(List.of(
                Utilities.translateColors("<color:#b0b0b0>Magical Wand that can rewrite past and future!"),
                Component.text(" ")
        ));

        item.editData(data -> {
            data.set(wandKey, PersistentDataType.BOOLEAN, true);
            data.set(wandMode, PersistentDataType.STRING, mode.name());
        });

        item.setGlow(true);
        item.addItemFlags(ItemFlag.values());
        return item;
    }

    protected static void updateName(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();
        MagicMode mode = MagicMode.valueOf(data.get(wandMode, PersistentDataType.STRING));
        meta.displayName(Utilities.translateColors("<gradient:#3a34eb:#eb34e8><bold>MagicWand</bold></gradient><color:#f174ef:> ("+mode.getFriendlyName()+")</color>"));
        item.setItemMeta(meta);
    }

    protected static PersistentDataContainer getDataContainer(ItemStack item) {
        return item.getItemMeta().getPersistentDataContainer();
    }

    protected static MagicMode getMagicMode(ItemStack item) {
        return MagicMode.valueOf(getDataContainer(item).get(wandMode, PersistentDataType.STRING));
    }

    protected static void setWandMode(ItemStack item, MagicMode mode) {
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();
        data.set(wandMode, PersistentDataType.STRING, mode.name());
        item.setItemMeta(meta);
    }


}
