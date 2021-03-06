package net.natroutter.natlibs.utilities;

import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.plugin.java.JavaPlugin;

import net.natroutter.natlibs.objects.BaseItem;
import net.natroutter.natlibs.objects.BasePlayer;
import net.natroutter.natlibs.objects.ParticleSettings;


@SuppressWarnings({"unused"})
public class Utilities {

	private JavaPlugin pl;
	
	public Utilities(JavaPlugin pl) {
		this.pl = pl;
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
	
	public Entity[] GetNearbyEntities(Location l, int radius) {
        int chunkRadius = radius < 16 ? 1 : (radius - (radius % 16)) / 16;
        HashSet<Entity> radiusEntities = new HashSet<Entity>();
        for (int chX = 0 - chunkRadius; chX <= chunkRadius; chX++) {
            for (int chZ = 0 - chunkRadius; chZ <= chunkRadius; chZ++) {
                int x = (int) l.getX(), y = (int) l.getY(), z = (int) l.getZ();
                for (Entity e : new Location(l.getWorld(), x + (chX * 16), y, z
                        + (chZ * 16)).getChunk().getEntities()) {
                    if (e.getLocation().distance(l) <= radius
                            && e.getLocation().getBlock() != l.getBlock()) {
                        radiusEntities.add(e);
                    }
                }
            }
        }
        return radiusEntities.toArray(new Entity[radiusEntities.size()]);
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
		consoleMessage("§4  _  _   _ _____ _    _ _       ");
		consoleMessage("§4 | \\| | /_\\_   _| |  (_) |__ ___");
		consoleMessage("§4 | .` |/ _ \\| | | |__| | '_ (_-<");
		consoleMessage("§4 |_|\\_/_/ \\_\\_| |____|_|_.__/__/");
		consoleMessage(" ");
		consoleMessage("      §cNATLibs §4v1.0 §cloaded");
		consoleMessage(" §8Environment: §4" + Bukkit.getVersion());
		consoleMessage(" ");
	}
	
	public boolean isBetween(int Check, int start, int end) {
		if (Check >= start && Check <= end) {
			return true;
		}
		return false;
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
        return bool ? false : true;
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
		for (Entity ent : settings.getLoc().getWorld().getNearbyEntities(settings.getLoc(), radius, radius, radius)) {
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
			if (item1.getLore().equals(item2.getLore())) {
				return true;
			}
		}
		return false;
	}
	
	public boolean NameMatch(BaseItem item1, BaseItem item2) {
		if (item1.getDisplayName().equals(item2.getDisplayName())) {
			return true;
		}return false;
	}
	
} 
