package net.natroutter.natlibs.handlers.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;

import net.natroutter.natlibs.objects.BaseItem;
import net.natroutter.natlibs.objects.BasePlayer;

import java.util.HashMap;
import java.util.Map;

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
	private Rows row;
	private BaseItem FillerItem = null;
	private Boolean ClickSounds = true;
	private Boolean OpenSound = true;
	
    public GUIWindow(String name, Rows row) {
		this.name = name;
		this.row = row;
		init();
    }

    public GUIWindow(String name, Rows row, Boolean FillEmpty) {
    	this.name = name;
		this.row = row;
    	this.FillEmpty = FillEmpty;
    	init();
	}
    
    public GUIWindow(String name, Rows row, Boolean FillEmpty, Boolean ClickSounds) {
    	this.name = name;
		this.row = row;
    	this.FillEmpty = FillEmpty;
    	this.ClickSounds = ClickSounds;
    	init();
	}
    
    public GUIWindow(String name, Rows row, Boolean FillEmpty, Boolean ClickSounds, Boolean OpenSound) {
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
    
    public void setItem(GUIItem item, Rows row, Integer slot) {
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

    public void show(BasePlayer p) {
    	show(p, false);
    }
    
	public void show(BasePlayer p, Boolean publicInv) {
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
		
		Inventory privateInv = Bukkit.createInventory(p, inv.getSize(), this.name);
		privateInv.setContents(inv.getContents());
		p.openInventory(privateInv);
		
	}

    public void unregister() {
        windows.remove(StripedName);
        this.items.clear();
    }

	public static GUIWindow getWindow(InventoryView view) {
		return windows.get(ChatColor.stripColor(view.getTitle()));
	}
	
	public enum Rows {
		row1(1), row2(2), row3(3),
		row4(4), row5(5), row6(6);
		
		private int row;
		Rows(Integer row) { this.row = row; }
		public int getRow() { return (row - 1) * 9; }
		public int getInvRow() { return row* 9; }
	}
	
	private BaseItem Filler() {
		BaseItem item = new BaseItem(Material.BLACK_STAINED_GLASS_PANE);
		item.addItemFlags(ItemFlag.values());
		item.setDisplayName(" ");
		return item;
	}
}
