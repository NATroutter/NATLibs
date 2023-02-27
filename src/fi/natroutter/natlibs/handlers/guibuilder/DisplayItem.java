package fi.natroutter.natlibs.handlers.guibuilder;

import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class DisplayItem {

    private LegacyComponentSerializer legacySerializer = LegacyComponentSerializer.legacySection();

    @Getter @Setter
    private ItemStack item;

    public DisplayItem(Material material) {
        this.item = new ItemStack(material);
    }

    public DisplayItem setGlow(boolean value) {
        if (value) {
            setMeta(meta-> {
                meta.addEnchant(Enchantment.DURABILITY, 1, true);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            });
        } else {
            item.removeEnchantment(Enchantment.DURABILITY);
        }
        return this;
    }

    public DisplayItem setMaterial(Material material) {
        item.setType(material);
        return this;
    }

    public DisplayItem setLore(List<Component> line) {
        setMeta(meta->{
            meta.lore(line);
        });
        return this;
    }

    public DisplayItem setLore(String... line) {
        setMeta(meta->{
            meta.lore(Arrays.stream(line).map(legacySerializer::deserialize).map(TextComponent::compact).toList());
        });
        return this;
    }
    public DisplayItem setLore(Component... line) {
        setMeta(meta-> meta.lore(List.of(line)));
        return this;
    }

    public DisplayItem setName(String name) {
        setName(legacySerializer.deserialize(name));
        return this;
    }
    public DisplayItem setName(Component component) {
        setMeta(meta-> meta.displayName(component));
        return this;
    }

    public void setMeta(Consumer<ItemMeta> meta) {
        ItemMeta iMeta = item.getItemMeta();
        meta.accept(iMeta);
        item.setItemMeta(iMeta);
    }
}
