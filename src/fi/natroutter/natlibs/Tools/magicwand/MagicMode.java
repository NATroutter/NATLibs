package fi.natroutter.natlibs.Tools.magicwand;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.checkerframework.checker.units.qual.A;

@AllArgsConstructor @Getter
public enum MagicMode {

    FRAME("ItemFrame"),
    STAND("ArmorStand");

    private String friendlyName;


    private static final MagicMode[] values = values();

    public MagicMode prev() {
        return values[(ordinal() - 1  + values.length) % values.length];
    }

    public MagicMode next() {
        return values[(this.ordinal() + 1) % values().length];
    }
}
