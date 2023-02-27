package fi.natroutter.natlibs.handlers.guibuilder;

import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Button {

    private LegacyComponentSerializer legacySerializer = LegacyComponentSerializer.legacySection();

    @Getter @Setter
    private ItemStack item;

    protected BiConsumer<ClickAction, GUI> clickEvent;

    public Button(Material material, BiConsumer<ClickAction, GUI> clickEvent) {
        this.item = new ItemStack(material);
        this.clickEvent = clickEvent;
    }

    public Button setGlow(boolean value) {
        if (value) {
            setMeta(meta-> meta.addEnchant(Enchantment.DURABILITY, 1, true));
        } else {
            item.removeEnchantment(Enchantment.DURABILITY);
        }
        return this;
    }

    public Button setMaterial(Material material) {
        item.setType(material);
        return this;
    }

    public Button setLore(List<Component> line) {
        setMeta(meta->{
            meta.lore(line);
        });
        return this;
    }

    public Button setLore(String... line) {
        setMeta(meta->{
            meta.lore(Arrays.stream(line).map(legacySerializer::deserialize).map(TextComponent::compact).toList());
        });
        return this;
    }
    public Button setLore(Component... line) {
        setMeta(meta-> meta.lore(List.of(line)));
        return this;
    }

    public Button setName(String name) {
        setName(legacySerializer.deserialize(name));
        return this;
    }
    public Button setName(Component component) {
        setMeta(meta-> meta.displayName(component));
        return this;
    }

    public void setMeta(Consumer<ItemMeta> meta) {
        ItemMeta iMeta = item.getItemMeta();
        meta.accept(iMeta);
        item.setItemMeta(iMeta);
    }
}
