package fi.natroutter.natlibs.handlers;

import fi.natroutter.natlibs.utilities.Utilities;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Represents a plugin hook.
 */
public class Hook {

    /**
     * Builder class for creating a Hook instance.
     */
    public class Builder {
        private JavaPlugin instance;
        private String PluginName;
        private boolean isSoftDepend;

        private String hookedMessage = " §a+ §7Plugin hooked succesfully!";
        private String hookingFailedMessage = " §4- §7Plugin hooking failed!";
        private String disabledMessage = "§cDisabling plugin because failed to hook plugin!";
        private boolean disableWhenFailed;

        /**
         * Constructs a new Builder instance.
         * @param instance     the JavaPlugin instance
         * @param PluginName   the name of the plugin to hook
         * @param isSoftDepend whether it is a soft dependency
         */
        public Builder(JavaPlugin instance, String PluginName, boolean isSoftDepend) {
            this.instance = instance;
            this.PluginName = PluginName;
            this.isSoftDepend = isSoftDepend;
        }

        /**
         * Sets the message to display when the plugin is successfully hooked.
         * @param message the hooked message
         * @return the Builder instance
         */
        public Builder setHookedMessage(String message) {
            this.hookedMessage = message;
            return this;
        }

        /**
         * Sets the message to display when the plugin hooking fails.
         * @param message the hooking failed message
         * @return the Builder instance
         */
        public Builder setHookingFailedMessage(String message) {
            this.hookingFailedMessage = message;
            return this;
        }

        /**
         * Sets the message to display when the plugin is disabled due to hooking failure.
         * @param message the disable message
         * @return the Builder instance
         */
        public Builder setDisableMessage(String message) {
            this.disabledMessage = message;
            return this;
        }

        /**
         * Sets whether the plugin should be disabled when hooking fails.
         * @param value true to disable the plugin, false otherwise
         * @return the Builder instance
         */
        public Builder setDisableWhenFailed(boolean value) {
            this.disableWhenFailed = value;
            return this;
        }

    }


    @Getter private Plugin plugin;
    @Getter private boolean Hooked = false;

    private final ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();

    private Builder args;

    /**
     * Constructs a new Hook instance using the provided Builder.
     * @param builder the Builder instance
     */
    private Hook(Builder builder) {
        this.args = builder;

        Component success = Utilities.translateColors(args.hookedMessage,
                Placeholder.parsed("plugin", args.PluginName)
        );
        Component failed = Utilities.translateColors(args.hookingFailedMessage,
                Placeholder.parsed("plugin", args.PluginName)
        );

        Plugin HookPL = Bukkit.getPluginManager().getPlugin(args.PluginName);
        ConsoleCommandSender console = Bukkit.getConsoleSender();
        if (HookPL != null && HookPL.isEnabled()) {
            this.plugin = HookPL;
            this.Hooked = true;
            console.sendMessage(success);
        } else {
            console.sendMessage(failed);
            if (!args.isSoftDepend) {
                disablePlugin();
            }
        }
    }

    /**
     * Disables the plugin if hooking fails.
     */
    protected void disablePlugin() {
        String plName = "UNKNOWN_PLUGIN";
        if (args.instance != null) {plName = args.instance.getName();}

        if (args.disableWhenFailed) {
            console.sendMessage("§4["+plName+"][Hook] " + args.disabledMessage);
            Bukkit.getServer().getPluginManager().disablePlugin(args.instance);
        }
    }
}
