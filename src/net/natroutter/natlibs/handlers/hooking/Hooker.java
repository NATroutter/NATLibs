package net.natroutter.natlibs.handlers.hooking;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;

public class Hooker {

    private JavaPlugin pl;
    private String hookedMessage;
    private String hookingFailedMessage;
    private String disabledMessage;
    private boolean disableWhenFailed;

    ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();

    @Deprecated
    public Hooker() {}
    public Hooker(JavaPlugin pl) {
        this.pl = pl;
    }
    public Hooker(JavaPlugin pl, boolean disableWhenFailed) {
        this.pl = pl;
        this.disableWhenFailed = disableWhenFailed;
    }
    public Hooker(JavaPlugin pl, boolean disableWhenFailed, String HookedMessage, String HookingFailedMessage, String disabledMessage) {
        this.pl = pl;
        this.disableWhenFailed = disableWhenFailed;
        this.hookedMessage = HookedMessage;
        this.hookingFailedMessage = HookingFailedMessage;
        this.disabledMessage = disabledMessage;
    }

    /**
     * Set message what will be send when hooked succesfully
     * @param message message to send, you can use {plugin} to display plugin name!
     */
    public Hooker setHookedMessage(String message) {
        this.hookedMessage = message;
        return this;
    }

    /**
     * Call this method if you want plugin to be disabled when one of hooked plugins fails to hook
     */
    public Hooker disableWhenFailed() {
        disableWhenFailed = true;
        return this;
    }

    /**
     * Set message what will be send when hooking fails
     * @param message message to send, you can use {plugin} to display plugin name!
     */
    public Hooker setHookingFailedMessage(String message) {
        this.hookingFailedMessage = message;
        return this;
    }

    /**
     * Create new plugin hooking
     * @param PluginName Plugin name what you want to hook into
     */
    public Hook create(String PluginName) {
        Hook hook = new Hook(PluginName, this);
        if (!hook.isHooked()) {
            if (this.disableWhenFailed) {
                console.sendMessage(getDisabledMessage());
                Bukkit.getServer().getPluginManager().disablePlugin(pl);
                return null;
            }
        }
        return hook;
    }

    /**
     * Create new custom hook with custom class (class needs to extend HooK)
     * @param PluginName Plugin name what you want to hook into
     * @param clazz class waht you want to use for custom hook
     */
    public <T> T create(String PluginName, Class<T> clazz) {
        try {
            T obj = clazz.getDeclaredConstructor(clazz).newInstance(PluginName, this);

            Class<?> extended = clazz.getSuperclass();
            if (!(extended.equals(Hook.class))) {
                String plName = "UNKNOWN_PLUGIN";
                if (pl != null) {plName = pl.getName();}

                console.sendMessage("§4["+plName+"][Hooker] §cERROR! - " + clazz.getName() + " does not extend Hook");
                return null;
            }
            Hook hook = (Hook)obj;

            if (!hook.isHooked()) {
                if (this.disableWhenFailed) {
                    console.sendMessage(getDisabledMessage());
                    Bukkit.getServer().getPluginManager().disablePlugin(pl);
                    return null;
                }
            }

            return clazz.getDeclaredConstructor(clazz).newInstance(PluginName, this);

        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }



    protected String getHookedMessage() {
        return hookedMessage;
    }

    protected String getHookingFailedMessage() {
        return hookingFailedMessage;
    }

    protected String getDisabledMessage() {
        String plName = "UNKNOWN_PLUGIN";
        if (pl != null) {plName = pl.getName();}
        return "§4["+plName+"][Hooker] " + disabledMessage;
    }
}
