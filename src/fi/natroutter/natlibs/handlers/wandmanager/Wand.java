package fi.natroutter.natlibs.handlers.wandmanager;

import fi.natroutter.natlibs.objects.BaseItem;
import fi.natroutter.natlibs.objects.Cuboid;
import fi.natroutter.natlibs.objects.ParticleSettings;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.util.BoundingBox;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Wand {

    @Getter @AllArgsConstructor @NoArgsConstructor
    public static class Builder {
        private UUID UUID = java.util.UUID.randomUUID();
        private BaseItem item = new BaseItem(Material.STICK).name("<bold><gradient:#FB1A13:#FDB515>SelectionWand</gradient>");
        private Location pos1 = null;
        private Location pos2 = null;
        private boolean useVisualizer = true;
        private int visualizerRenderDistance = 15;
        private String visualizerPermission = null;
        private ParticleSettings particle = new ParticleSettings(Particle.REDSTONE, 1, 0, 0, 0, 0, new Particle.DustOptions(org.bukkit.Color.RED, 1f));

        public Builder setItem(BaseItem item) {
            this.item = item;
            return this;
        }

        public Builder setPos1(Location pos1) {
            this.pos1 = pos1;
            return this;
        }

        public Builder setPos2(Location pos2) {
            this.pos2 = pos2;
            return this;
        }

        public Builder setUseVisualizer(boolean useVisualizer) {
            this.useVisualizer = useVisualizer;
            return this;
        }

        public Builder setVisualizerRenderDistance(int visualizerRenderDistance) {
            this.visualizerRenderDistance = visualizerRenderDistance;
            return this;
        }

        public Builder setVisualizerPermission(String visualizerPermission) {
            this.visualizerPermission = visualizerPermission;
            return this;
        }

        public Builder setParticle(ParticleSettings particle) {
            this.particle = particle;
            return this;
        }

        public Wand build() {
            return new Wand(this);
        }
    }




    protected Builder args;

    private Wand(Builder builder) {
        this.args = builder;
    }

    public UUID getUUID() {return args.UUID;}
    public BaseItem getItem() {return args.item;}
    public Location getPos1() {return args.pos1;}
    public Location getPos2() {return args.pos2;}

    public void deselect() {
        args.pos1 = null;
        args.pos2 = null;
    }

    public boolean hasPos1() {
        return args.pos1 != null;
    }

    public boolean hasPos2() {
        return args.pos2 != null;
    }

    public boolean hasBothPos() {
        return hasPos1() && hasPos2();
    }

    public Cuboid asCuboid() throws InvalidSelectionException {
        if (!hasBothPos()) {
            throw new InvalidSelectionException(this);
        }
        return new Cuboid(args.pos1, args.pos2);
    }

    public BoundingBox asBoundingBox() throws InvalidSelectionException {
        if (!hasBothPos()) {
            throw new InvalidSelectionException(this);
        }
        return new BoundingBox(args.pos1.getX(), args.pos1.getY(), args.pos1.getZ(), args.pos2.getX(), args.pos2.getY(), args.pos2.getZ());
    }

    public List<Location> asHollowCube() {
        List<Location> result = new ArrayList<>();
        World world = args.pos1.getWorld();
        double minX = Math.min(args.pos1.getX(), args.pos2.getX());
        double minY = Math.min(args.pos1.getY(), args.pos2.getY());
        double minZ = Math.min(args.pos1.getZ(), args.pos2.getZ());
        double maxX = Math.max(args.pos1.getX(), args.pos2.getX());
        double maxY = Math.max(args.pos1.getY(), args.pos2.getY());
        double maxZ = Math.max(args.pos1.getZ(), args.pos2.getZ());

        for (double x = minX; x <= maxX; x+=0.2D) {
            for (double y = minY; y <= maxY; y+=0.2D) {
                result.add(new Location(world, x, y, minZ));
                result.add(new Location(world, x, y, maxZ));
            }
        }

        for (double z = minZ; z <= maxZ; z+=0.2D) {
            for (double y = minY; y <= maxY; y+=0.2D) {
                result.add(new Location(world, minX, y, z));
                result.add(new Location(world, maxX, y, z));
            }
        }
        return result;
    }

}
