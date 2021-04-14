package net.natroutter.natlibs.handlers.Database.objects;

import net.natroutter.natlibs.handlers.Database.enums.FieldType;

public class DataField {

    private String name;
    private FieldType type;
    private Object data;

    public DataField(Object data, FieldType type) {
        this.name = null;
        this.data = data;
        this.type = type;
    }

    public DataField(String name, Object data, FieldType type) {
        this.name = name;
        this.data = data;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public FieldType getType() {
        return type;
    }

    public Object getData() {
        return data;
    }
}
