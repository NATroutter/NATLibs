package net.natroutter.natlibs.objects;

public class CondCommand {

    Class<?> clazz;
    Boolean state;

    public CondCommand(Class<?> clazz, Boolean state) {
        this.clazz = clazz;
        this.state = state;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public Boolean getState() {
        return state;
    }
}
