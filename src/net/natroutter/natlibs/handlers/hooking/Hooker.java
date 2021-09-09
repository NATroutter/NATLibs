package net.natroutter.natlibs.handlers.hooking;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;

public class Hooker {

    private JavaPlugin pl;
    private String hookedMessage = " §a+ §7Plugin hooked succesfully!";
    private String hookingFailedMessage = " §4- §7Plugin hooking failed!";
    private String disabledMessage = "§7Disabling plugin because failed to hook plugin!";
    private boolean disableWhenFailed;
    private boolean hookFail = false;

    ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();

    @Deprecated
    public Hooker() {}

    /**
     * Set message what will be send when hooked succesfully
     * @param pl Plugin instance!
     */
    public Hooker(JavaPlugin pl) {
        this.pl = pl;
    }

    /**
     * Set message what will be send when hooked succesfully
     * @param pl Plugin instance!
     * @param disableWhenFailed Determine do you want to disable plugin when hooking fails!
     */
    public Hooker(JavaPlugin pl, boolean disableWhenFailed) {
        this.pl = pl;
        this.disableWhenFailed = disableWhenFailed;
    }

    /**
     * Set message what will be send when hooked succesfully
     * @param pl Plugin instance!
     * @param disableWhenFailed Determine do you want to disable plugin when hooking fails!
     * @param HookedMessage Message that will be sent when plugin is hooked successfully!
     * @param HookingFailedMessage Message that will be sent when plugin hooking fails!
     * @param disabledMessage Message that will be sent when plugin is being disabled!
     */
    public Hooker(JavaPlugin pl, boolean disableWhenFailed, String HookedMessage, String HookingFailedMessage, String disabledMessage) {
        this.pl = pl;
        this.disableWhenFailed = disableWhenFailed;
        this.hookedMessage = HookedMessage;
        this.hookingFailedMessage = HookingFailedMessage;
        this.disabledMessage = disabledMessage;
    }

    /**
     * Set message what will be sent when hooked succesfully
     * @param message message to sent, you can use {plugin} to display plugin name!
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
     * Set message what will be sent when hooking fails
     * @param message message to sent, you can use {plugin} to display plugin name!
     */
    public Hooker setHookingFailedMessage(String message) {
        this.hookingFailedMessage = message;
        return this;
    }

    /**
     * Set message what will be sent when hooking fails
     * @param message message to sent, you can use {plugin} to display plugin name!
     */
    public Hooker setDisableMessage(String message) {
        this.disabledMessage = message;
        return this;
    }

    /**
     * Create new plugin hooking
     * @param PluginName Plugin name what you want to hook into
     */
    public Hook create(String PluginName) {
        return create(PluginName, false);
    }
    /**
     * Create new plugin hooking
     * @param PluginName Plugin name what you want to hook into
     * @param softDepend determine if plugin is softdepended or not!
     */
    public Hook create(String PluginName, boolean softDepend) {

        if (hookFail) {return null;}
        Hook hook = new Hook(PluginName, this);

        if (!hook.isHooked() && !softDepend) {
            disablePlugin();
            return null;
        }

        return hook;
    }

    /**
     * Create new custom hook with custom class (class needs to extend HooK)
     * @param PluginName Plugin name what you want to hook into!
     * @param clazz class waht you want to use for custom hook!
     */
    public <T> T create(String PluginName, Class<T> clazz) {
        return create(PluginName, clazz, false);
    }

    /**
     * Create new custom hook with custom class (class needs to extend HooK)
     * @param PluginName Plugin name what you want to hook into!
     * @param clazz class waht you want to use for custom hook!
     * @param softDepend determine if plugin is softdepended or not!
     */
    public <T> T create(String PluginName, Class<T> clazz, boolean softDepend) {
        if (hookFail) {return null;}
        try {
            T obj = clazz.getDeclaredConstructor(clazz).newInstance(PluginName, this);

            Class<?> extended = clazz.getSuperclass();
            if (!(extended.equals(Hook.class))) {
                String plName = "UNKNOWN_PLUGIN";
                if (pl != null) {plName = pl.getName();}

                console.sendMessage("§4["+plName+"][Hooker] §cERROR! - " + clazz.getName() + " does not extend Hook");
                if (!softDepend) {hookFail = true;}
                return null;
            }
            Hook hook = (Hook)obj;

            if (!hook.isHooked() && !softDepend) {
                disablePlugin();
                return null;
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

    protected void disablePlugin() {
        String plName = "UNKNOWN_PLUGIN";
        if (pl != null) {plName = pl.getName();}

        if (this.disableWhenFailed) {
            console.sendMessage("§4["+plName+"][Hooker] " + disabledMessage);
            Bukkit.getServer().getPluginManager().disablePlugin(pl);
            hookFail = true;
        }
    }
}
