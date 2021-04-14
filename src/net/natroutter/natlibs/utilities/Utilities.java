package net.natroutter.natlibs.utilities;

import com.sun.tools.javac.Main;
import net.natroutter.natlibs.NATLibs;
import net.natroutter.natlibs.objects.BaseItem;
import net.natroutter.natlibs.objects.BasePlayer;
import net.natroutter.natlibs.objects.ParticleSettings;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@SuppressWarnings({"unused"})
public class Utilities {

	public Utilities(JavaPlugin pl) {
	}
	
	public void consoleMessage(String msg) {
		Bukkit.getConsoleSender().sendMessage(msg);
	}
	
	public void glint(BaseItem item, boolean state) {
		item.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		if (state) {
			item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		} else {
			item.removeEnchantment(Enchantment.DURABILITY);
		}
		
	}

	public float pitchToFloat(Player p) {
        return 2 - (p.getLocation().getPitch() + 90) / 90;
    }
	
	public String getRegExMatch(String pattern, String data) {
		Matcher matcher = Pattern.compile(pattern).matcher(data);
		if (matcher.find()){
		    return matcher.group(1);
		}
		return null;
	}
	
	public String locString(Location loc) {
		return loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ();
	}
	
	public void printBanner() {
		PluginDescriptionFile pdf = NATLibs.getInstance().getDescription();
		consoleMessage("§4  _  _   _ _____ _    _ _       ");
		consoleMessage("§4 | \\| | /_\\_   _| |  (_) |__ ___");
		consoleMessage("§4 | .` |/ _ \\| | | |__| | '_ (_-<");
		consoleMessage("§4 |_|\\_/_/ \\_\\_| |____|_|_.__/__/");
		consoleMessage(" ");
		consoleMessage("      §cNATLibs §4v" + pdf.getVersion() + " §cloaded");
		consoleMessage(" §8Environment: §4" + Bukkit.getVersion());
		consoleMessage(" ");
	}
	
	public boolean isBetween(int Check, int start, int end) {
		return Check >= start && Check <= end;
	}
	
	public Float ParseSpeed(Integer val, boolean isFly) {return ParseSpeed(val,isFly,false);}
	public Float ParseSpeed(Integer val, boolean isFly, boolean bypassLimits) {
		float speed = val.floatValue();
		float defaultSpeed = isFly ? 0.1F : 0.2F;
	    float maxSpeed = 1.0F;
	    if (bypassLimits) {
	        maxSpeed = (isFly ? 10 : 10);
	    }
	    if (speed < 1.0F) {
	    	return defaultSpeed * speed;
	    }
	    float ratio = (speed - 1.0F) / 9.0F * (maxSpeed - defaultSpeed);
	    return ratio + defaultSpeed;
	}
	
	public Boolean FlipBool(Boolean bool) {
        return !bool;
	}
	
	public void spawnParticle(BasePlayer p, ParticleSettings settings) {
		p.spawnParticle(settings.getParticle(),
			settings.getLoc().getX(),settings.getLoc().getY(),settings.getLoc().getZ(),
			settings.getCount(),
			settings.getOffsetX(),settings.getOffsetY(),settings.getOffsetZ(),
			settings.getSpeed()
		);
	}
	 
	public void spawnParticleInRadius(ParticleSettings settings, int radius) {
		World world = settings.getLoc().getWorld();
		if (world == null) {return;}

		for (Entity ent : world.getNearbyEntities(settings.getLoc(), radius, radius, radius)) {
			if (ent instanceof Player) {
				BasePlayer p = BasePlayer.from(ent);
				spawnParticle(p, settings);
			}
		}
	}
	
	public Integer ToGround(Location start) {
		int amount = 0;
		while (!start.subtract(0, 1, 0).getBlock().getType().isSolid()) {
		    amount++;
		}
		return amount;
	}
	
	public boolean ItemsMatch(BaseItem item1, BaseItem item2) {
		if (item1.getDisplayName().equals(item2.getDisplayName())) {
			return item1.getLore().equals(item2.getLore());
		}
		return false;
	}
	
	public boolean NameMatch(BaseItem item1, BaseItem item2) {
		return item1.getDisplayName().equals(item2.getDisplayName());
	}
	
} 
