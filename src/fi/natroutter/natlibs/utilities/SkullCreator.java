package fi.natroutter.natlibs.utilities;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import fi.natroutter.natlibs.configuration.IConfig;
import fi.natroutter.natlibs.objects.BaseItem;
import net.kyori.adventure.text.Component;
import org.apache.maven.model.Profile;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

@SuppressWarnings("deprecation")
public class SkullCreator {

	public static BaseItem create(String base64) {
		return itemWithBase64(new BaseItem(Material.PLAYER_HEAD), base64);
	}
	
	public static BaseItem create(String name, String base64) {
		BaseItem item = itemWithBase64(new BaseItem(Material.PLAYER_HEAD), base64);
		item.name(name);
		return item;
	}

	public static BaseItem create(Component name, String base64) {
		BaseItem item = itemWithBase64(new BaseItem(Material.PLAYER_HEAD), base64);
		item.name(name);
		return item;
	}

	public static BaseItem create(IConfig name, String base64) {
		BaseItem item = itemWithBase64(new BaseItem(Material.PLAYER_HEAD), base64);
		item.name(name);
		return item;
	}
	
	public static BaseItem create(Player p) {
		BaseItem item = new BaseItem(Material.PLAYER_HEAD);
		SkullMeta meta = (SkullMeta) item.getItemMeta();
		assert meta != null;
		meta.setOwningPlayer(p);
		item.setItemMeta(meta);
		return item;
	}

	public static String getTexture(ItemStack item) {
		if (!item.hasItemMeta() || item.getItemMeta() == null) return null;

		SkullMeta meta = (SkullMeta)item.getItemMeta();
		PlayerProfile profile = meta.getPlayerProfile();

		if (profile == null) return null;
		Set<ProfileProperty> properties = profile.getProperties();
		Optional<ProfileProperty> property = properties.stream().filter(prop -> prop.getName().equals("textures")).findFirst();
		return property.map(ProfileProperty::getValue).orElse(null);
	}

	private static BaseItem itemWithBase64(ItemStack item, String skullCode) {
		notNull(item, "item");
		notNull(skullCode, "base64");

		UUID hashAsId = new UUID(skullCode.hashCode(), skullCode.hashCode());
		return BaseItem.from(Bukkit.getUnsafe().modifyItemStack(item,"{SkullOwner:{Id:\"" + hashAsId + "\",Properties:{textures:[{Value:\"" + skullCode + "\"}]}}}"));
	}
	
	private static void notNull(Object o, String name) {
		if (o == null) {
			throw new NullPointerException(name + " should not be null!");
		}
	}
	
}
