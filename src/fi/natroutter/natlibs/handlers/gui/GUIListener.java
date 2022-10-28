package fi.natroutter.natlibs.handlers.gui;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;

public class GUIListener implements Listener {
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player) {
			Player p = (Player)e.getWhoClicked();
			GUIWindow window = GUIWindow.getWindow(e.getView());

			if (window != null) {
				GUIItem item = window.getItem(e.getSlot());

				e.setResult(Event.Result.DENY);
				e.setCancelled(true);

				if (e.getClickedInventory() == null) { return; }
				if (e.getClickedInventory().getType().equals(InventoryType.PLAYER)) {return;}
				if (item == null) { return; }
				if (item.getItem() == null) { return; }
				if (item.getItem().getType().equals(Material.AIR)) { return; }

				if (window.isClickSound()) {
					p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 100, 1);
				}

				item.invClick(e);

			}
		}
	}

	@EventHandler
	public void onOpen(InventoryOpenEvent e) {
		GUIWindow window = GUIWindow.getWindow(e.getView());
		if(window != null) {
			window.callOpen(e);
		}
	}

	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		GUIWindow window = GUIWindow.getWindow(e.getView());
		if(window != null) {
			window.callClosed(e);
		}
	}

	@EventHandler
	public void onInteract(InventoryInteractEvent e) {
		if(GUIWindow.getWindow(e.getView()) != null){
			e.setResult(Event.Result.DENY);
			e.setCancelled(true);
		}
	}
}
