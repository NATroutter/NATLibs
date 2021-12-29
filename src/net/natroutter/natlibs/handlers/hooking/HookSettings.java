package net.natroutter.natlibs.handlers.hooking;

public class HookSettings {

    private String hookedMessage = " §a+ §7Plugin hooked succesfully!";
    private String hookingFailedMessage = " §4- §7Plugin hooking failed!";
    private String disabledMessage = "§cDisabling plugin because failed to hook plugin!";
    private boolean disableWhenFailed;

    /**
     * Set message what will be sent when hooked succesfully
     * @param message message to sent, you can use {plugin} to display plugin name!
     */
    public HookSettings setHookedMessage(String message) {
        this.hookedMessage = message;
        return this;
    }

    /**
     * Set message what will be sent when hooking fails
     * @param message message to sent, you can use {plugin} to display plugin name!
     */
    public HookSettings setHookingFailedMessage(String message) {
        this.hookingFailedMessage = message;
        return this;
    }

    /**
     * Set message what will be sent when hooking fails
     * @param message message to sent, you can use {plugin} to display plugin name!
     */
    public HookSettings setDisableMessage(String message) {
        this.disabledMessage = message;
        return this;
    }

    /**
     * Call this method if you want plugin to be disabled when one of hooked plugins fails to hook
     */
    public HookSettings disableWhenFailed() {
        disableWhenFailed = true;
        return this;
    }

    /**
     * @return get disable message
     */
    public String getDisabledMessage() {
        return disabledMessage;
    }

    /**
     * @return get hook success message
     */
    public String getHookedMessage() {
        return hookedMessage;
    }

    /**
     * @return get hook fail message
     */
    public String getHookingFailedMessage() {
        return hookingFailedMessage;
    }

    /**
     * @return check if plugin need to be disabled if hooking fails
     */
    public boolean isDisableWhenFailed() {
        return disableWhenFailed;
    }
}
