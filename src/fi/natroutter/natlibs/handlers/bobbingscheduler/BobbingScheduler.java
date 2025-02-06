package fi.natroutter.natlibs.handlers.bobbingscheduler;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class BobbingScheduler {

    private JavaPlugin plugin;
    private int taskID;
    private int delayedTaskID;

    private ConcurrentHashMap<UUID, BobbingTask> tasks = new ConcurrentHashMap<>();
    private ConcurrentHashMap<UUID, List<BobbingTask>> delayedTasks = new ConcurrentHashMap<>();

    public BobbingScheduler(JavaPlugin plugin) {
        this(plugin, 1L);
    }
    public BobbingScheduler(JavaPlugin plugin, long interval) {
        this.plugin = plugin;
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, ()-> {
            if (tasks.size() > 0) {
                tasks.forEach((id, task) -> task.tick());
            }
        },0L,interval);
        delayedTaskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, ()-> {
            if (delayedTasks.size() > 0) {
                delayedTasks.forEach((id, tasks) -> {
                    tasks.forEach(BobbingTask::tick);
                });
            }
        },0L,interval);
    }

    public UUID schedule(Consumer<Double> height, double amplitude, double frequency) {
        return schedule(height, amplitude, frequency, 0);
    }
    public UUID schedule(Consumer<Double> height, double amplitude, double frequency, long delay) {
        UUID id = UUID.randomUUID();
        schedule(new BobbingTask(height, amplitude, frequency, delay));
        return id;
    }
    public UUID schedule(BobbingTask task) {
        UUID id = UUID.randomUUID();
        tasks.put(id, task);
        return id;
    }

    public UUID scheduleDelayed(Map<?, BobbingTask> tasks, long delay) {
        return scheduleDelayed(tasks.values(), delay);
    }

    public UUID scheduleDelayed(Collection<BobbingTask> tasks, long delay) {
        UUID id = UUID.randomUUID();
        long offset = 0;

        List<BobbingTask> ntasks = new ArrayList<>();

        for (BobbingTask task : tasks) {
            ntasks.add(new BobbingTask(task.height, task.amplitude, task.frequency, offset));
            offset = offset + delay;
        }

        delayedTasks.put(id, ntasks);
        return id;
    }

    public void cancelTask(UUID id) {
        if (id == null) return;
        tasks.remove(id);
        delayedTasks.remove(id);
    }

    public void terminate() {
        Bukkit.getScheduler().cancelTask(taskID);
        Bukkit.getScheduler().cancelTask(delayedTaskID);
        tasks.clear();
        delayedTasks.clear();
    }

    public static void bob(Entity ent, double y) {
        ent.teleport(ent.getLocation().add(0,y,0));
    }
}