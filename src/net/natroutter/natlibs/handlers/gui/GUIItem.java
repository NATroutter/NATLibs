package net.natroutter.natlibs.handlers.gui;

import org.bukkit.event.inventory.InventoryClickEvent;

import net.natroutter.natlibs.objects.BaseItem;

public class GUIItem {
	private Consumer<InventoryClickEvent> invClick;
    private BaseItem item;

    public GUIItem(BaseItem item, Consumer<InventoryClickEvent> toRun) {
        this.invClick = toRun;
        this.item = item;
    }

    public BaseItem getItem() {
        return this.item;
    }

    public void invClick(InventoryClickEvent e) {
        this.invClick.accept(e);
    }
}
