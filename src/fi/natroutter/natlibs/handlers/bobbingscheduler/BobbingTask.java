package fi.natroutter.natlibs.handlers.bobbingscheduler;

import org.bukkit.entity.Entity;

import java.util.function.Consumer;

public class BobbingTask {

    private long currentTick = 0;
    private double lastYOffset = 0;

    protected Consumer<Double> height;
    protected double amplitude;
    protected double frequency;

    public BobbingTask(Entity entity, double amplitude, double frequency) {
        this.height = y -> {
            if (entity != null && !entity.isDead()) {
                entity.teleport(entity.getLocation().add(0,y,0));
            }
        };
        this.amplitude = amplitude;
        this.frequency = frequency;
    }

    public BobbingTask(Consumer<Double> height, double amplitude, double frequency) {
        this.height = height;
        this.amplitude = amplitude;
        this.frequency = frequency;
    }

    protected BobbingTask(Consumer<Double> height, double amplitude, double frequency, long startingTicks) {
        this.height = height;
        this.amplitude = amplitude;
        this.frequency = frequency;
        this.currentTick = startingTicks;
    }

    public void tick() {
        if (currentTick == (Long.MAX_VALUE - 1)) {
            currentTick = 0;
        }
        double yOffset = Math.sin(Math.toRadians(currentTick * frequency)) * amplitude;
        height.accept((yOffset - lastYOffset));
        lastYOffset = yOffset;
        currentTick++;
    }

}
