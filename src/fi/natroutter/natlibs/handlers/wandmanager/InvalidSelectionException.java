package fi.natroutter.natlibs.handlers.wandmanager;

public class InvalidSelectionException extends Exception {

    public InvalidSelectionException(Wand wand) {
        super(getMessage(wand));
    }

    private static String getMessage(Wand wand) {
        if (!wand.hasBothPos()) {
            if (!wand.hasPos1() && wand.hasPos2()) {
                return "Invalid Selection - Missing pos1";
            } else if (wand.hasPos1() && !wand.hasPos2()) {
                return "Invalid Selection - Missing pos2";
            } else {
                return "Invalid Selection - Missing both pos1 and pos2";
            }
        }
        return "Invalid Selection";
    }

}
