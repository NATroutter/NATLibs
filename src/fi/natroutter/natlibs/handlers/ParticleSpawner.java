package fi.natroutter.natlibs.handlers;

import fi.natroutter.natlibs.objects.ParticleSettings;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class ParticleSpawner {

    public void spawnParticleWorld(ParticleSettings settings) {
        World world = settings.getLoc().getWorld();
        if (world == null) {return;}
        world.spawnParticle(settings.getParticle(),
                settings.getLoc().getX(),settings.getLoc().getY(),settings.getLoc().getZ(),
                settings.getCount(),
                settings.getOffsetX(),settings.getOffsetY(),settings.getOffsetZ(),
                settings.getSpeed()
        );
    }

    public void spawnParticle(Player p, ParticleSettings settings) {
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
                Player p = (Player)ent;
                spawnParticle(p, settings);
            }
        }
    }

}
