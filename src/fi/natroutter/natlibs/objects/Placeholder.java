package fi.natroutter.natlibs.objects;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

public record Placeholder(String tag, Object value) {

    public TagResolver.Single getResolver() {
        if (value instanceof Component comp) {
            return TagResolver.resolver(tag(), Tag.inserting(comp));
        }
        return TagResolver.resolver(tag(), Tag.inserting(Component.text(value().toString())));
    }

}
