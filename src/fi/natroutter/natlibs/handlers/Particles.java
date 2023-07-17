package fi.natroutter.natlibs.handlers;

import fi.natroutter.natlibs.objects.ParticleSettings;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Particles {

    public static void spawnWorld(Location location, ParticleSettings settings) {
        World world = location.getWorld();
        if (world == null) {return;}
        if (settings.getDustOptions() == null) {
            world.spawnParticle(settings.getParticle(),
                    location.getX(),location.getY(),location.getZ(),
                    settings.getCount(),
                    settings.getOffsetX(),settings.getOffsetY(),settings.getOffsetZ(),
                    settings.getSpeed()
            );
        } else {
            world.spawnParticle(settings.getParticle(),
                    location.getX(),location.getY(),location.getZ(),
                    settings.getCount(),
                    settings.getOffsetX(),settings.getOffsetY(),settings.getOffsetZ(),
                    settings.getSpeed(),
                    settings.getDustOptions()
            );
        }
    }

    public static void spawn(Player p, Location location, ParticleSettings settings) {
        if (settings.getDustOptions() == null)  {
            p.spawnParticle(settings.getParticle(),
                    location.getX(),location.getY(),location.getZ(),
                    settings.getCount(),
                    settings.getOffsetX(),settings.getOffsetY(),settings.getOffsetZ(),
                    settings.getSpeed()
            );
        } else {
            p.spawnParticle(settings.getParticle(),
                    location.getX(),location.getY(),location.getZ(),
                    settings.getCount(),
                    settings.getOffsetX(),settings.getOffsetY(),settings.getOffsetZ(),
                    settings.getSpeed(),
                    settings.getDustOptions()
            );
        }
    }

    public static void spawnRadius(Location location, int radius, ParticleSettings settings) {
        World world = location.getWorld();
        if (world == null) {return;}

        for (Entity ent : world.getNearbyEntities(location, radius, radius, radius)) {
            if (ent instanceof Player) {
                Player p = (Player)ent;
                spawn(p, location, settings);
            }
        }
    }

}
