package fi.natroutter.natlibs.handlers;

import fi.natroutter.natlibs.objects.ResolverInfo;
import fi.natroutter.natlibs.utilities.tagresolvers.LowerCaseTag;
import fi.natroutter.natlibs.utilities.tagresolvers.TitleCaseTag;
import fi.natroutter.natlibs.utilities.tagresolvers.UpperCaseTag;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class CustomResolver {

    private static ConcurrentHashMap<ResolverInfo, TagResolver> resolvers = new ConcurrentHashMap<>();

    public CustomResolver() {
        resolvers.put(new ResolverInfo("space", "sp", "Adds space"), Placeholder.component("sp", Component.space()));
        resolvers.put(new ResolverInfo("lowercase", "lowercase", "Converts text to TitleCase"),TagResolver.resolver("lowercase", new LowerCaseTag()));
        resolvers.put(new ResolverInfo("uppercase", "uppercase", "Converts text to UPPERCASE"),TagResolver.resolver("uppercase", new UpperCaseTag()));
        resolvers.put(new ResolverInfo("titlecase", "titlecase", "Converts text to lowercase"),TagResolver.resolver("titlecase", new TitleCaseTag()));
    }

    public static void add(ResolverInfo info, TagResolver resolver) {
        resolvers.put(info, resolver);
    }

    public static List<ResolverInfo> infos() {
        return new ArrayList<>(resolvers.keySet());
    }

    public static List<TagResolver> resolvers() {
        return new ArrayList<>(resolvers.values());
    }

}
