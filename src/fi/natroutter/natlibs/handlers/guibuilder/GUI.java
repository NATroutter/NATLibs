package fi.natroutter.natlibs.handlers.guibuilder;

import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class GUI {

    @Getter
    private GUIFrame frame;

    @Getter @Setter
    private Component title;

    @Getter
    private Inventory inv;
    private int page = 1;

    private ConcurrentHashMap<Integer, Button> buttons = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Integer, DisplayItem> items = new ConcurrentHashMap<>();

    public GUI(GUIFrame frame){
        this.frame = frame;
        this.title = frame.getTitle();
        this.inv = Bukkit.createInventory(null, frame.getRows().getRow()*9, frame.getTitle());
    }

    public int getPage() {
        return this.page;
    }

    public void changePage(int page) {
        this.page = page;
        if (this.page < 1) {
            this.page = 1;
            return;
        }
        if (this.page > frame.getMaxPages()) {
            this.page = frame.getMaxPages();
        }
    }

    public Button getButton(Rows rows, int slot) {
        return getButton(rows.getSlots()+slot);
    }
    public Button getButton(int slot) {
        return buttons.get(slot);
    }

    public DisplayItem getItem(Rows rows, int slot) {
        return getItem(rows.getSlots()+slot);
    }
    public DisplayItem getItem(int slot) {
        return items.get(slot);
    }

    public void setButton(Button button, Rows rows, int slot) {
        setButton(button, rows.getSlots()+slot);
    }

    public void setButton(Button button, int slot) {
        buttons.put(slot, button);
        inv.setItem(slot, button.getItem());
    }

    public void setItem(DisplayItem item, Rows rows, int slot) {
        setItem(item, rows.getSlots()+slot);
    }

    public void setItem(DisplayItem item, int slot) {
        items.put(slot, item);
        inv.setItem(slot, item.getItem());
    }

    private <T> List<T> getPageContent(List<T> items, int page, int pageSize) {
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, items.size());
        return items.subList(start, end);
    }

    private int getPageCount(int totalItems, int pageSize) {
        int fullPages = totalItems / pageSize;
        int remainingItems = totalItems % pageSize;
        return fullPages + (remainingItems > 0 ? 1 : 0);
    }

    public void paginateButtons(int startSlot, int endSlot, List<Button> items) {
        endSlot = endSlot + 1;
        int pageCount = getPageCount(items.size(), endSlot - startSlot);
        List<Button> pageItems = getPageContent(items, this.getPage(), endSlot - startSlot);

        this.getFrame().setMaxPages(pageCount);
        for(int i = startSlot ; i < pageItems.size() + startSlot; i++) {
            this.setButton(pageItems.get(i - startSlot), i);
        }
    }

    public void paginateItems(int startSlot, int endSlot, List<DisplayItem> items) {
        endSlot = endSlot + 1;
        int pageCount = getPageCount(items.size(), endSlot - startSlot);
        List<DisplayItem> pageItems = getPageContent(items, this.getPage(), endSlot - startSlot);

        this.getFrame().setMaxPages(pageCount);
        for(int i = startSlot ; i < pageItems.size() + startSlot; i++) {
            this.setItem(pageItems.get(i - startSlot), i);
        }
    }

}
