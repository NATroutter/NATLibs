package net.natroutter.natlibs.handlers.Database.objects;

import net.natroutter.natlibs.handlers.Database.enums.FieldType;

public class InsertField {

    private FieldType type;
    private Object data;

    public InsertField(Object data, FieldType type) {
        this.data = data;
        this.type = type;
    }

    public FieldType getType() {
        return type;
    }

    public Object getData() {
        return data;
    }
}