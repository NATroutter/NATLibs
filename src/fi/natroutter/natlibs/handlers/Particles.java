package fi.natroutter.natlibs.handlers;

import fi.natroutter.natlibs.objects.ParticleSettings;
import fi.natroutter.natlibs.utilities.Utilities;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Queue;

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

    public static void spawnCircle(Player p, Location loc, ParticleSettings settings, int size) {
        for (int d = 0; d <= 90; d += 1) {
            Location particleLoc = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ());
            particleLoc.setX(loc.getX() + Math.cos(d) * size);
            particleLoc.setZ(loc.getZ() + Math.sin(d) * size);
            spawn(p, particleLoc, settings);
        }
    }

    public static void drawBlockOutlines(Player p, Location loc, double pointDistance, Color color) {
        ParticleSettings settings = new ParticleSettings(Particle.REDSTONE, 1, 0, 0, 0, 0);
        settings.setDustOptions(new Particle.DustOptions(color, 1));
        for (Location point : Utilities.getHollowCube(loc, pointDistance)) {
            Particles.spawn(p, point, settings);
        }
    }

    public static void drawPixel(Player p, Location loc, Color color) {
        ParticleSettings settings = new ParticleSettings(Particle.REDSTONE, 1, 0, 0, 0, 0);
        settings.setDustOptions(new Particle.DustOptions(color, 1));
        Particles.spawn(p, loc, settings);
    }

}
