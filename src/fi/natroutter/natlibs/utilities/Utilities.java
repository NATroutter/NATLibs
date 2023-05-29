package fi.natroutter.natlibs.utilities;

import fi.natroutter.natlibs.objects.BaseItem;
import fi.natroutter.natlibs.objects.Complete;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.minimessage.tag.standard.StandardTags;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.StringUtil;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@SuppressWarnings({"unused"})
public class Utilities {

	private static MiniMessage mm = MiniMessage.builder().build();
	private static LegacyComponentSerializer lcs = LegacyComponentSerializer.legacyAmpersand();

	public static String CurrencyFormat(double balance) {
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.ENGLISH);
		DecimalFormatSymbols sym = formatter.getDecimalFormatSymbols();
		sym.setCurrencySymbol("");
		sym.setDecimalSeparator(',');
		sym.setGroupingSeparator('.');
		formatter.setDecimalFormatSymbols(sym);
		return formatter.format(balance);
	}

	public static List<String> completesWithPerms(CommandSender sender, String arg, List<Complete> list) {
		List<String> newList = list.stream()
				.filter(c -> sender.hasPermission(c.permission()))
				.map(Complete::arg)
				.collect(Collectors.toList());
		return completes(sender, arg, newList);
	}
	public static List<String> completes(CommandSender sender, String arg, List<String> list) {
		List<String> shorted = new ArrayList<>();
		StringUtil.copyPartialMatches(arg, list, shorted);
		Collections.sort(shorted);
		return shorted;
	}

	public static void glint(BaseItem item, boolean state) {
		item.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		if (state) {
			item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		} else {
			item.removeEnchantment(Enchantment.DURABILITY);
		}
	}

	public static String plain(Component comp) {
		return PlainTextComponentSerializer.plainText().serialize(comp);
	}

	public static String legacy(Component comp)  {
		return lcs.serialize(comp);
	}

	public static Component translate(String str) {
		return translate(str,null);
	}

	public static Component translate(String str, List<TagResolver> tagResolvers) {
		str = str.replace("§", "&");
		Component deserialize = lcs.deserialize(str);
		String format;
		if (tagResolvers != null && !tagResolvers.isEmpty()) {
			Component resolved = mm.deserialize(mm.serialize(deserialize), tagResolvers.toArray(TagResolver[]::new));
			format = mm.serialize(resolved);
		} else {
			format = mm.serialize(deserialize);
		}
		String replace = format.replace("\\<", "<");
		return mm.deserialize(replace);
	}

	public static float pitchToFloat(Player p) {
        return 2 - (p.getLocation().getPitch() + 90) / 90;
    }

	public static String locString(Location loc) {
		return loc.getWorld().getName() + ", " + loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ();
	}

	public static boolean isBetween(int Check, int start, int end) {
		return Check >= start && Check <= end;
	}
	
	public static Float parseSpeed(Integer val, boolean isFly) {return parseSpeed(val,isFly,false);}
	public static Float parseSpeed(Integer val, boolean isFly, boolean bypassLimits) {
		float speed = val.floatValue();
		float defaultSpeed = isFly ? 0.1F : 0.2F;
	    float maxSpeed = 1.0F;
	    if (bypassLimits) {
	        maxSpeed = (10);
	    }
	    if (speed < 1.0F) {
	    	return defaultSpeed * speed;
	    }
	    float ratio = (speed - 1.0F) / 9.0F * (maxSpeed - defaultSpeed);
	    return ratio + defaultSpeed;
	}

	public static Integer distanceToGround(Location start) {
		int amount = 0;
		while (!start.subtract(0, 1, 0).getBlock().getType().isSolid()) {
		    amount++;
		}
		return amount;
	}

	public static String serializeLocation(Location loc, Character separator) {
		String sep = separator.toString();
		return loc.getWorld().getName() + sep + loc.getX() + sep + loc.getY() + sep + loc.getZ()  + sep + loc.getYaw()  + sep + loc.getPitch();
	}

	public static Location deserializeLocation(String loc, Character separator) {
		String sep = separator.toString();
		String[] parts = loc.split(Pattern.quote(sep));
		if (parts.length == 6) {
			try {
				return new Location(
						Bukkit.getWorld(parts[0]),
						Double.parseDouble(parts[1]), Double.parseDouble(parts[2]), Double.parseDouble(parts[3]),
						Float.parseFloat(parts[4]), Float.parseFloat(parts[5]));
			} catch (Exception e) {
				Bukkit.getLogger().warning("[Serializer] invalid values entered to location deserializer");
				e.printStackTrace();
			}
		}
		return null;
	}

	public static ArrayList<Block> getBlocks(Location start, int radius){
		ArrayList<Block> blocks = new ArrayList<Block>();
		for(double x = start.getX() - radius; x <= start.getX() + radius; x++){
			for(double y = start.getY() - radius; y <= start.getY() + radius; y++){
				for(double z = start.getZ() - radius; z <= start.getZ() + radius; z++){
					Location loc = new Location(start.getWorld(), x, y, z);
					blocks.add(loc.getBlock());
				}
			}
		}
		return blocks;
	}

	public static boolean inRegion(Location loc, Location loc1, Location loc2) {
		double x1 = loc1.getX();
		double y1 = loc1.getY();
		double z1 = loc1.getZ();

		double x2 = loc2.getX();
		double y2 = loc2.getY();
		double z2 = loc2.getZ();

		return (loc.getX() > x1) && (loc.getY() > y1) && (loc.getZ() > z1) && (loc.getX() < x2) && (loc.getY() < y2) && (loc.getZ() < z2);
	}

} 
