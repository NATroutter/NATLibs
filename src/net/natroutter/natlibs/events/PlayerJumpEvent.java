package net.natroutter.natlibs.events;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJumpEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final Player p;

    public PlayerJumpEvent(Player p) {
        this.p = p;
    }

    public Player getPlayer() {
        return p;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

   public static class PlayerJumpEventListener implements Listener {

        private final Map<UUID, Integer> jumps = new HashMap<>();

        @EventHandler(priority = EventPriority.MONITOR)
        public void onPlayerJoin(PlayerJoinEvent e) {
            jumps.put(e.getPlayer().getUniqueId(), e.getPlayer().getStatistic(Statistic.JUMP));
        }

        @EventHandler(priority = EventPriority.MONITOR)
        public void onPlayerQuit(PlayerQuitEvent e) {
            jumps.remove(e.getPlayer().getUniqueId());
        }

        @EventHandler(priority = EventPriority.MONITOR)
        public void onPlayerMove(PlayerMoveEvent e) {
            Player p = e.getPlayer();

            if(e.getFrom().getY() < e.getTo().getY()) {
                int current = p.getStatistic(Statistic.JUMP);
                int last = jumps.getOrDefault(p.getUniqueId(), -1);

                if(last != current) {
                    jumps.put(p.getUniqueId(), current);

                    double yDif = (long) ((e.getTo().getY() - e.getFrom().getY()) * 1000) / 1000d;

                    if((yDif < 0.035 || yDif > 0.037) && (yDif < 0.116 || yDif > 0.118)) {
                        Bukkit.getPluginManager().callEvent(new PlayerJumpEvent(p));
                    }
                }
            }
        }
    }

}
