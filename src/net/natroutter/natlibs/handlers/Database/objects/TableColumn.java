package net.natroutter.natlibs.handlers.Database.objects;

import net.natroutter.natlibs.handlers.Database.enums.FieldType;

public class TableColumn {

    private FieldType type;
    private String name;

    public TableColumn(String name, FieldType type) {
        this.name = name;
        this.type = type;
    }

    public FieldType getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
