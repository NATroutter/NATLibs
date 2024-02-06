package fi.natroutter.natlibs.objects.typeadapters;

import dev.dejvokep.boostedyaml.serialization.standard.TypeAdapter;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.apache.commons.lang3.math.NumberUtils;
import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class ItemStackAdapter implements TypeAdapter<ItemStack> {

    private MiniMessage mm = MiniMessage.miniMessage();

    @NotNull
    @Override
    public Map<Object, Object> serialize(@NotNull ItemStack item) {
        Map<Object, Object> map = new HashMap<>();

        map.put("material", item.getType());
        map.put("amount", item.getAmount());
        map.put("itemFlags", item.getItemFlags());
        map.put("enchantments", item.getEnchantments());

        ItemMeta meta = item.getItemMeta();
        if (item.hasItemMeta() && meta != null) {
            Map<Object, Object> metaMap = new HashMap<>();

            if (meta.hasDisplayName()) {
                metaMap.put("displayName", mm.serialize(meta.displayName()));
            }
            if (meta.hasLore()) {
                metaMap.put("lore", meta.lore().stream().map(v->mm.serialize(v)).toList());
            }
            if (meta.hasCustomModelData()) {
                metaMap.put("modelData", meta.getCustomModelData());
            }

            map.put("itemMeta", metaMap);
        }

        return map;
    }

    @NotNull
    @Override
    public ItemStack deserialize(@NotNull Map<Object, Object> map) {
        return null;
    }
}
