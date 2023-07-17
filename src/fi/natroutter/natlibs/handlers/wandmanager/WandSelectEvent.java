package fi.natroutter.natlibs.handlers.wandmanager;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class WandSelectEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {return handlers;}
    public static HandlerList getHandlerList() {return handlers;}

    @Getter private final Player player;
    @Getter private final Side side;
    @Getter private final Wand wand;

    public WandSelectEvent(Player player, Side side, Wand wand) {
        this.player = player;
        this.side = side;
        this.wand = wand;
    }

    public static class WandSelectEventListener implements Listener {

        @EventHandler(priority = EventPriority.MONITOR)
        public void onInteract(PlayerInteractEvent e) {

            if (!(e.getAction().equals(Action.LEFT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK))) return;
            if (!e.hasBlock() || e.getClickedBlock() == null || !e.hasItem()) return;

            Player player = e.getPlayer();
            Action action = e.getAction();
            Block block = e.getClickedBlock();
            ItemStack item = e.getItem();

            Side side = action.equals(Action.LEFT_CLICK_BLOCK) ? Side.LEFT : Side.RIGHT;

            Wand wand = WandManager.getWand(item);
            if (wand == null) return;

            e.setCancelled(true);

            if (side.equals(Side.LEFT)) {
                wand.args.setPos1(block.getLocation());
            } else {
                wand.args.setPos2(block.getLocation());
            }

            Bukkit.getPluginManager().callEvent(new WandSelectEvent(player, side, wand));
        }

    }

}