package fi.natroutter.natlibs.utilities;

import java.util.UUID;

import fi.natroutter.natlibs.config.IConfig;
import fi.natroutter.natlibs.objects.BaseItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.SkullMeta;

@SuppressWarnings("deprecation")
public class SkullCreator {
	
	
	public BaseItem Create(String name, String base64) {
		BaseItem item = itemWithBase64(new BaseItem(Material.PLAYER_HEAD), base64);
		item.name(name);
		return item;
	}

	public BaseItem Create(Component name, String base64) {
		BaseItem item = itemWithBase64(new BaseItem(Material.PLAYER_HEAD), base64);
		item.name(name);
		return item;
	}

	public BaseItem Create(IConfig name, String base64) {
		BaseItem item = itemWithBase64(new BaseItem(Material.PLAYER_HEAD), base64);
		item.name(name);
		return item;
	}
	
	public BaseItem Create(Player p) {
		BaseItem item = new BaseItem(Material.PLAYER_HEAD);
		SkullMeta meta = (SkullMeta) item.getItemMeta();
		assert meta != null;
		meta.setOwningPlayer(p);
		item.setItemMeta(meta);
		return item;
	}
	
	

	private BaseItem itemWithBase64(BaseItem item, String skullCode) {
		notNull(item, "item");
		notNull(skullCode, "base64");

		UUID hashAsId = new UUID(skullCode.hashCode(), skullCode.hashCode());
		return BaseItem.from(Bukkit.getUnsafe().modifyItemStack(item,"{SkullOwner:{Id:\"" + hashAsId + "\",Properties:{textures:[{Value:\"" + skullCode + "\"}]}}}"));
	}
	
	private void notNull(Object o, String name) {
		if (o == null) {
			throw new NullPointerException(name + " should not be null!");
		}
	}
	
}
