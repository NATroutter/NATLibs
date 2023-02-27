package fi.natroutter.natlibs.handlers.guibuilder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

@Getter @AllArgsConstructor
public class ClickAction {

    private Player player;
    private ClickType clickType;
    private InventoryAction action;
    private Inventory clickedInventory;
    private ItemStack cursor;
    private ItemStack currentItem;
    private int slot;
    private InventoryType.SlotType slotType;
    private InventoryView inventoryView;

}
