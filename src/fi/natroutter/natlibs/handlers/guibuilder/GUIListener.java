package fi.natroutter.natlibs.handlers.guibuilder;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GUIListener implements Listener {

    protected static final Map<UUID, GUI> guis = new HashMap<>();
    protected static final Map<UUID, List<Object>> args = new HashMap<>();

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getWhoClicked() instanceof Player p) {
            GUI gui = guis.get(p.getUniqueId());

            if (gui != null) {
                GUIFrame frame = gui.getFrame();
                Button item = gui.getButton(e.getSlot());
                e.setResult(Event.Result.DENY);
                e.setCancelled(true);

                if (e.getClickedInventory() == null) { return; }
                if (e.getClickedInventory().getType().equals(InventoryType.PLAYER)) {return;}
                if (item == null) { return; }
                if (item.getType().equals(Material.AIR)) { return; }

                if (frame.getClickSound() != null) {
                    if (frame.getClickSound().isEnumSound()) {
                        p.playSound(p.getLocation(), frame.getClickSound().getSound(), frame.getClickSound().getVolume(), frame.getClickSound().getPitch());
                    } else {
                        p.playSound(p.getLocation(), frame.getClickSound().getStrSound(), frame.getClickSound().getVolume(), frame.getClickSound().getPitch());
                    }
                }
                item.clickEvent.accept(new ClickAction(
                        p,e.getClick(),e.getAction(),e.getClickedInventory(),
                        e.getCursor(),e.getCurrentItem(),e.getSlot(),e.getSlotType(),e.getView()
                ), gui);
                if (!gui.closing) {
                    gui.getFrame().update(p, args.get(p.getUniqueId()), false);
                }
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        if(e.getReason().equals(InventoryCloseEvent.Reason.OPEN_NEW))return;
        if(e.getReason().equals(InventoryCloseEvent.Reason.PLUGIN))return;
        if (e.getPlayer() instanceof Player p) {

            GUI gui = guis.get(p.getUniqueId());
            if(gui != null){
                GUIFrame frame = gui.getFrame();

                if (frame.getCloseSound() != null) {
                    if (frame.getCloseSound().isEnumSound()) {
                        p.playSound(p.getLocation(), frame.getCloseSound().getSound(), frame.getCloseSound().getVolume(), frame.getCloseSound().getPitch());
                    } else {
                        p.playSound(p.getLocation(), frame.getCloseSound().getStrSound(), frame.getCloseSound().getVolume(), frame.getCloseSound().getPitch());
                    }
                }
                frame.onClose(p, gui);
                guis.remove(p.getUniqueId());
                args.remove(p.getUniqueId());
            }
        }
    }

    @EventHandler
    public void onInteract(InventoryInteractEvent e) {
        if (e.getWhoClicked() instanceof Player p) {
            GUI gui = guis.get(p.getUniqueId());
            if(gui != null){
                e.setResult(Event.Result.DENY);
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        guis.remove(e.getPlayer().getUniqueId());
        args.remove(e.getPlayer().getUniqueId());
    }

}
