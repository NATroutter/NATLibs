package net.natroutter.natlibs.objects;

public class VersionData {
    private final boolean success;
    private final boolean update;
    private final String error;
    private final String version;
    private final String updateURL;

    public VersionData(boolean success,boolean update,String error,String version, String updateURL){
        this.success = success;
        this.update = update;
        this.error = error;
        this.version = version;
        this.updateURL = updateURL;
    }

    public String getVersion() {return version;}
    public String getError() {return error;}
    public boolean isSuccess() {return success;}
    public boolean hasUpdate() {return update;}
    public String getUpdateURL() {return updateURL;}
}
