package net.natroutter.natlibs.handlers.LangHandler.language.translation;

import com.google.common.collect.Lists;
import net.natroutter.natlibs.handlers.LangHandler.language.Language;
import org.bukkit.ChatColor;

import java.util.List;
import java.util.Locale;

public class Translation {

    private final Language language;
    private final List<String> translations;
    private boolean found;

    private Translation(final Language language, final List<String> translations, boolean found) {
        this.language = language;
        this.translations = translations;
        this.found = found;
    }

    public static Translation of(Language language, final String translation , boolean found) {
        return new Translation(language, Lists.newArrayList(translation), found);
    }

    public static Translation of(Language language, final List<String> translations, boolean found) {
        return new Translation(language, translations, found);
    }

    public boolean found() {
        return found;
    }

    /**
     * Gets the language of this translation.
     * @return the translation's language
     */
    public Language getLanguage() {
        return language;
    }

    /**
     * Formats the translation with the provided values, using {@link String#format(String, Object...)}
     * @param values formatting values
     */
    public void format(Object... values) {
        for (int i = 0; i < translations.size(); i++) {
            final String translation = translations.get(i);
            translations.set(i, String.format(Locale.ROOT, translation, values));
        }
    }

    /**
     * Gets the raw, unformatted text for this translation.
     * A translation may contain multiple elements.
     * @return raw, unformatted translation text
     */
    public List<String> getTranslations() {
        return translations;
    }

    /**
     * Gets a coloured string representation, as formatted by {@link ChatColor#translateAlternateColorCodes(char, String)}
     * @return string representation of coloured text
     */
    public List<String> colour() {
        List<String> colouredText = Lists.newArrayList();
        translations.forEach(translation -> {
            if (!translation.isEmpty()) colouredText.add(colour(translation));
        });
        return colouredText;
    }

    private String colour(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
