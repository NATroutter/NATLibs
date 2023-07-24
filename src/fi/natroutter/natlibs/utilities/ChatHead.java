package fi.natroutter.natlibs.utilities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.A;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ChatHead {

    private static String printCharacter = "█";
    private static HeadAPI lastAPI = HeadAPI.MINEPIC;

    @AllArgsConstructor @Getter
    private enum HeadAPI {
        MINEPIC("https://minepic.org/avatar/%s/%s"),
        MINOTAR("https://minotar.net/helm/%s/%s.png"),
        CRAFT_HEADS("https://cravatar.eu/helmavatar/%s/%s.png"),
        ;

        private final String url;

        private static final HeadAPI[] vals = values();

        public HeadAPI next() {
            return switch (this) {
                case MINEPIC -> MINOTAR;
                case MINOTAR -> CRAFT_HEADS;
                case CRAFT_HEADS -> MINEPIC;
            };
        }

        public String getUrl(String user, int scale) {
            return String.format(url, user, scale);
        }
    }

    public static void send(Player p, int scale, Component... messages) {
        render(p.getName(), scale, messages);
    }

    @SneakyThrows
    public static Component render(String user, int scale, Component... messages) {
        HeadAPI useAPI = lastAPI.next();
        String urlString = useAPI.getUrl(user, scale);

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
        lastAPI = useAPI;
        System.out.println("This is rendered by: " + useAPI.name());
        return Component.join(JoinConfiguration.newlines(), columns);
    }

}
