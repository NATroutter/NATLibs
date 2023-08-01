package fi.natroutter.natlibs.objects;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.google.common.collect.Multimap;
import fi.natroutter.natlibs.config.IConfig;
import fi.natroutter.natlibs.utilities.Utilities;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BaseItem extends ItemStack {

	private LegacyComponentSerializer lcs = LegacyComponentSerializer.legacyAmpersand();

	public BaseItem(Material material) {
		super(material);
	}

	public BaseItem(ItemStack item) {
		super(item.getType(), item.getAmount());
		this.setItemMeta(item.getItemMeta());
	}

	public static BaseItem from(ItemStack stack) {
		return new BaseItem(stack);
	}

	public BaseItem setGlow(boolean value) {
		if (value) {
			this.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			this.addUnsafeEnchantment(Enchantment.DURABILITY, 3);
		} else {
			this.removeEnchantment(Enchantment.DURABILITY);
		}
		return this;
	}

	@Override
	public boolean isSimilar(@NotNull ItemStack item) {

		if (item.getAmount() != getAmount()) return false;
		if (item.getType() != getType()) return false;

		if (item.hasItemMeta()) {
			if (hasItemMeta()) {
				ItemMeta meta = item.getItemMeta();
				ItemMeta meta2 = getItemMeta();

				if (!Objects.equals(meta.displayName(), meta2.displayName())) return false;
				if (!Objects.equals(meta.lore(), meta2.lore())) return false;
				if (!Objects.equals(meta.getPersistentDataContainer(), meta2.getPersistentDataContainer())) return false;
				if (!Objects.equals(meta.getEnchants(), meta2.getEnchants())) return false;
				if (!Objects.equals(meta.getAttributeModifiers(), meta2.getAttributeModifiers())) return false;
				if (!Objects.equals(meta.getCustomModelData(), meta2.getCustomModelData())) return false;
				if (!Objects.equals(meta.getItemFlags(), meta2.getItemFlags())) return false;
				return true;
			}
			return false;
		}
		return true;
	}

	public BaseItem setMaterial(Material material) {
		this.setType(material);
		return this;
	}


	public BaseItem lore(String... line) {
		lore(Arrays.stream(line).map(Utilities::translateColors).toList());
		return this;
	}
	public BaseItem lore(List<String> lines, TagResolver... placeholders) {
		lore(lines.stream().map(entry->Utilities.translateColors(entry,placeholders)).toList());
		return this;
	}
	public BaseItem lore(IConfig lore) {
		lore(lore.asComponentList());
		return this;
	}
	public BaseItem lore(IConfig lore, TagResolver... placeholders) {
		lore(lore.asComponentList(placeholders));
		return this;
	}

	public BaseItem name(String name) {
		setMeta(meta-> meta.displayName(Utilities.translateColors(name)));
		return this;
	}
	public BaseItem name(Component component) {
		setMeta(meta-> meta.displayName(component));
		return this;
	}
	public BaseItem name(IConfig name) {
		setMeta(meta-> meta.displayName(name.asComponent()));
		return this;
	}
	public BaseItem name(IConfig name, TagResolver... placeholders) {
		setMeta(meta-> meta.displayName(name.asComponent(placeholders)));
		return this;
	}

	public void destroy() {
		this.setAmount(0);
	}

	public BaseItem setUnbreakable(boolean bool) {
		return setMeta(meta -> meta.setUnbreakable(bool));
	}

	public BaseItem setCustomModelData(Integer modelData) {
		return setMeta(meta -> meta.setCustomModelData(modelData));
	}

	public BaseItem setAttributeModifiers(Multimap<Attribute, AttributeModifier> attriButeModifiers) {
		return setMeta(meta -> meta.setAttributeModifiers(attriButeModifiers));
	}

	public void addItemFlags(ItemFlag... flags) {
		setMeta(meta -> meta.addItemFlags(flags));
	}

	public BaseItem removeItemFlags() {
		return setMeta(meta -> meta.removeItemFlags((ItemFlag[]) meta.getItemFlags().toArray()));
	}

	public BaseItem removeItemFlag(ItemFlag flag) {
		return setMeta(meta -> meta.removeItemFlags(flag));
	}

	public BaseItem removeEnchants() {
		return setMeta(meta -> meta.getEnchants().entrySet().forEach(entry -> meta.removeEnchant(entry.getKey())));
	}

	//Getters

	public String name() {
		return Utilities.legacy(displayName());
	}
	public List<String> loreLegacy() {
		return lore().stream().map(Utilities::legacy).collect(Collectors.toList());
	}

	public PersistentDataContainer getPersistentDataContainer() {
		return getItemMeta().getPersistentDataContainer();
	}

	public Map<Enchantment, Integer> getEnchants() {
		return getItemMeta().getEnchants();
	}

	public int getEnchantLevel(Enchantment ench) {
		return getItemMeta().getEnchantLevel(ench);
	}

	public Multimap<Attribute, AttributeModifier> getAttributeModifiers() {
		return getItemMeta().getAttributeModifiers();
	}

	public Collection<AttributeModifier> getAttributeModifiers(Attribute atribute) {
		return getItemMeta().getAttributeModifiers(atribute);
	}

	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot equipmentSlot) {
		return getItemMeta().getAttributeModifiers(equipmentSlot);
	}

	public int getCustomModelData() {
		return getItemMeta().getCustomModelData();
	}


	public BaseItem setMeta(Consumer<ItemMeta> meta) {
		ItemMeta iMeta = this.getItemMeta();
		meta.accept(iMeta);
		this.setItemMeta(iMeta);
		return this;
	}
}
