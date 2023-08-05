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

    public static double doublee(Object obj, double defaultValue) {
        try {
            return Double.parseDouble(obj.toString());
        } catch (Exception ignored) {}
        return defaultValue;
    }

    public static float floatt(Object obj, float defaultValue) {
        try {
            return Float.parseFloat(obj.toString());
        } catch (Exception ignored) {}
        return defaultValue;
    }

    public static boolean bool(Object obj, boolean defaultValue) {
        try {
            return Boolean.parseBoolean(obj.toString());
        } catch (Exception ignored) {}
        return defaultValue;
    }

    public static String string(Object obj, String defaultValue) {
        try {
            return obj.toString();
        } catch (Exception ignored) {}
        return defaultValue;
    }

    public static <T> T object(Object obj, Class<T> type, T defaultValue) {
        try {
            return type.cast(obj);
        } catch (Exception ignored) {}
        return defaultValue;
    }

    public static Material material(Object obj, Material defaultValue) {
        Material mat = Material.getMaterial(obj.toString().toUpperCase());
        if (mat != null) return mat;
        return defaultValue;
    }

}
