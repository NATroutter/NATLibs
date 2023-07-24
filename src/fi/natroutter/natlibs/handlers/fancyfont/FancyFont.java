package fi.natroutter.natlibs.handlers.fancyfont;
import fi.natroutter.natlibs.handlers.fancyfont.fonts.BaseFont;
import org.bukkit.Bukkit;

import java.util.HashMap;

public class FancyFont {

    private static HashMap<FontRegistery, BaseFont> fonts = new HashMap<>();

    public static boolean register(FontRegistery id, BaseFont font) {
        if (fonts.containsKey(id)) return false;
        fonts.put(id, font);
        return true;
    }

    public static String transform(String string, FontRegistery font) {
        StringBuilder fancy = new StringBuilder();
        for (char c : string.toCharArray()) {
            BaseFont baseFont = fonts.get(font);
            if (baseFont == null) {
                Bukkit.getConsoleSender().sendMessage("§4[FancyFont] §cCould find font: §7" + font);
                fancy.append(c);
                continue;
            }
            FancyChar fChar = baseFont.find(c);
            if (fChar == null) {
                Bukkit.getConsoleSender().sendMessage("§4[FancyFont] §cCould not find char: §7" + c);
                fancy.append(c);
                continue;
            }

            if (Character.isUpperCase(c)) {
                fancy.append(fChar.getUpper());
            } else {
                fancy.append(fChar.getLower());
            }
        }
        return fancy.toString();
    }

}
