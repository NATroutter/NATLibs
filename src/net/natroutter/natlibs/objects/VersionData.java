package net.natroutter.natlibs.objects;

public class VersionData {
    private boolean success;
    private boolean update;
    private String error;
    private String version;
    private String updateURL;

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
