package fi.natroutter.natlibs.handlers.guibuilder;

import fi.natroutter.natlibs.objects.BaseItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.function.BiConsumer;

public class SwitchButton extends Button {


    public SwitchButton(Material material, GUIFrame gui) {
        super(material, (e, oldGUI) -> {
            oldGUI.switchGUI(e.getPlayer(), gui);
        });
    }

    public SwitchButton(ItemStack item, GUIFrame gui) {
        super(item, (e, oldGUI) -> {
            oldGUI.switchGUI(e.getPlayer(), gui);
        });
    }

    public SwitchButton(BaseItem display, GUIFrame gui) {
        super(display, (e, oldGUI) -> {
            oldGUI.switchGUI(e.getPlayer(), gui);
        });
    }

    //.........

    public SwitchButton(Material material, GUIFrame gui, List<Object> args) {
        super(material, (e, oldGUI) -> {
            oldGUI.switchGUI(e.getPlayer(), gui, args);
        });
    }

    public SwitchButton(ItemStack item, GUIFrame gui, List<Object> args) {
        super(item, (e, oldGUI) -> {
            oldGUI.switchGUI(e.getPlayer(), gui, args);
        });
    }

    public SwitchButton(BaseItem display, GUIFrame gui, List<Object> args) {
        super(display, (e, oldGUI) -> {
            oldGUI.switchGUI(e.getPlayer(), gui, args);
        });
    }
}
