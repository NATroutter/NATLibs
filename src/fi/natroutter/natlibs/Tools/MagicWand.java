package fi.natroutter.natlibs.Tools;

import fi.natroutter.natlibs.NATLibs;
import fi.natroutter.natlibs.objects.BaseItem;
import fi.natroutter.natlibs.utilities.Theme;
import fi.natroutter.natlibs.utilities.Utilities;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class MagicWand extends Command implements Listener {

    private NamespacedKey frameKey = new NamespacedKey(NATLibs.getInstance(), "FrameKey");

    public MagicWand() {
        super("MagicWand");
        this.setAliases(Collections.singletonList("mw"));
    }




    //TODO

    /// TOISTEN TYÖKALUJEN VALITSEMINEN SHIFT SCROLL ITEM KÄDES!!







    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!(sender instanceof Player p)) {
            Theme.sendError(sender, Theme.Error.ONLY_INGAME);
            return false;
        }
        if (!p.hasPermission("natlibs.tools.magicwand")) {
            Theme.sendError(sender, Theme.Error.NO_PERMISSION);
            return false;
        }
        if (args.length == 0) {
            p.getInventory().addItem(wand());
            p.sendMessage(Theme.prefixed("MagicWand added to your inventory!"));
        } else {
            Theme.sendError(sender, Theme.Error.INVALID_ARGUMENTS);
        }
        return false;
    }

    private BaseItem wand() {
        BaseItem item = new BaseItem(Material.END_ROD);
        item.name(Utilities.translateColors("<gradient:#3a34eb:#eb34e8><bold>MagicWand"));
        item.lore(List.of(
                Utilities.translateColors("<color:#b0b0b0>Magical Wand that can rewrite past and future!"),
                Utilities.translateColors("<color:#b0b0b0>It makes item frames invisible...."),
                Component.text(" "),
                Utilities.translateColors("<color:#b0b0b0>(other features may be added later)")
        ));
        item.setGlow(true);
        item.addItemFlags(ItemFlag.values());
        return item;
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if (wand().isSimilar(e.getItemInHand())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        ItemStack hand = e.getPlayer().getInventory().getItemInMainHand();
        if (hand == null || hand.getType().isAir()) return;

        if (wand().isSimilar(hand)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemFrame(PlayerInteractEntityEvent e) {
        ItemStack hand = e.getPlayer().getInventory().getItemInMainHand();
        if (hand == null || hand.getType().isAir()) return;

        if (wand().isSimilar(hand)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemFrameBreak(HangingBreakEvent e) {
        if (!(e.getEntity() instanceof ItemFrame frame)) return;
        if (e.getCause().equals(HangingBreakEvent.RemoveCause.ENTITY)) {
            if (!frame.isVisible()) {
                e.setCancelled(true);
            }
            return;
        }
        if (!frame.getPersistentDataContainer().has(frameKey)) return;
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

        if (wand().isSimilar(hand)) {
            if (!frame.getPersistentDataContainer().has(frameKey)) {
                frame.getPersistentDataContainer().set(frameKey, PersistentDataType.INTEGER, 1);
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
