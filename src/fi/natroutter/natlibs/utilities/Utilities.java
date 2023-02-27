package fi.natroutter.natlibs.utilities;

import fi.natroutter.natlibs.objects.BaseItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.standard.StandardTags;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Pattern;


@SuppressWarnings({"unused"})
public class Utilities {

	private MiniMessage mm = MiniMessage.builder().build();
	private LegacyComponentSerializer lcs = LegacyComponentSerializer.legacyAmpersand();

	final JavaPlugin pl;

	public Utilities(JavaPlugin pl) {
		this.pl = pl;
	}
	
	public void consoleMessage(String msg) {
		Bukkit.getConsoleSender().sendMessage(msg);
	}

	public String CurrencyFormat(double balance) {
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.ENGLISH);
		DecimalFormatSymbols sym = formatter.getDecimalFormatSymbols();
		sym.setCurrencySymbol("");
		sym.setDecimalSeparator(',');
		sym.setGroupingSeparator('.');
		formatter.setDecimalFormatSymbols(sym);
		return formatter.format(balance);
	}

	public void glint(BaseItem item, boolean state) {
		item.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		if (state) {
			item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		} else {
			item.removeEnchantment(Enchantment.DURABILITY);
		}
	}

	public String plain(Component comp) {
		return PlainTextComponentSerializer.plainText().serialize(comp);
	}

	public Component translateColors(String str) {
		Component comp = mm.deserialize(str);
		String s = lcs.serialize(comp);
		return lcs.deserialize(s);
	}

	public float pitchToFloat(Player p) {
        return 2 - (p.getLocation().getPitch() + 90) / 90;
    }

	public String locString(Location loc) {
		return loc.getWorld().getName() + ", " + loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ();
	}

	public boolean isBetween(int Check, int start, int end) {
		return Check >= start && Check <= end;
	}
	
	public Float parseSpeed(Integer val, boolean isFly) {return parseSpeed(val,isFly,false);}
	public Float parseSpeed(Integer val, boolean isFly, boolean bypassLimits) {
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

	public Integer distanceToGround(Location start) {
		int amount = 0;
		while (!start.subtract(0, 1, 0).getBlock().getType().isSolid()) {
		    amount++;
		}
		return amount;
	}

	public String serializeLocation(Location loc, Character separator) {
		String sep = separator.toString();
		return loc.getWorld().getName() + sep + loc.getX() + sep + loc.getY() + sep + loc.getZ()  + sep + loc.getYaw()  + sep + loc.getPitch();
	}

	public Location deserializeLocation(String loc, Character separator) {
		String sep = separator.toString();
		String[] parts = loc.split(Pattern.quote(sep));
		if (parts.length == 6) {
			try {
				return new Location(
						Bukkit.getWorld(parts[0]),
						Double.parseDouble(parts[1]), Double.parseDouble(parts[2]), Double.parseDouble(parts[3]),
						Float.parseFloat(parts[4]), Float.parseFloat(parts[5]));
			} catch (Exception e) {
				consoleMessage("§4["+pl.getName()+"][Serializer] invalid values entered to location deserializer");
			}
		}
		return null;
	}

	public ArrayList<Block> getBlocks(Location start, int radius){
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

	public boolean inRegion(Location loc, Location loc1, Location loc2) {
		double x1 = loc1.getX();
		double y1 = loc1.getY();
		double z1 = loc1.getZ();

		double x2 = loc2.getX();
		double y2 = loc2.getY();
		double z2 = loc2.getZ();

		return (loc.getX() > x1) && (loc.getY() > y1) && (loc.getZ() > z1) && (loc.getX() < x2) && (loc.getY() < y2) && (loc.getZ() < z2);
	}

} 
