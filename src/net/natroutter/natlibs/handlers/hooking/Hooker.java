package net.natroutter.natlibs.handlers.hooking;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;

public class Hooker {

    private JavaPlugin pl;
    private String HookedMessage;
    private String HookingFailedMessage;

    ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();

    @Deprecated
    public Hooker() {}
    public Hooker(JavaPlugin pl) {
        this.pl = pl;
    }
    public Hooker(JavaPlugin pl, String HookedMessage, String HookingFailedMessage) {
        this.pl = pl;
        this.HookedMessage = HookedMessage;
        this.HookingFailedMessage = HookingFailedMessage;
    }

    /**
     * Set message what will be send when hooked succesfully
     * @param message message to send, you can use {plugin} to display plugin name!
     */
    public Hooker setHookedMessage(String message) {
        this.HookedMessage = message;
        return this;
    }

    /**
     * Set message what will be send when hooking fails
     * @param message message to send, you can use {plugin} to display plugin name!
     */
    public Hooker setHookingFailedMessage(String message) {
        this.HookingFailedMessage = message;
        return this;
    }

    protected String getHookedMessage() {
        return HookedMessage;
    }

    protected String getHookingFailedMessage() {
        return HookingFailedMessage;
    }

    /**
     * Create new plugin hooking
     * @param PluginName Plugin name what you want to hook into
     */
    public Hook create(String PluginName) {
        return new Hook(PluginName, this);
    }

    /**
     * Create new custom hook with custom class (class needs to extend HooK)
     * @param PluginName Plugin name what you want to hook into
     * @param clazz class waht you want to use for custom hook
     */
    public <T> T create(String PluginName, Class<T> clazz) {
        try {
            Class<?> extended = clazz.getSuperclass();
            if (!(extended.equals(Hook.class))) {

                String plName = "UNKNOWN_PLUGIN";
                if (pl != null) {plName = pl.getName();}

                console.sendMessage("§4["+plName+"][Hooker] §cERROR! - " + clazz.getName() + " does not extend Hook");
                return null;
            }
            return clazz.getDeclaredConstructor(clazz).newInstance(PluginName, this);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }
}
