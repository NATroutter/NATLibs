package fi.natroutter.natlibs.utilities;

import fi.natroutter.natlibs.objects.PushDirection;
import org.bukkit.entity.Entity;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;

import java.util.Random;

public class Push implements Listener {


    public static PushDirection randomDirection(Boolean isUpPossible) {
        Random r = new Random();

        if (isUpPossible) {
            int Low = 1;
            int High = 5;
            int Result = r.nextInt(High-Low) + Low;

            return switch (Result) {
                case 1 -> PushDirection.Y_PLUS;
                case 2 -> PushDirection.X_PLUS;
                case 3 -> PushDirection.X_MINUS;
                case 4 -> PushDirection.Z_PLUS;
                default -> PushDirection.Z_MINUS;
            };
        } else {
            int Low = 1;
            int High = 4;
            int Result = r.nextInt(High-Low) + Low;

            return switch (Result) {
                case 1 -> PushDirection.X_PLUS;
                case 2 -> PushDirection.X_MINUS;
                case 3 -> PushDirection.Z_PLUS;
                default -> PushDirection.Z_MINUS;
            };
        }

    }

    private static Boolean randomBool() {
        Random random = new Random();
        return random.nextInt(2) == 0;
    }

    public static void Throw(Entity ent, int Speed, Double UpSpeed) {

        Random r = new Random();
        int Low = -1;
        int High = 1;
        int Result = r.nextInt(High-Low) + Low;
        int X = 0;
        int Z = 0;

        X = Result;
        if (X == 0) {
            if (randomBool()) {
                Z = 1;
            } else {
                Z = -1;
            }
        }

        Vector v = new Vector (X, UpSpeed, Z).normalize ().multiply (Speed);
        ent.setVelocity(v);

    }

    public static void push(Entity ent, PushDirection direction, double speed) {

        if (direction.equals(PushDirection.Y_PLUS)) {

            Vector center = ent.getLocation().toVector();
            Vector toThrow = ent.getLocation().toVector();
            double x = toThrow.getX() - center.getX();
            double z = toThrow.getZ() - center.getZ();
            Vector v = new Vector (x, 1, z).normalize ().multiply (speed);
            ent.setVelocity(v);

        } else if (direction.equals(PushDirection.Y_MINUS)) {

            Vector center = ent.getLocation().toVector();
            Vector toThrow = ent.getLocation().toVector();
            double x = toThrow.getX() - center.getX();
            double z = toThrow.getZ() - center.getZ();
            Vector v = new Vector (x, -1, z).normalize ().multiply (speed);
            ent.setVelocity(v);

        } else if (direction.equals(PushDirection.X_PLUS)) {

            Vector center = ent.getLocation().toVector();
            Vector toThrow = ent.getLocation().toVector();

            double y = toThrow.getY() - center.getY();
            double z = toThrow.getZ() - center.getZ();

            Vector v = new Vector (1, y, z).normalize ().multiply (speed);
            ent.setVelocity(v);

        } else if (direction.equals(PushDirection.X_MINUS)) {

            Vector center = ent.getLocation().toVector();
            Vector toThrow = ent.getLocation().toVector();

            double y = toThrow.getY() - center.getY();
            double z = toThrow.getZ() - center.getZ();

            Vector v = new Vector (-1, y, z).normalize ().multiply (speed);
            ent.setVelocity(v);

        } else if (direction.equals(PushDirection.Z_PLUS)) {

            Vector center = ent.getLocation().toVector();
            Vector toThrow = ent.getLocation().toVector();

            double x = toThrow.getX() - center.getX();
            double y = toThrow.getY() - center.getY();

            Vector v = new Vector (x, y, 1).normalize ().multiply (speed);
            ent.setVelocity(v);

        } else if (direction.equals(PushDirection.Z_MINUS)) {

            Vector center = ent.getLocation().toVector();
            Vector toThrow = ent.getLocation().toVector();

            double x = toThrow.getX() - center.getX();
            double y = toThrow.getY() - center.getY();

            Vector v = new Vector (x, y, -1).normalize ().multiply (speed);
            ent.setVelocity(v);

        }

    }

}
