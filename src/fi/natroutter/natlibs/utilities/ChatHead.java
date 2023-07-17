package fi.natroutter.natlibs.utilities;

import lombok.SneakyThrows;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ChatHead {

    private static String printCharacter = "█";

    public static void send(Player p, int scale, Component... messages) {
        render(p.getName(), scale, messages);
    }

    @SneakyThrows
    public static Component render(String user, int scale, Component... messages) {
        String urlString = "https://minotar.net/avatar/"+user+"/" + scale + ".png";

        List<Component> columns = new ArrayList<>();

        URL url = new URL(urlString);
        BufferedImage image = ImageIO.read(url);
        for (int i = 0; i < image.getHeight(); i++) {
            List<Component> row = new ArrayList<>();

            for (int j = 0; j < image.getWidth(); j++) {
                Color color = new Color(image.getRGB(j, i));
                TextColor blockColor = TextColor.color(color.getRed(), color.getGreen(), color.getBlue());

                row.add(Component.text(printCharacter).color(blockColor));
            }
            Component headRow = Component.join(JoinConfiguration.noSeparators(), row);
            if (messages != null && messages.length >= i+1) {
                columns.add(Component.join(JoinConfiguration.noSeparators(), headRow, messages[i]));
            } else {
                columns.add(headRow);
            }
        }
        return Component.join(JoinConfiguration.newlines(), columns);
    }

}
