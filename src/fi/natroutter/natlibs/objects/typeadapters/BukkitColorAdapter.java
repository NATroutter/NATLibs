package fi.natroutter.natlibs.objects.typeadapters;

import dev.dejvokep.boostedyaml.serialization.standard.TypeAdapter;
import org.apache.commons.lang3.math.NumberUtils;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class BukkitColorAdapter implements TypeAdapter<Color> {


    @NotNull
    @Override
    public Map<Object, Object> serialize(@NotNull Color color) {
        Map<Object, Object> map = new HashMap<>();
        map.put("alpha", color.getAlpha());
        map.put("red", color.getRed());
        map.put("green", color.getGreen());
        map.put("blue", color.getBlue());
        return map;
    }

    @NotNull
    @Override
    public Color deserialize(@NotNull Map<Object, Object> map) {
        int alpha = NumberUtils.toInt(map.get("alpha").toString(), 0);
        int red = NumberUtils.toInt(map.get("red").toString(), 0);
        int green = NumberUtils.toInt(map.get("green").toString(), 0);
        int blue = NumberUtils.toInt(map.get("blue").toString(), 0);
        return Color.fromARGB(alpha,red,green,blue);
    }
}
