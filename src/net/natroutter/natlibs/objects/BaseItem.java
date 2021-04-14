package net.natroutter.natlibs.objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import com.google.common.collect.Multimap;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.tags.CustomItemTagContainer;
import org.bukkit.material.MaterialData;
import org.bukkit.persistence.PersistentDataContainer;

@SuppressWarnings("deprecation")
public class BaseItem extends ItemStack implements Cloneable, ConfigurationSerializable {

	ItemStack ogStack;

	public BaseItem(Material type) {
		super(type);
	}

	public BaseItem(Material type, int amount, short damage) {
		super(type, amount, damage);
	}

	public BaseItem(Material type, int amount, short damage, Byte data) {
		super(type, amount, damage, data);
	}

	// will create OneItemStack and wont edit the orginalStack
	public BaseItem(ItemStack stack) throws IllegalArgumentException {
		this(stack, false);
	}
	
	//will create OneItemStack and also edit the orginalStack
	public static BaseItem from(ItemStack stack) {
		return new BaseItem(stack, true);
	}
	
	private BaseItem(ItemStack stack, boolean editOrginal) throws IllegalArgumentException {
		setValues(stack, editOrginal);
	}
	
	public void setValues(ItemStack stack, boolean editOrginal) {
		if (stack != null) {
			if (editOrginal)
				this.ogStack = stack;
			setType(stack.getType());
			setAmount(stack.getAmount());
			if (getType().isLegacy()) {
				setData(stack.getData());
			}
			
			//if (stack.hasItemMeta())
			setItemMeta0(stack.getItemMeta(), getType());
		}
	}

	private BaseItem setToMeta(Consumer<ItemMeta> consumer) {
		ItemMeta meta = getItemMeta();
		consumer.accept(meta);
		setItemMeta(meta);

		// Sets to orginal stack
		if (ogStack != null)
			ogStack.setItemMeta(getItemMeta());
		return this;
	}

	private BaseItem setToOrginal(Consumer<ItemStack> consumer) {
		if (ogStack != null)
			consumer.accept(ogStack);
		return this;
	}

