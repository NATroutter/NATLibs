package fi.natroutter.natlibs.handlers.guibuilder;

import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public abstract class GUIFrame {

    @Getter
    private Component title;

    @Getter
    private Rows rows;

    @Getter
    private String strippedTitle;

    @Getter @Setter
    private SoundSettings openSound;

    @Getter @Setter
    private SoundSettings closeSound;

    @Getter @Setter
    private SoundSettings clickSound;

    @Setter @Getter
    private ItemStack filler;

    @Setter @Getter
    private int maxPages = 0;

    public GUIFrame(Component title, Rows rows) {
        this.title = title;
        this.rows = rows;
        this.strippedTitle = PlainTextComponentSerializer.plainText().serialize(title);
    }

    protected abstract boolean onShow(Player player, GUI gui, List<Object> args);
    public void onClose(Player player, GUI gui){}

    public void setCloseSound(Sound sound, float volume, float pitch) {
        this.closeSound = new SoundSettings(sound, volume, pitch);
    }
    public void setOpenSound(Sound sound, float volume, float pitch) {
        this.openSound = new SoundSettings(sound, volume, pitch);
    }
    public void setClickSound(Sound sound, float volume, float pitch) {
        this.clickSound = new SoundSettings(sound, volume, pitch);
    }

    public boolean hasOpen(Player p) {
        return GUIListener.guis.containsKey(p.getUniqueId());
    }

    public void show(Player p) {show(p,null,true);}
    public void show(Player p, List<Object> args) {show(p,args,true);}

    protected void show(Player p, List<Object> args, boolean sound) {
        GUIListener.args.put(p.getUniqueId(), args);
        GUI gui = GUIListener.guis.get(p.getUniqueId());
        if (gui == null) {
            gui = new GUI(this);
            GUIListener.guis.put(p.getUniqueId(), gui);
        }
        Inventory inv = gui.getInv();

        inv.clear();

        if (!onShow(p, gui, args)) {
            return;
        }

        if (sound){
            if (openSound != null) {
                p.playSound(p.getLocation(), openSound.sound(), openSound.volume(), openSound.pitch());
            }
        }

        GUIListener.guis.put(p.getUniqueId(), gui);
        GUIListener.args.put(p.getUniqueId(), args);
        Inventory privInv = Bukkit.createInventory(p.getInventory().getHolder(), inv.getSize(), gui.getTitle());

        if (filler != null) {
            for (int i = 0; i < inv.getSize(); i++) {
                if (inv.getItem(i) == null || inv.getItem(i).getType() == Material.AIR) {
                    inv.setItem(i, filler);
                }
            }
        }

        privInv.setContents(gui.getInv().getContents().clone());
        p.openInventory(privInv);
    }

}