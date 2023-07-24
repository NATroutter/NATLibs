package fi.natroutter.natlibs.commands;

import fi.natroutter.natlibs.NATLibs;
import fi.natroutter.natlibs.handlers.CustomResolver;
import fi.natroutter.natlibs.handlers.database.YamlDatabase;
import fi.natroutter.natlibs.objects.DualString;
import fi.natroutter.natlibs.utilities.Theme;
import fi.natroutter.natlibs.utilities.Utilities;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.serializer.json.JSONComponentSerializer;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MiniMessageViewer extends Command implements Listener {

    private NamespacedKey namespacedKey = new NamespacedKey(NATLibs.getInstance(), "MiniMessageViewer");
    private YamlDatabase database = NATLibs.getDatabase();

    public MiniMessageViewer() {
        super("MiniMessageViewer");
        this.setAliases(Collections.singletonList("mmv"));
    }

    private Component helpMessage() {
        return Theme.multiline(
                new DualString("Right-Click", "Hide/Show armorstand hologram"),
                new DualString("", ""),
                new DualString("/mmv Help", "Shows this help message"),
                new DualString("/mmv Settings <Show/SettingsName> <Value>", "Change settings"),
                new DualString("/mmv Resolvers", "Shows the list of registered custom resolvers"),
                new DualString("/mmv <Name,N> <MiniMessage>", "Set name to item in hand"),
                new DualString("/mmv <Lore,L> <MiniMessage>", "Set lore to item in hand"),
                new DualString("/mmv <Chat,C> <MiniMessage>", "Show message in chat"),
                new DualString("/mmv <Holo,H> <MiniMessage>", "Show message as hologram")
        );
    }

    private ItemStack getItem(Player p) {
        ItemStack item = p.getInventory().getItemInMainHand();
        if (item == null) {
            item = p.getInventory().getItemInOffHand();
        }
        if (item == null || item.getType().isAir()) {
            Theme.sendError(p, Theme.Error.INVALID_ITEM);
            return null;
        }
        return item;
    }

    private String stateString(boolean state) {
        return (state ? "Enabled" : "Disabled");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!(sender instanceof Player p)) {
            Theme.sendError(sender, Theme.Error.ONLY_INGAME);
            return false;
        }
        if (!p.hasPermission("natlibs.tools.minimessageviewer")) {
            Theme.sendError(sender, Theme.Error.NO_PERMISSION);
            return false;
        }
        if (args.length == 0) {
            p.sendMessage(helpMessage());
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("help")) {
                p.sendMessage(helpMessage());
                return true;
            } else if (args[0].equalsIgnoreCase("resolvers")) {

                List<DualString> resoList = new ArrayList<>();
                resoList.add(new DualString("Registered resolvers", String.valueOf(CustomResolver.resolvers().size())));
                resoList.add(new DualString("", ""));
                resoList.addAll(CustomResolver.infos().stream().map(data->new DualString(data.getTitle(), data.getDescription())).toList());
                p.sendMessage(Theme.multiline("<bold>»</bold>",true,true,resoList.toArray(new DualString[0])));
                return true;
            }
            Theme.sendError(sender, Theme.Error.INVALID_ARGUMENTS);

        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("settings")) {
                if (args[1].equalsIgnoreCase("show")) {
                    p.sendMessage(Theme.multiline("<bold>»</bold>",true,true,
                            new DualString("HoloVisible", stateString(database.getBoolean("Tools.MiniMessageViewer", "HoloVisible"))),
                            new DualString("ClickToVisible", stateString(database.getBoolean("Tools.MiniMessageViewer", "ClickToVisible")))
                    ));
                    return true;
                }
            }
            return mmCommand(p, args);
        } else if (args.length == 3) {
            if (args[0].equalsIgnoreCase("settings")) {
                switch (args[1].toLowerCase()) {
                    case "holovisible" -> {
                        boolean state = Boolean.parseBoolean(args[2]);
                        p.sendMessage(Theme.prefixed("Hologram visibility set to: " + Theme.highlight(stateString(state))));
                        database.save("Tools.MiniMessageViewer", "HoloVisible", state);
                        return true;
                    }
                    case "clicktovisible" -> {
                        boolean state = Boolean.parseBoolean(args[2]);
                        p.sendMessage(Theme.prefixed("Click visibility set to: " + Theme.highlight(stateString(state))));
                        database.save("Tools.MiniMessageViewer", "ClickToVisible", state);
                        return true;
                    }
                }
            }
            return mmCommand(p, args);
        } else {
            return mmCommand(p, args);
        }
        return false;
    }

    private String getMessage(Player p, String[] args) {
        String message = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
        if (NATLibs.getPapiHook().isHooked()) {
            message = PlaceholderAPI.setPlaceholders(p, message);
        }
        return message;
    }

    private boolean mmCommand(Player p, String[] args) {
        switch (args[0].toLowerCase()) {
            case "name", "n" -> {
                ItemStack item = getItem(p);
                if (item == null) return false;
                item.editMeta(meta->
                        meta.displayName(Utilities.translateColors(getMessage(p, args), CustomResolver.resolvers().toArray(new TagResolver[0])))
                );
                p.sendMessage(Theme.prefixed("Item editted!"));
                return true;
            }
            case "lore", "l" -> {
                ItemStack item = getItem(p);
                if (item == null) return false;
                String message = getMessage(p, args);
                List<Component> comp = Arrays.stream(message.split("<br>")).map(m->
                        Utilities.translateColors(m, CustomResolver.resolvers().toArray(new TagResolver[0]))
                ).toList();
                item.editMeta(meta->
                        meta.lore(comp)
                );
                p.sendMessage(Theme.prefixed("Item editted!"));
                return true;
            }
            case "chat", "c" -> {
                String message = getMessage(p, args);
                Arrays.stream(message.split("<br>")).map(m->
                        Utilities.translateColors(m, CustomResolver.resolvers().toArray(new TagResolver[0]))
                )
                .map(c-> c.clickEvent(ClickEvent.copyToClipboard(JSONComponentSerializer.json().serialize(c))))
                .map(c-> c.hoverEvent(Theme.main("Click to copy raw json!")))
                .forEach(p::sendMessage);
                return true;
            }
            case "holo", "h" -> {
                String message = getMessage(p, args);
                World world = p.getWorld();
                Location spawnLoc = p.getLocation().add(0,-2,0);
                float adjustment = 0.25f;

                Arrays.stream(message.split("<br>")).forEach(line->{
                    if (line.isEmpty()) {
                        spawnLoc.add(0, -adjustment, 0);
                    } else {
                        ArmorStand stand = (ArmorStand)world.spawnEntity(spawnLoc, EntityType.ARMOR_STAND);
                        stand.getPersistentDataContainer().set(namespacedKey, PersistentDataType.INTEGER, 1);
                        stand.setCustomNameVisible(true);
                        stand.customName(Utilities.translateColors(line, CustomResolver.resolvers().toArray(new TagResolver[0])));
                        stand.setGravity(false);
                        stand.setVisible(database.getBoolean("Tools.MiniMessageViewer", "HoloVisible"));
                        spawnLoc.add(0, -adjustment, 0);
                    }
                });
                p.sendMessage(Theme.prefixed("Hologram spawned!"));
                return true;
            }
        }
        return false;
    }

    @EventHandler
    public void onArmorStandClick(PlayerInteractAtEntityEvent e) {
        Player p = e.getPlayer();
        if (!(e.getRightClicked() instanceof ArmorStand stand)) return;
        if (!p.hasPermission("natlibs.tools.minimessageviewer")) {
            return;
        }
        if (stand.getPersistentDataContainer().has(namespacedKey, PersistentDataType.INTEGER)) {
            if (database.getBoolean("Tools.MiniMessageViewer", "ClickToVisible")) {
                stand.setVisible(!stand.isVisible());
            }
        }
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {

        if (!sender.hasPermission("natlibs.tools.minimessageviewer")) {
            Theme.sendError(sender, Theme.Error.NO_PERMISSION);
            return Utilities.emptyTab();
        }

        if (args.length == 1) {
            return Utilities.getCompletes(sender, args[0],Arrays.asList(
                    "Help", "Name", "Lore", "Chat","Holo", "Settings", "Resolvers"
            ));
        } else if (args.length == 2) {
            switch (args[0].toLowerCase()) {
                case "settings" -> {
                    return Utilities.getCompletes(sender, args[1], Arrays.asList(
                            "HoloVisible", "Show", "ClickToVisible"
                    ));
                }
            }
            Utilities.emptyTab();
        } else if (args.length == 3) {
            if (args[0].equalsIgnoreCase("settings")) {
                if (args[1].toLowerCase().equalsIgnoreCase("HoloVisible")) {
                    String response = String.valueOf(!database.getBoolean("Tools.MiniMessageViewer", "HoloVisible"));
                    return Utilities.getCompletes(sender, args[2], Collections.singletonList(response));

                } else if (args[1].toLowerCase().equalsIgnoreCase("ClickToVisible")) {
                    String response = String.valueOf(!database.getBoolean("Tools.MiniMessageViewer", "ClickToVisible"));
                    return Utilities.getCompletes(sender, args[2], Collections.singletonList(response));
                }
                return Utilities.emptyTab();
            }
            return Utilities.emptyTab();
        } else {
            if (args[0].equalsIgnoreCase("settings")) {
                return Utilities.emptyTab();
            }
            return Utilities.emptyTab();
        }
        return super.tabComplete(sender, alias, args);
    }
}
