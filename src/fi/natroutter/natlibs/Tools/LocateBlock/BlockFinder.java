package fi.natroutter.natlibs.Tools.LocateBlock;

import fi.natroutter.natlibs.utilities.Theme;
import fi.natroutter.natlibs.utilities.Utilities;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class BlockFinder {


    private JavaPlugin plugin;
    private int scheduleID;
    private boolean searchActive = false;
    private Player activator;
    private LocalDateTime timestamp;
    private Material searchBlock;
    private int searchRadius;
    private BukkitTask task;

    public BlockFinder(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void startSchedule() {
        scheduleID = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, ()->{
            if (searchActive) {
                if (activator != null) {
                    if (activator.isOnline()) {
                        activator.sendActionBar(Utilities.translateColors(
                                Theme.main("Searching") + " " + Theme.highlight(searchBlock.name()) + " &8| " + Theme.main("Radius") + " " + Theme.highlight(String.valueOf(searchRadius)) + " &8| " + Theme.main("Time") + " " + Theme.highlight(timeEnlapsed())
                        ));
                    }
                }
            } else {
                Bukkit.getScheduler().cancelTask(scheduleID);
            }
        }, 0, 20L);
    }

    public void abort(Player p) {
        if (searchActive) {
            task.cancel();
            searchActive = false;
            if (activator != null) {
                if (activator.isOnline()) {
                    if (!activator.getUniqueId().equals(p.getUniqueId())) {
                        activator.sendMessage(Theme.prefixed("Your search was cancelled by " + Theme.highlight(p.getName())));
                    }
                }
            }
            activator = null;
            timestamp = null;
            p.sendMessage(Theme.prefixed("Search canceled!"));
        } else {
            p.sendMessage(Theme.prefixed("Search is not currently active!"));
        }
    }

    public void info(Player p) {
        if (searchActive) {
            if (activator != null) {
                Location l = activator.getLocation();
                p.sendMessage(Utilities.translateColors("<dark_gray><bold>&m━━━━━━━━━━━━|</bold></dark_gray> " +Theme.main("<bold>SearchInfo</bold>")+ " <dark_gray><bold>|&m━━━━━━━━━━━━</bold></dark_gray>"));

                ZonedDateTime time = ZonedDateTime.of(timestamp, ZoneId.of("Europe/Helsinki"));

                p.sendMessage(Utilities.translateColors("&8<bold>»</bold> <color:#b3b3b3>Start time:</color> " + Theme.highlight(time.getDayOfMonth() + "." + time.getMonthValue() + "." + time.getYear() + " " + time.getHour() + ":" + time.getMinute())));
                p.sendMessage(Utilities.translateColors("&8<bold>»</bold> <color:#b3b3b3>Time past:</color> " + Theme.highlight(timeEnlapsed())));
                p.sendMessage(Utilities.translateColors("&8<bold>»</bold> <color:#b3b3b3>Started by:</color> " + Theme.highlight(activator.getName())));



                Component message = Utilities.translateColors("&8<bold>»</bold> <color:#b3b3b3>Started at:</color> &8("+ Theme.highlight(l.getWorld().getName()+", "+l.getBlockX()+", "+l.getBlockY()+", "+l.getBlockZ()) +"&8) ");
                Component btn = Utilities.translateColors(Theme.main("<bold>[TELEPORT]</bold>"))
                        .clickEvent(ClickEvent.runCommand("/minecraft:tp " + p.getName() + " " + l.getBlockX() + " " + l.getBlockY() + " " + l.getBlockZ()))
                        .hoverEvent(HoverEvent.showText(Theme.mainC("Click to teleport")));
                message = message.append(btn);
                p.sendMessage(message);

                p.sendMessage(Utilities.translateColors("&8<bold>»</bold> <color:#b3b3b3>Searching for:</color> " + Theme.highlight(searchBlock.name())));
                p.sendMessage(Utilities.translateColors("&8<bold>»</bold> <color:#b3b3b3>Search Radius:</color> " + Theme.highlight(String.valueOf(searchRadius))));
                p.sendMessage(Utilities.translateColors("<dark_gray><bold>&m━━━━━━━━━━━━|</bold></dark_gray> " +Theme.main("<bold>SearchInfo</bold>")+ " <dark_gray><bold>|&m━━━━━━━━━━━━</bold></dark_gray>"));
                p.sendMessage(" ");
            }
        } else {
            p.sendMessage(Theme.prefixed("Search is not currently active!"));
        }
    }

    public void locateBlocks(Player p, Material searchBlock, Integer radius) {
        if (task != null) {
            if (task.isCancelled()) {
                searchActive = false;
            }
        }

        if (searchActive) {
            p.sendMessage(Theme.prefixed("Another search is already active!"));
            return;
        }

        task = Bukkit.getScheduler().runTaskAsynchronously(plugin, ()-> {
            searchActive = true;
            activator = p;
            timestamp = LocalDateTime.now();
            searchRadius = radius;
            this.searchBlock = searchBlock;
            startSchedule();

            p.sendMessage(Theme.prefixed("Searching " + Theme.highlight(searchBlock.name()) + " in radius of " + Theme.highlight(radius.toString())));
            if (radius > 50) {
                p.sendMessage(Theme.prefixed("This may take a while..."));
            }

            List<Block> blocks = getNearbyBlocks(p.getLocation(), radius);
            List<Block> filtered = new ArrayList<>();

            for(Block block : blocks) {
                if (!searchActive)return;
                if (block.getType().equals(searchBlock)) {
                    filtered.add(block);
                }
            }
            if (!searchActive)return;

            if (filtered.size() > 0) {
                p.sendMessage(Utilities.translateColors("<dark_gray><bold>&m━━━━━━━━━━━━|</bold></dark_gray> " +Theme.main("<bold>LocateBlock</bold>")+ " <dark_gray><bold>|&m━━━━━━━━━━━━</bold></dark_gray>"));
                for (Block lb : filtered) {
                    if (!searchActive)return;
                    Location l = lb.getLocation();

                    Component message = Utilities.translateColors("&8<bold>»</bold> " + Theme.highlight(lb.getType().name()) + " &8| " + Theme.highlight(String.valueOf(l.getBlockX())) + "&7, " + Theme.highlight(String.valueOf(l.getBlockY())) + "&7, " + Theme.highlight(String.valueOf(l.getBlockZ())) + " &8| ");
                    Component btn = Utilities.translateColors(Theme.main("<bold>[TELEPORT]</bold>"))
                            .clickEvent(ClickEvent.runCommand("/minecraft:tp " + p.getName() + " " + l.getBlockX() + " " + l.getBlockY() + " " + l.getBlockZ()))
                            .hoverEvent(HoverEvent.showText(Theme.mainC("Click to teleport")));
                    message = message.append(btn);
                    p.sendMessage(message);
                }
                p.sendMessage(" ");
                p.sendMessage(Utilities.translateColors("&8<bold>»</bold> <color:#b3b3b3>Located</color> " + Theme.highlight(String.valueOf(filtered.size())) + " <color:#b3b3b3>blocks of</color> " + Theme.highlight(searchBlock.name())));
                p.sendMessage(Utilities.translateColors("&8<bold>»</bold> <color:#b3b3b3>Time Took:</color> " + Theme.highlight(timeEnlapsed())));
                p.sendMessage(Utilities.translateColors("<dark_gray><bold>&m━━━━━━━━━━━━|</bold></dark_gray> " +Theme.main("<bold>LocateBlock</bold>")+ " <dark_gray><bold>|&m━━━━━━━━━━━━</bold></dark_gray>"));
            } else {
                p.sendMessage(Theme.prefixed("Can't find any " + Theme.highlight(searchBlock.name()) + " in radius of " + Theme.highlight(radius.toString()) + " Time took: " + Theme.highlight(timeEnlapsed())));
            }
            p.sendActionBar(Utilities.translateColors(Theme.main("Search finished in ") + Theme.highlight(timeEnlapsed())));
            searchActive = false;
        });


    }

    public String timeEnlapsed() {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(timestamp, now);
        return duration.toHoursPart() + ":" + duration.toMinutesPart() + ":" + duration.toSecondsPart();
    }
    public List<Block> getNearbyBlocks(Location location, int radius) {
        List<Block> blocks = new ArrayList<>();
        for(int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
            if (!searchActive) break;
            for(int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
                if (!searchActive) break;
                for(int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
                    if (!searchActive) break;
                    blocks.add(location.getWorld().getBlockAt(x, y, z));
                }
            }
        }
        return blocks;
    }






}
