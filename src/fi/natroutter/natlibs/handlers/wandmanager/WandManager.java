package fi.natroutter.natlibs.handlers.wandmanager;

import fi.natroutter.natlibs.handlers.Particles;
import fi.natroutter.natlibs.utilities.Utilities;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class WandManager {

    private static ConcurrentHashMap<UUID, Wand> wands = new ConcurrentHashMap<>();


    public WandManager(JavaPlugin plugin, int visualizerUpdateInterval) {
        visualizer(plugin, visualizerUpdateInterval);
    }

    public void register(Wand wand) {
        wands.put(wand.getUUID(), wand);
    }

    public void unregister(Wand wand) {
        wands.remove(wand.getUUID());
    }

    private void visualizer(Plugin plugin, int visualizerUpdateInterval) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, ()->{
            for (Map.Entry<UUID, Wand> wandEntry : wands.entrySet()) {
                Wand wand = wandEntry.getValue();
                if (!wand.args.isUseVisualizer()) continue;
                if (!wand.hasBothPos()) continue;

                for(Player p : Bukkit.getOnlinePlayers()) {
                    for (Location pos : wand.asHollowCube()) {
                        if (wand.args.getVisualizerPermission() != null) {
                            if (!p.hasPermission(wand.args.getVisualizerPermission())) continue;
                        }

                        double dist = Utilities.distanceTo(pos, p.getLocation());
                        if (dist > wand.args.getVisualizerRenderDistance()) continue;

                        Particles.spawn(p, pos.toCenterLocation(), wand.args.getParticle());
                    }
                }
            }
        },0,visualizerUpdateInterval);
    }

    protected static Wand getWand(ItemStack item) {
        for(Wand wand : wands.values()) {
            if (wand.getItem().isSimilar(item)) {
                return wand;
            }
        }
        return null;
    }

}
