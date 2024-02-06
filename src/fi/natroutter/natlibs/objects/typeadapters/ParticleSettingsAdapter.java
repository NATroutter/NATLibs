package fi.natroutter.natlibs.objects.typeadapters;

import dev.dejvokep.boostedyaml.serialization.standard.TypeAdapter;
import fi.natroutter.natlibs.objects.ParticleSettings;
import org.apache.commons.lang3.builder.EqualsExclude;
import org.apache.commons.lang3.math.NumberUtils;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class ParticleSettingsAdapter implements TypeAdapter<ParticleSettings> {


    @NotNull
    @Override
    public Map<Object, Object> serialize(@NotNull ParticleSettings settings) {
        Map<Object, Object> map = new HashMap<>();
        map.put("particle", settings.getParticle() != null ? settings.getParticle().name() : "REDSTONE");
        map.put("count", settings.getCount());
        map.put("offsetX", settings.getOffsetX());
        map.put("offsetY", settings.getOffsetY());
        map.put("offsetZ", settings.getOffsetZ());
        map.put("speed", settings.getSpeed());

        Map<Object, Object> dustOptions = new HashMap<>();
        dustOptions.put("size", settings.getDustOptions().getSize());
        dustOptions.put("color", settings.getDustOptions().getColor());
        map.put("dustOption", dustOptions);

        Map<Object, Object> dustTransition = new HashMap<>();
        dustTransition.put("color", settings.getDustTransition().getColor());
        dustTransition.put("colorTo", settings.getDustTransition().getToColor());
        dustTransition.put("size", settings.getDustTransition().getSize());
        map.put("dustTransition", dustTransition);

        map.put("material", settings.getItem().getType().isBlock() ? settings.getItem().getType().name() : "RED_CONCRETE");
        map.put("note", settings.getNote());
        map.put("spellColor", settings.getSpellColor());

        return map;
    }

    @NotNull
    @Override
    public ParticleSettings deserialize(@NotNull Map<Object, Object> map) {
        Particle particle = Particle.valueOf(map.get("particle").toString());
        int count = NumberUtils.toInt(map.get("count").toString(), 0);
        double offsetX = NumberUtils.toDouble(map.get("offsetX").toString(), 0);
        double offsetY = NumberUtils.toDouble(map.get("offsetY").toString(), 0);
        double offsetZ = NumberUtils.toDouble(map.get("offsetZ").toString(), 0);
        double speed = NumberUtils.toDouble(map.get("speed").toString(), 0);

        Map<Object, Object> dMap = (Map<Object, Object>)map.get("dustOption");
        Color dColor = (Color)dMap.get("color");
        float dSize = NumberUtils.toFloat(dMap.get("size").toString(), 0);
        Particle.DustOptions dustOpts = new Particle.DustOptions(dColor, dSize);

        Map<Object, Object> tMap = (Map<Object, Object>)map.get("dustTransition");
        Color tColor = (Color) tMap.get("color");
        Color tColorTo = (Color) tMap.get("colorTo");
        float tSize = NumberUtils.toFloat(tMap.get("size").toString(), 0);
        Particle.DustTransition dustTransition = new Particle.DustTransition(tColor, tColorTo, tSize);

        Material mat = Material.getMaterial(map.get("material").toString().toUpperCase());
        ItemStack item = new ItemStack(mat.isBlock() ? mat : Material.RED_CONCRETE);

        int note = NumberUtils.toInt(map.get("note").toString(), 0);
        Color spellColor = (Color) map.get("spellColor");

        ParticleSettings set = new ParticleSettings(particle, count, offsetX, offsetY, offsetZ, speed, dustOpts);
        set.setDustTransition(dustTransition);
        set.setItem(item);
        set.setNote(note);
        set.setSpellColor(spellColor);
        return set;
    }
}
