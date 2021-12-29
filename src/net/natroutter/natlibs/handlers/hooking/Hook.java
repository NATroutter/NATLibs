package net.natroutter.natlibs.handlers.hooking;

import net.natroutter.natlibs.utilities.StringHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Hook {

    private JavaPlugin instance;
    private HookSettings settings;

    private Plugin plugin;
    private boolean Hooked = false;


    ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();

    /**
     * @return returns hooked plugin instance
     */
    public Plugin getPlugin() {return plugin;}

    /**
     * @return returns if wanted plugin is hooked or not!
     */
    public boolean isHooked() {return Hooked;}


    public Hook(JavaPlugin instance, HookSettings settings, String PluginName, boolean softDepend) {
        this.instance = instance;
        this.settings = settings;

        StringHandler successMessage = new StringHandler(settings.getHookedMessage());
        StringHandler failedMessage = new StringHandler(settings.getHookingFailedMessage());

        Plugin HookPL = Bukkit.getPluginManager().getPlugin(PluginName);
        ConsoleCommandSender console = Bukkit.getConsoleSender();
        if (HookPL != null && HookPL.isEnabled()) {
            this.plugin = HookPL;
            this.Hooked = true;
            successMessage.replaceAll("{plugin}", plugin.getName());
            console.sendMessage(successMessage.build());
        } else {
            failedMessage.replaceAll("{plugin}", PluginName);
            console.sendMessage(failedMessage.build());
            if (!softDepend) {
                disablePlugin();
            }
        }
    }

    protected void disablePlugin() {
        String plName = "UNKNOWN_PLUGIN";
        if (instance != null) {plName = instance.getName();}

        if (settings.isDisableWhenFailed()) {
            console.sendMessage("§4["+plName+"][Hook] " + settings.getDisabledMessage());
            Bukkit.getServer().getPluginManager().disablePlugin(instance);
        }
    }

}
