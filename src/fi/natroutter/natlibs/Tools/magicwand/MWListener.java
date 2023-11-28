package fi.natroutter.natlibs.Tools.magicwand;

import fi.natroutter.natlibs.utilities.Utilities;
import io.papermc.paper.math.Rotations;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class MWListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        ItemStack item = p.getInventory().getItemInMainHand();

        if (!MWHandler.isWand(item)) return;
        MagicMode mode = MWHandler.getMagicMode(item);

        for (Entity ent : p.getNearbyEntities(10, 10, 10)) {
            switch (mode) {
                case FRAME -> {
                    if (ent instanceof ItemFrame frame && frame.getPersistentDataContainer().has(MWHandler.frameKey)) {
                        Utilities.drawBlockOutlines(p, ent.getLocation(), 0.1, Color.fromRGB(235, 52, 232));
                    }
                }
                case STAND -> {
                    if (ent instanceof ArmorStand stand && stand.getPersistentDataContainer().has(MWHandler.standKey)) {
                        Utilities.drawBlockOutlines(p, ent.getLocation(), 0.1, Color.fromRGB(235, 52, 232));
                    }
                }
            }
        }

    }

    @EventHandler
    public void onScroll(PlayerItemHeldEvent e) {
        ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
        if (!MWHandler.isWand(item)) return;

        MagicMode mode = MWHandler.getMagicMode(item);

        Player p = e.getPlayer();
        int previousSlot = e.getPreviousSlot();
        int newSlot = e.getNewSlot();

        if (p.isSneaking()) {
            e.setCancelled(true);

            int diff = (newSlot - previousSlot + 9) % 9;
            if (diff == 1 || diff == -8) {
                MWHandler.setWandMode(item, mode.prev());
            } else if (diff == -1 || diff == 8) {
                MWHandler.setWandMode(item, mode.next());
            }
            MWHandler.updateName(item);
            p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, SoundCategory.MASTER, 1, 2);
        }
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player attacker)) return;

        ItemStack hand = attacker.getInventory().getItemInMainHand();
        if (hand.getType().isAir()) return;

        if (MWHandler.isWand(hand)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        ItemStack hand = e.getPlayer().getInventory().getItemInMainHand();
        if (hand.getType().isAir()) return;

        if (MWHandler.isWand(hand)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteractAtEntityEvent(PlayerInteractAtEntityEvent e) {
        ItemStack hand = e.getPlayer().getInventory().getItemInMainHand();
        if (hand.getType().isAir()) return;

        if (MWHandler.isWand(hand)) {
            if (!(e.getRightClicked() instanceof ArmorStand stand)) return;
            e.setCancelled(true);

            MagicMode mode = MWHandler.getMagicMode(hand);

            if (!mode.equals(MagicMode.STAND)) return;

            PersistentDataContainer data = stand.getPersistentDataContainer();

            if (!data.has(MWHandler.standKey)) {
                data.set(MWHandler.standKey, PersistentDataType.INTEGER, 1);

                stand.setLeftArmRotations(Rotations.ZERO);
                stand.setRightArmRotations(Rotations.ZERO);
                stand.setArms(true);
                stand.setGravity(false);
                return;
            }

            if (stand.isVisible()) {
                stand.setVisible(false);
                stand.setInvulnerable(true);
                stand.setCollidable(false);
                stand.setDisabledSlots(EquipmentSlot.values());
            } else {
                stand.setVisible(true);
                stand.setInvulnerable(false);
                stand.setCollidable(true);
                stand.removeDisabledSlots(EquipmentSlot.values());
            }

        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent e) {
        ItemStack hand = e.getPlayer().getInventory().getItemInMainHand();
        if (hand.getType().isAir()) return;

        if (MWHandler.isWand(hand)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemFrameBreak(HangingBreakEvent e) {
        if (!(e.getEntity() instanceof ItemFrame frame)) return;

        if (!frame.getPersistentDataContainer().has(MWHandler.frameKey)) return;

        if (e.getCause().equals(HangingBreakEvent.RemoveCause.ENTITY)) {
            if (!frame.isVisible()) {
                e.setCancelled(true);
            }
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onWandUse(PlayerInteractAtEntityEvent e) {
        Player p = e.getPlayer();
        if (!(e.getRightClicked() instanceof ItemFrame frame)) return;
        if (!p.hasPermission("natlibs.tools.magicwand")) {
            return;
        }
        ItemStack hand = e.getPlayer().getInventory().getItemInMainHand();
        if (hand == null || hand.getType().isAir()) return;

        if (MWHandler.isWand(hand)) {
            PersistentDataContainer itemData = hand.getItemMeta().getPersistentDataContainer();
            if (!itemData.has(MWHandler.wandMode)) return;

            MagicMode mode = MagicMode.valueOf(itemData.get(MWHandler.wandMode, PersistentDataType.STRING));

            if (!mode.equals(MagicMode.FRAME)) return;

            if (!frame.getPersistentDataContainer().has(MWHandler.frameKey)) {
                frame.getPersistentDataContainer().set(MWHandler.frameKey, PersistentDataType.INTEGER, 1);
            }
            if (frame.isVisible()) {
                frame.setVisible(false);
                frame.setInvulnerable(true);
                frame.setFixed(true);
            } else {
                frame.setVisible(true);
                frame.setInvulnerable(false);
                frame.setFixed(false);
            }
            e.setCancelled(true);
        }
    }
}
