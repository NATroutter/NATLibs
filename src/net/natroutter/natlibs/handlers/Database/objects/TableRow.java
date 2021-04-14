package net.natroutter.natlibs.handlers.Database.objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TableRow {
    private List<DataField> row = new ArrayList<>();
    public TableRow(DataField... data) {
        this.row.addAll(Arrays.asList(data));
    }
    public List<DataField> getFields() {
        return row;
    }
    public void add(DataField field) {
        this.row.add(field);
    }

    public String getText(String fieldName) {
        for (DataField field : row) {
            if (field.getName().equals(fieldName)) {
                return field.getData().toString();
            }
        }
        return null;
    }

    public Integer getInt(String fieldName) {
        for (DataField field : row) {
            if (field.getName().equals(fieldName)) {
                try {
                    return Integer.parseInt(field.getData().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public Boolean getBoolean(String fieldName) {
        for (DataField field : row) {
            if (field.getName().equals(fieldName)) {
                try {
                    return Boolean.parseBoolean(field.getData().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}