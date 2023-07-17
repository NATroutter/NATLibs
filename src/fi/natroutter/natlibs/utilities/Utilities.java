package fi.natroutter.natlibs.utilities;

import fi.natroutter.natlibs.objects.BaseItem;
import fi.natroutter.natlibs.objects.Complete;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.util.StringUtil;

import javax.annotation.Nullable;
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

	public static String currencyFormat(double balance) {
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.ENGLISH);
		DecimalFormatSymbols sym = formatter.getDecimalFormatSymbols();
		sym.setCurrencySymbol("");
		sym.setDecimalSeparator(',');
		sym.setGroupingSeparator('.');
		formatter.setDecimalFormatSymbols(sym);
		return formatter.format(balance);
	}

	public static List<String> getCompletesWithPerms(CommandSender sender, String arg, List<Complete> list) {
		List<String> newList = list.stream()
				.filter(c -> sender.hasPermission(c.permission()))
				.map(Complete::arg)
				.collect(Collectors.toList());
		return getCompletes(sender, arg, newList);
	}
	public static List<String> getCompletes(CommandSender sender, String arg, List<String> list) {
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

	public static Component translateColors(String str, List<TagResolver> placeholders) {
		return translateColors(str, placeholders.toArray(new TagResolver[0]));
	}
	public static Component translateColors(String str, @Nullable TagResolver... placeholders) {
		TextComponent deserialize = lcs.deserialize(str.replace("§", "&"));
		String serialize = mm.serialize(deserialize).replace("\\<", "<");
		return placeholders == null ? mm.deserialize(serialize) : mm.deserialize(serialize, placeholders);
	}


	public static float pitchToFloat(Player p) {
        return 2 - (p.getLocation().getPitch() + 90) / 90;
    }

	public static String locString(Location loc) {
		return loc.getWorld().getName() + ", " + loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ();
	}

	public static boolean isBetween(int value, int start, int end) {
		return value >= start && value <= end;
	}

	public static float parseWalkingSpeed(Integer val) {return parseSpeed(val, false, false);}
	public static float parseWalkingSpeed(Integer val, boolean bypassLimits) {return parseSpeed(val, false, bypassLimits);}

	public static float parseFlyingSpeed(Integer val) {return parseSpeed(val, true, false);}
	public static float parseFlyingSpeed(Integer val, boolean bypassLimits) {return parseSpeed(val, true, bypassLimits);}

	public static float parseSpeed(Integer val, boolean isFly, boolean bypassLimits) {
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

	public static float distanceToGround(Location start) {
		int amount = 0;
		while (!start.subtract(0, 1, 0).getBlock().getType().isSolid()) {
		    amount++;
		}
		return amount;
	}

	public static Double distanceTo(Location loc1, Location loc2) {
		if (loc1 == null || loc2 == null) {return null;}
		return Math.sqrt((loc2.getX() - loc1.getX()) * (loc2.getX() - loc1.getX()) + (loc2.getZ() - loc1.getZ()) * (loc2.getZ() - loc1.getZ()));
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

	public static ArrayList<Block> getBlocksInRadius(Location center, int radius){
		ArrayList<Block> blocks = new ArrayList<Block>();
		for(double x = center.getX() - radius; x <= center.getX() + radius; x++){
			for(double y = center.getY() - radius; y <= center.getY() + radius; y++){
				for(double z = center.getZ() - radius; z <= center.getZ() + radius; z++){
					Location loc = new Location(center.getWorld(), x, y, z);
					blocks.add(loc.getBlock());
				}
			}
		}
		return blocks;
	}

	public static boolean isInside(Location pos, Location corner1, Location corner2) {
		double x1 = corner1.getX();
		double y1 = corner1.getY();
		double z1 = corner1.getZ();

		double x2 = corner2.getX();
		double y2 = corner2.getY();
		double z2 = corner2.getZ();

		return (pos.getX() > x1) && (pos.getY() > y1) && (pos.getZ() > z1) && (pos.getX() < x2) && (pos.getY() < y2) && (pos.getZ() < z2);
	}

} 