	public boolean matches(BaseItem item2) {
		if (getItemMeta() != null && item2.getItemMeta() != null) {
			if (getItemMeta().getDisplayName().equals(item2.getDisplayName())) {
				if (getItemMeta().getLore().equals(item2.getLore())) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void Destroy() {
		setToOrginal(ogStack -> ogStack.setAmount(0));
	}
	
	public BaseItem setLocalizedName(String localizedName) {
		return setToMeta(meta -> meta.setLocalizedName(localizedName));
	}

	public BaseItem setUnbreakable(boolean bool) {
		return setToMeta(meta -> meta.setUnbreakable(bool));
	}

	public BaseItem setCustomModelData(Integer modelData) {
		return setToMeta(meta -> meta.setCustomModelData(modelData));
	}

	public BaseItem setAttributeModifiers(Multimap<Attribute, AttributeModifier> attriButeModifiers) {
		return setToMeta(meta -> meta.setAttributeModifiers(attriButeModifiers));
	}

	public BaseItem setDisplayName(String displayName) {
		return setToMeta(meta -> meta.setDisplayName(displayName));
	}

	public BaseItem addItemFlags(ItemFlag... flags) {
		return setToMeta(meta -> meta.addItemFlags(flags));
	}

	public BaseItem removeItemFlags() {
		return setToMeta(meta -> meta.removeItemFlags((ItemFlag[]) meta.getItemFlags().toArray()));
	}

	public BaseItem removeItemFlag(ItemFlag flag) {
		return setToMeta(meta -> meta.removeItemFlags(flag));
	}

	public BaseItem removeEnchants() {
		return setToMeta(meta -> meta.getEnchants().entrySet().forEach(entry -> meta.removeEnchant(entry.getKey())));
	}

	// Adds lores to the lore list keeping the old ones.
	public BaseItem addLore(String... lores) {
		return setToMeta(meta -> {
			List<String> itemLore = meta.getLore() == null ? new ArrayList<>() : meta.getLore();
			itemLore.addAll(Arrays.asList(lores));
			meta.setLore(itemLore);
		});
	}

	public BaseItem addLore(List<String> lores) {
		return addLore(lores.toArray(new String[0]));
	}

	public BaseItem setLore(String... lores) {
		return setToMeta(meta -> meta.setLore(Arrays.asList(lores)));
	}

	public BaseItem setLore(List<String> lores) {
		return setLore(lores.toArray(new String[0]));
	}

	public BaseItem setLore(int row, String lore) {
		return setToMeta(meta -> {
			int countRow = row;
			List<String> itemLores = meta.getLore() == null ? new ArrayList<>() : meta.getLore();

			if (meta.getLore().size() > countRow) {
				itemLores = meta.getLore();
				itemLores.set(countRow, lore);
			} else {
				while (countRow != 0) {
					itemLores.add("");
					countRow--;
				}
				itemLores.add(lore);
			}
			meta.setLore(itemLores);
		});
	}



	// for cloning
	private boolean setItemMeta0(ItemMeta itemMeta, Material material) {
		if (itemMeta == null) {
			setItemMeta(null);
			return true;
		}
		if (!Bukkit.getItemFactory().isApplicable(itemMeta, material)) {
			return false;
		}
		setItemMeta(Bukkit.getItemFactory().asMetaFor(itemMeta, material));
		Material newType = Bukkit.getItemFactory().updateMaterial(getItemMeta(), material);
		if (getType() != newType) {
			setType(newType);
		}

		if (getItemMeta() == itemMeta) {
			//OG
			//setItemMeta(itemMeta.clone());
			setItemMeta(itemMeta);
		}

		return true;
	}
	
	// Getters
	public List<String> getLore() {
		return getItemMeta().getLore();
	}

	public PersistentDataContainer getPersistentDataContainer() {
		return getItemMeta().getPersistentDataContainer();
	}

	public String getLocalizedName() {
		return getItemMeta().getLocalizedName();
	}

	public Set<ItemFlag> getItemFlags() {
		return getItemMeta().getItemFlags();
	}

	public Map<Enchantment, Integer> getEnchants() {
		return getItemMeta().getEnchants();
	}

	public int getEnchantLevel(Enchantment ench) {
		return getItemMeta().getEnchantLevel(ench);
	}

	public String getDisplayName() {
		return getItemMeta().getDisplayName();
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

	public CustomItemTagContainer getCustomTagContainer() {
		
		return getItemMeta().getCustomTagContainer();
	}
	
	@Override
	public BaseItem clone() {
		BaseItem itemStack = (BaseItem) super.clone();

		if (getItemMeta() != null) {
			itemStack.setItemMeta(getItemMeta().clone());
		}

		if (getData() != null) {
			itemStack.setData(getData().clone());
		}
		return itemStack;
	}

	// Handling orginalStack edits
	public BaseItem addEnchantmentt(Enchantment ench, int level) {
		setToOrginal(ogStack -> ogStack.addEnchantment(ench, level));
		super.addEnchantment(ench, level);
		return this;
	}

	public BaseItem addEnchantmentss(Map<Enchantment, Integer> enchantments) {
		setToOrginal(ogStack -> ogStack.addEnchantments(enchantments));
		super.addEnchantments(enchantments);
		return this;
	}

	public BaseItem addUnsafeEnchantmentt(Enchantment ench, int level) {
		setToOrginal(ogStack -> ogStack.addUnsafeEnchantment(ench, level));
		super.addUnsafeEnchantment(ench, level);
		return this;
	}

	public BaseItem addUnsafeEnchantmentss(Map<Enchantment, Integer> enchantments) {
		setToOrginal(ogStack -> ogStack.addUnsafeEnchantments(enchantments));
		super.addUnsafeEnchantments(enchantments);
		return this;
	}

	public BaseItem removeEnchantmentt(Enchantment ench) {
		setToOrginal(ogStack -> ogStack.removeEnchantment(ench));
		super.removeEnchantment(ench);
		return this;
	}

	public BaseItem setAmountt(int amount) {
		setToOrginal(ogStack -> ogStack.setAmount(amount));
		super.setAmount(amount);
		return this;
	}

	public BaseItem setDataa(MaterialData data) {
		setToOrginal(ogStack -> ogStack.setData(data));
		super.setData(data);
		return this;
	}

	public BaseItem setDurabilityy(short durability) {
		setToOrginal(ogStack -> ogStack.setDurability(durability));
		super.setDurability(durability);
		return this;
	}

	public boolean setItemMetaa(ItemMeta itemMeta) {
		setToOrginal(ogStack -> ogStack.setItemMeta(itemMeta));
		return super.setItemMeta(itemMeta);
	}

	public BaseItem setTypee(Material type) {
		setToOrginal(ogStack -> ogStack.setType(type));
		setType(type);
		return this;
	}
	
	//orginal getters, sets to orginal item aswell
	@Override
	public void addEnchantment(Enchantment ench, int level) {
		setToOrginal(ogStack -> ogStack.addEnchantment(ench, level));
		super.addEnchantment(ench, level);
	}

	@Override
	public void addEnchantments(Map<Enchantment, Integer> enchantments) {
		setToOrginal(ogStack -> ogStack.addEnchantments(enchantments));
		super.addEnchantments(enchantments);
	}

	@Override
	public void addUnsafeEnchantment(Enchantment ench, int level) {
		setToOrginal(ogStack -> ogStack.addUnsafeEnchantment(ench, level));
		super.addUnsafeEnchantment(ench, level);
	}

	@Override
	public void addUnsafeEnchantments(Map<Enchantment, Integer> enchantments) {
		setToOrginal(ogStack -> ogStack.addUnsafeEnchantments(enchantments));
		super.addUnsafeEnchantments(enchantments);
	}

	@Override
	public void setAmount(int amount) {
		setToOrginal(ogStack -> ogStack.setAmount(amount));
		super.setAmount(amount);
	}

	@Override
	public void setData(MaterialData data) {
		setToOrginal(ogStack -> ogStack.setData(data));
		super.setData(data);
	}

	@Override
	public void setDurability(short durability) {
		setToOrginal(ogStack -> ogStack.setDurability(durability));
		super.setDurability(durability);
	}

	@Override
	public boolean setItemMeta(ItemMeta itemMeta) {
		setToOrginal(ogStack -> ogStack.setItemMeta(itemMeta));
		return super.setItemMeta(itemMeta);
	}

	@Override
	public void setType(Material type) {
		setToOrginal(ogStack -> ogStack.setType(type));
		super.setType(type);
	}
}
