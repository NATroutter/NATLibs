package fi.natroutter.natlibs.handlers;

import com.sun.source.doctree.SeeTree;
import fi.natroutter.natlibs.objects.ParticleSettings;
import fi.natroutter.natlibs.utilities.Utilities;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Particles {


    public static void spawnWorld(Location location, ParticleSettings settings) {
        World world = location.getWorld();
        if (world == null) {return;}

        switch (settings.getParticle()) {
            case REDSTONE -> {
                if (settings.getDustOptions() == null) return;
                world.spawnParticle(settings.getParticle(), location,
                        settings.getCount(),
                        settings.getOffsetX(),settings.getOffsetY(),settings.getOffsetZ(),
                        settings.getSpeed(),
                        settings.getDustOptions()
                );
                Bukkit.getConsoleSender().sendMessage("§5debug: §d");
                Bukkit.getConsoleSender().sendMessage("   §d- " + settings.getParticle());
                Bukkit.getConsoleSender().sendMessage("   §d- " + Utilities.serializeLocation(location, '|'));
                Bukkit.getConsoleSender().sendMessage("   §d- " + settings.getCount());
                Bukkit.getConsoleSender().sendMessage("   §d- " + settings.getOffsetX() + ", " + settings.getOffsetY() + ", " + settings.getOffsetZ());
                Bukkit.getConsoleSender().sendMessage("   §d- " + settings.getSpeed());
                Bukkit.getConsoleSender().sendMessage("   §d- " + settings.getDustOptions().getSize());
                Bukkit.getConsoleSender().sendMessage("   §d- " + settings.getDustOptions().getColor().getRed() + ", " + settings.getDustOptions().getColor().getGreen() + ", " + settings.getDustOptions().getColor().getBlue());
            }
            case SPELL_MOB,SPELL_MOB_AMBIENT -> {
                if (settings.getSpellColor() == null) return;
                double red = settings.getSpellColor().getRed() / 255D;
                double green = settings.getSpellColor().getGreen() / 255D;
                double blue = settings.getSpellColor().getBlue() / 255D;
                world.spawnParticle(settings.getParticle(), location, 0, red, green, blue, 1);
            }
            case NOTE -> {
                double note = settings.getNote() / 24D; // 6 is the value of the red note
                world.spawnParticle(Particle.NOTE, location, 0, note, 0, 0, 1);
            }
            case DUST_COLOR_TRANSITION -> {
                if (settings.getDustTransition() == null) return;
                world.spawnParticle(settings.getParticle(), location,
                        settings.getCount(),
                        settings.getOffsetX(),settings.getOffsetY(),settings.getOffsetZ(),
                        settings.getSpeed(),
                        settings.getDustTransition()
                );
            }
            case ITEM_CRACK,BLOCK_CRACK,BLOCK_DUST,FALLING_DUST -> {
                if (settings.getItem() == null) return;
                world.spawnParticle(settings.getParticle(), location,
                        settings.getCount(),
                        settings.getOffsetX(),settings.getOffsetY(),settings.getOffsetZ(),
                        settings.getSpeed(),
                        settings.getItem()
                );
            }
            default -> {
                world.spawnParticle(settings.getParticle(), location,
                        settings.getCount(),
                        settings.getOffsetX(),settings.getOffsetY(),settings.getOffsetZ(),
                        settings.getSpeed()
                );
            }
        }
    }

    public static void spawn(Player p, Location location, ParticleSettings settings) {
        switch (settings.getParticle()) {
            case REDSTONE -> {
                if (settings.getDustOptions() == null) return;
                p.spawnParticle(settings.getParticle(), location,
                        settings.getCount(),
                        settings.getOffsetX(),settings.getOffsetY(),settings.getOffsetZ(),
                        settings.getSpeed(),
                        settings.getDustOptions()
                );
            }
            case SPELL_MOB,SPELL_MOB_AMBIENT -> {
                if (settings.getSpellColor() == null) return;
                double red = settings.getSpellColor().getRed() / 255D;
                double green = settings.getSpellColor().getGreen() / 255D;
                double blue = settings.getSpellColor().getBlue() / 255D;
                p.spawnParticle(settings.getParticle(), location, 0, red, green, blue, 1);
            }
            case NOTE -> {
                double note = settings.getNote() / 24D; // 6 is the value of the red note
                p.spawnParticle(Particle.NOTE, location.getX(),location.getY(),location.getZ(), 0, note, 0, 0, 1);
            }
            case DUST_COLOR_TRANSITION -> {
                if (settings.getDustTransition() == null) return;
                p.spawnParticle(settings.getParticle(), location,
                        settings.getCount(),
                        settings.getOffsetX(),settings.getOffsetY(),settings.getOffsetZ(),
                        settings.getSpeed(),
                        settings.getDustTransition()
                );
            }
            case ITEM_CRACK,BLOCK_CRACK,BLOCK_DUST,FALLING_DUST -> {
                if (settings.getItem() == null) return;
                p.spawnParticle(settings.getParticle(), location,
                        settings.getCount(),
                        settings.getOffsetX(),settings.getOffsetY(),settings.getOffsetZ(),
                        settings.getSpeed(),
                        settings.getItem()
                );
            }
            default -> {
                p.spawnParticle(settings.getParticle(),
                        location.getX(),location.getY(),location.getZ(),
                        settings.getCount(),
                        settings.getOffsetX(),settings.getOffsetY(),settings.getOffsetZ(),
                        settings.getSpeed()
                );
            }
        }
    }

    public static void spawnRadius(Location location, int radius, ParticleSettings settings) {
        World world = location.getWorld();
        if (world == null) {return;}

        for (Entity ent : world.getNearbyEntities(location, radius, radius, radius)) {
            if (ent instanceof Player p) {
                spawn(p, location, settings);
            }
        }
    }

    public static void spawnCircleWorld(Location loc, ParticleSettings settings, int size) {
        Utilities.getCircle(loc, size).forEach(particleLoc->{
            spawnWorld(particleLoc, settings);
        });
    }

    public static void spawnCircle(Player p, Location loc, ParticleSettings settings, int size) {
        Utilities.getCircle(loc, size).forEach(particleLoc->{
            spawn(p, particleLoc, settings);
        });
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
