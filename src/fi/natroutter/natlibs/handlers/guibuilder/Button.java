package fi.natroutter.natlibs.handlers.guibuilder;

import fi.natroutter.natlibs.objects.BaseItem;
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
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Button extends BaseItem {

    protected BiConsumer<ClickAction, GUI> clickEvent;

    public Button(Material material, BiConsumer<ClickAction, GUI> clickEvent) {
        super(material);
        this.clickEvent = clickEvent;
    }

    public Button(ItemStack item, BiConsumer<ClickAction, GUI> clickEvent) {
        super(item);
        this.clickEvent = clickEvent;
    }

    public Button(BaseItem display, BiConsumer<ClickAction, GUI> clickEvent) {
        super(display);
        this.clickEvent = clickEvent;
    }

    public Button setGlow(boolean value) {
        if (value) {
            this.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            this.addUnsafeEnchantment(Enchantment.DURABILITY, 3);
        } else {
            this.removeEnchantment(Enchantment.DURABILITY);
        }
        return this;
    }
}
