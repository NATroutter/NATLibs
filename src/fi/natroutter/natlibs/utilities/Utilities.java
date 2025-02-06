package fi.natroutter.natlibs.utilities;

import com.google.common.collect.Lists;
import fi.natroutter.natlibs.handlers.Particles;
import fi.natroutter.natlibs.objects.BaseItem;
import fi.natroutter.natlibs.objects.ParticleSettings;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;


@SuppressWarnings({"unused"})
public class Utilities {

	public static ArrayList<Location> getTwoPointCircle(Location loc, double size, int increment){
		ArrayList<Location> locations = new ArrayList<>();
		for (int d = 0; d <= 180; d += increment) {
			Location point1 = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ());
			point1.setX(loc.getX() + Math.cos(Math.toRadians(d)) * size);
			point1.setZ(loc.getZ() + Math.sin(Math.toRadians(d)) * size);
			locations.add(point1);

			Location point2 = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ());
			point2.setX(loc.getX() + Math.cos(Math.toRadians(d + 180)) * size);
			point2.setZ(loc.getZ() + Math.sin(Math.toRadians(d + 180)) * size);
			locations.add(point2);
		}
		return locations;
	}

	public static ArrayList<Location> getCircle(Location loc, double size){
		ArrayList<Location> locations = new ArrayList<>();
		for (int d = 0; d <= 90; d += 1) {
			Location point = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ());
			point.setX(loc.getX() + Math.cos(d) * size);
			point.setZ(loc.getZ() + Math.sin(d) * size);
			locations.add(point);
		}
		return locations;
	}

	public static Color colorArrayFade(List<Color> colors, AtomicInteger index) {
		if (index.get() > colors.size()-1) { index.set(0); }
		return colors.get(index.getAndIncrement());
	}

	public static List<Color> generateLoopFade(Color color1, Color color2, int FadeSteps) {
		List<Color> colors = new ArrayList<>();
		colors.addAll(generateOneDirectionFade(color1, color2, FadeSteps));
		colors.addAll(generateOneDirectionFade(color2, color1, FadeSteps));
		return colors;
	}

	public static List<Color> generateOneDirectionFade(Color color1, Color color2, int FadeSteps) {
		List<Color> colors = new ArrayList<>();
		final int dRed = color2.getRed() - color1.getRed();
		final int dGreen = color2.getGreen() - color1.getGreen();
		final int dBlue = color2.getBlue() - color1.getBlue();
		if (dRed != 0 || dGreen != 0 || dBlue != 0) {
			for (int i = 0; i <= FadeSteps; i++) {
				final Color c = Color.fromBGR(
						color1.getRed() + ((dRed * i) / FadeSteps),
						color1.getGreen() + ((dGreen * i) / FadeSteps),
						color1.getBlue() + ((dBlue * i) / FadeSteps)
				);
				colors.add(c);
			}
		}
		return colors;
	}

	public static boolean canItemFit(Inventory inventory, ItemStack item) {
		if (inventory.firstEmpty() != -1) {
			return true;
		}
		for (ItemStack stack : inventory.getStorageContents()) {
			if (stack == null) {
				return true;
			}
			if (item.isSimilar(stack)) {
				if ((stack.getAmount() + item.getAmount()) <= stack.getMaxStackSize()) {
					return true;
				}
			}
		}
		return false;
	}

	public static <E extends Enum<E>> E findEnumValue(String name, Class<E> enumClass) {
		if (enumClass.isEnum()) {
			E[] values = enumClass.getEnumConstants();
			for (E value : values) {
				if (value.name().equalsIgnoreCase(name)) {
					return value;
				}
			}
			return values[0];
		} else {
			throw new IllegalArgumentException("The provided class is not an enum.");
		}
	}

	public static boolean isNumberic(Object obj) {
		return obj.toString().matches("^[0-9]+$");
	}
	public static boolean isDouble(Object obj) {
		return obj.toString().matches("[-+]?[0-9]*\\.?[0-9]+(?:[eE][-+]?[0-9]+)?");
	}

	public static String currencyFormat(double balance) {
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.ENGLISH);
		DecimalFormatSymbols sym = formatter.getDecimalFormatSymbols();
		sym.setCurrencySymbol("");
		sym.setDecimalSeparator(',');
		sym.setGroupingSeparator('.');
		formatter.setDecimalFormatSymbols(sym);
		return formatter.format(balance);
	}

	public static void glint(BaseItem item, boolean state) {
		item.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		if (state) {
			item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		} else {
			item.removeEnchantment(Enchantment.DURABILITY);
		}
	}
	public static String parse(String value, TagResolver... placeholders) {
		if (placeholders == null) return value;
		return Colors.plain(Colors.translate(value, placeholders));
	}

	public static boolean locationMatch(Location loc1, Location loc2) {
		if (loc1.getWorld() == null || loc2.getWorld() == null) {
			return loc1.getBlockX() == loc2.getBlockX() &&
					loc1.getBlockY() == loc2.getBlockY() &&
					loc1.getBlockZ() == loc2.getBlockZ();
		} else {
			return loc1.getWorld().equals(loc2.getWorld()) &&
					loc1.getBlockX() == loc2.getBlockX() &&
					loc1.getBlockY() == loc2.getBlockY() &&
					loc1.getBlockZ() == loc2.getBlockZ();
		}
	}

	public static List<Location> getHollowCube(Location loc, double pointDistance) {
		List<Location> result = Lists.newArrayList();
		World world = loc.getWorld();
		double minX = loc.getBlockX();
		double minY = loc.getBlockY();
		double minZ = loc.getBlockZ();
		double maxX = loc.getBlockX()+1;
		double maxY = loc.getBlockY()+1;
		double maxZ = loc.getBlockZ()+1;

		for (double x = minX; x <= maxX; x = Math.round((x + pointDistance) * 1e2) / 1e2) {
			for (double y = minY; y <= maxY; y = Math.round((y + pointDistance) * 1e2) / 1e2) {
				for (double z = minZ; z <= maxZ; z = Math.round((z + pointDistance) * 1e2) / 1e2) {
					int components = 0;
					if (x == minX || x == maxX) components++;
					if (y == minY || y == maxY) components++;
					if (z == minZ || z == maxZ) components++;
					if (components >= 2) {
						result.add(new Location(world, x, y, z));
					}
				}
			}
		}
		return result;
	}

	public static String toTitleCase(String sentence) {
		if (sentence == null || sentence.isEmpty()) {
			return sentence;
		}
		StringBuilder titleCase = new StringBuilder();
		boolean nextTitleCase = true;
		for (char c : sentence.toCharArray()) {
			if (Character.isSpaceChar(c)) {
				nextTitleCase = true;
			} else if (nextTitleCase) {
				c = Character.toTitleCase(c);
				nextTitleCase = false;
			} else {
				c = Character.toLowerCase(c);
			}
			titleCase.append(c);
		}
		return titleCase.toString();
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

	public static Location centerLocation(Location loc) {
		return centerLocation(loc,false);
	}
	public static Location centerLocation(Location loc, boolean centerY) {
		return loc.clone().set(loc.getBlockX()+0.5, (centerY ? loc.getBlockY()+0.5 : loc.getBlockY()), loc.getBlockZ()+0.5);
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
