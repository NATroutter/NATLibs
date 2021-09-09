package net.natroutter.natlibs.handlers.hooking;

import net.natroutter.natlibs.utilities.StringHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.Plugin;

public class Hook {

    private Plugin plugin;
    private boolean Hooked = false;

    public Plugin getPlugin() {return plugin;}
    public boolean isHooked() {return Hooked;}

    protected Hook(String PluginName, Hooker hooker) {
        StringHandler successMessage = new StringHandler(hooker.getHookedMessage());
        StringHandler failedMessage = new StringHandler(hooker.getHookingFailedMessage());

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
        }
    }

}
