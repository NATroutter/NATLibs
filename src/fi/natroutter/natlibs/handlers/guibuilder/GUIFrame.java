package fi.natroutter.natlibs.handlers.guibuilder;

import fi.natroutter.natlibs.configuration.IConfig;
import fi.natroutter.natlibs.objects.BaseItem;
import fi.natroutter.natlibs.utilities.Colors;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;

public abstract class GUIFrame {

    @Getter
    private String rawTitle;

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
    private BaseItem filler;

    @Setter @Getter
    private int maxPages = 0;

    private PlainTextComponentSerializer plain = PlainTextComponentSerializer.plainText();

    public GUIFrame(String title, Rows rows) {
        this.rawTitle = title;
        this.rows = rows;
        this.strippedTitle = plain.serialize(Colors.translate(title));

        openSound = new SoundSettings(Sound.BLOCK_CHEST_OPEN, 100, 1);
        closeSound = new SoundSettings(Sound.BLOCK_CHEST_CLOSE, 100, 1);
        clickSound = new SoundSettings(Sound.UI_BUTTON_CLICK, 100, 1);
        filler = new BaseItem(Material.BLACK_STAINED_GLASS_PANE).name(" ");
    }

    public GUIFrame(IConfig title, Rows rows) {
        this(title.asString(), rows);
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

    protected void update(Player p, List<Object> args, boolean sound) {
        GUI gui = GUIListener.guis.get(p.getUniqueId());
        if (gui != null) {
            show(p, gui, args,sound);
        }
    }

    public void switchGUI(Player p, GUIFrame frame) {
        switchGUI(p, frame, null);
    }

    public void switchGUI(Player p, GUIFrame frame, List<Object> args) {
        GUI gui = GUIListener.guis.get(p.getUniqueId());
        if (gui != null) {
            gui.closing = true;
            frame.show(p, args, false);
        }
    }

    public void show(Player p) {show(p,null,true);}
    public void show(Player p, List<Object> args) {show(p,args,true);}

    protected void show(Player p, List<Object> args, boolean sound) {
        show(p, new GUI(this), args, sound);
    }

    protected void show(Player p, GUI gui, List<Object> args, boolean sound) {
        GUIListener.guis.put(p.getUniqueId(), gui);
        GUIListener.args.put(p.getUniqueId(), args);
        Inventory inv = gui.getInv();

        inv.clear();

        if (!onShow(p, gui, args)) {
            gui.closing = true;
            GUIListener.guis.remove(p.getUniqueId());
            GUIListener.args.remove(p.getUniqueId());
            p.closeInventory();
            return;
        }

        if (sound){
            if (openSound != null) {
                if (openSound.isEnumSound()) {
                    p.playSound(p.getLocation(), openSound.getSound(), openSound.getVolume(), openSound.getPitch());
                } else {
                    p.playSound(p.getLocation(), openSound.getStrSound(), openSound.getVolume(), openSound.getPitch());
                }
            }
        }

        GUIListener.guis.put(p.getUniqueId(), gui);
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
