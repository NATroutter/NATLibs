package net.natroutter.natlibs.handlers.gui;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.*;

import net.natroutter.natlibs.objects.BaseItem;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SuppressWarnings("unused")
public class GUIWindow {
    private static Map<String, GUIWindow> windows = new HashMap<>();
 
    private Inventory inv;
    private Map<Integer, GUIItem> items;
	private Consumer<InventoryOpenEvent> onOpen = null;
	private Consumer<InventoryCloseEvent> onClose = null;

	private String name;
	private String StripedName;
	private Boolean FillEmpty = false;
	private GUIRow row;
	private BaseItem FillerItem = null;
	private Boolean ClickSounds = true;
	private Boolean OpenSound = true;
	
    public GUIWindow(String name, GUIRow row) {
		this.name = name;
		this.row = row;
		init();
    }

    public GUIWindow(String name, GUIRow row, Boolean FillEmpty) {
    	this.name = name;
		this.row = row;
    	this.FillEmpty = FillEmpty;
    	init();
	}
    
    public GUIWindow(String name, GUIRow row, Boolean FillEmpty, Boolean ClickSounds) {
    	this.name = name;
		this.row = row;
    	this.FillEmpty = FillEmpty;
    	this.ClickSounds = ClickSounds;
    	init();
	}
    
    public GUIWindow(String name, GUIRow row, Boolean FillEmpty, Boolean ClickSounds, Boolean OpenSound) {
    	this.name = name;
		this.row = row;
    	this.FillEmpty = FillEmpty;
    	this.ClickSounds = ClickSounds;
    	this.OpenSound = OpenSound;
    	init();
	}
    
    private void init() {
    	this.StripedName = ChatColor.stripColor(name);
   	
    	inv = Bukkit.createInventory(null, row.getInvRow() ,name);
        items = new HashMap<>(row.getRow());

		windows.put(StripedName, this);
    }
    
    public Boolean isOpenSound() {
    	return OpenSound;
    }
    
    public Boolean isClickSound() {
    	return ClickSounds;
    }
    
    public void setItem(GUIItem item, Integer slot) {
        items.put(slot, item);
        inv.setItem(slot, item.getItem());
    }
    
    public void setItem(GUIItem item, GUIRow row, Integer slot) {
		setItem(item, slot + row.getRow());
	}
    
    public void setFillerItem(BaseItem item) {
    	FillerItem = item;
    }

	public void setOpenEvent(Consumer<InventoryOpenEvent> e) { this.onOpen = e; }
	public void setCloseEvent(Consumer<InventoryCloseEvent> e) { this.onClose = e; }
	
	public void callOpen(InventoryOpenEvent e) {
		if(onOpen != null) {
			onOpen.accept(e);
		}
	}
	public void callClosed(InventoryCloseEvent e) {
		if(onClose != null) {
			onClose.accept(e);
		}
	}

	public GUIItem getItem(int slot) { return this.items.get(slot); }
    public Inventory getInventory() { return this.inv; }
    public String getName() { return this.name; }
    public String getStripedName() { return StripedName; }
    
    public BaseItem getFillerItem() {
    	if (FillerItem == null) {
    		return Filler();
		} else {
			return FillerItem;
		}
    }

    public void show(Player p) {
    	show(p, false);
    }
	public void show(Player p, Boolean publicInv) {
		Inventory inv = getInventory();
		
		if (OpenSound) {
			p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 100, 1);
		}
		
		if (FillEmpty) {
			for (int i = 0; i < inv.getSize(); i++) {
				if (inv.getItem(i) == null || inv.getItem(i).getType() == Material.AIR) {
					inv.setItem(i, getFillerItem());
				}
			}
		}
		
		if (publicInv) {
			p.openInventory(getInventory());
			return;
		}
		
		Inventory privateInv = Bukkit.createInventory(p.getInventory().getHolder(), inv.getSize(), Component.text(this.name));
		privateInv.setContents(inv.getContents().clone());
		p.openInventory(privateInv);
		
	}

    public void unregister() {
        windows.remove(StripedName);
        this.items.clear();
    }

	public static GUIWindow getWindow(InventoryView view) {
		return windows.get(ChatColor.stripColor(view.getTitle()));
	}

	private BaseItem Filler() {
		BaseItem item = new BaseItem(Material.BLACK_STAINED_GLASS_PANE);
		item.addItemFlags(ItemFlag.values());
		item.setDisplayName(" ");
		return item;
	}
}
