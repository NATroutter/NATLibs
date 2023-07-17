package fi.natroutter.natlibs.utilities;

import org.bukkit.Material;

import java.util.function.Consumer;

public class Parser {


    public static int integer(Object obj, int defaultValue) {
        try {
            return Integer.parseInt(obj.toString());
        } catch (Exception ignored) {}
        return defaultValue;
    }

    public static Material material(Object obj, Material defaultValue) {
        Material mat = Material.getMaterial(obj.toString().toUpperCase());
        if (mat != null) return mat;
        return defaultValue;
    }

}
