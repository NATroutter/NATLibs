package fi.natroutter.natlibs.utilities;

import fi.natroutter.natlibs.objects.Enchant;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Map;

public class ItemBuilder {

    private ItemStack item;

    public ItemBuilder(Material material) {
        this(material, 1);
    }
    public ItemBuilder(Material material, int amount) {
        item = new ItemStack(material, amount);
    }

    public ItemBuilder setType(@NotNull Material material) {
        item.setType(material);
        return this;
    }

    public ItemBuilder setAmount(@NotNull int amount) {
        item.setAmount(amount);
        return this;
    }

    public ItemBuilder addEnchantments(Enchant... enchantments) {
        item.editMeta(meta -> {
            for (Enchant enchant : enchantments) {
                meta.addEnchant(enchant.getEnchant(), enchant.getLevel(), false);
            }
        });
        return this;
    }

    public ItemBuilder addUnsafeEnchantments(Enchant... enchantments) {
        item.editMeta(meta -> {
            for (Enchant enchant : enchantments) {
                meta.addEnchant(enchant.getEnchant(), enchant.getLevel(), true);
            }
        });
        return this;
    }

    public ItemBuilder setItemMeta(@Nullable ItemMeta itemMeta) {
        item.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder addItemFlags(@NotNull ItemFlag... itemFlags) {
        item.addItemFlags(itemFlags);
        return this;
    }

    public ItemBuilder setDisplayName(@Nullable Component displayName) {
        item.editMeta(meta -> meta.displayName(displayName));
        return this;
    }

    public ItemBuilder setDisplayName(@Nullable Component... lore) {
        item.editMeta(meta -> meta.lore(Arrays.asList(lore)));
        return this;
    }

    public ItemBuilder setDisplayName(@Nullable int customModelData) {
        item.editMeta(meta -> meta.setCustomModelData(customModelData));
        return this;
    }

    public ItemBuilder setUnbreakable(@Nullable boolean value) {
        item.editMeta(meta -> meta.setUnbreakable(value));
        return this;
    }

    public ItemStack build() {
        return item;
    }

}
