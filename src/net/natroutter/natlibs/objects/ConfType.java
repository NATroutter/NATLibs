package net.natroutter.natlibs.objects;

public enum ConfType {
    Config("Config.json"),
    Lang("Lang.json");

    private String file;
    ConfType(String file) { this.file = file; }
    public String getFile() { return file; }
}
