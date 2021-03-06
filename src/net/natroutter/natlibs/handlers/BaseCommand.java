package net.natroutter.natlibs.handlers;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public abstract class BaseCommand extends Command {

	protected BaseCommand(String name) {
		super(name);
	}

	public abstract boolean executeCommand(CommandSender sender, String label, String[] args);

    @Override
    public boolean execute(CommandSender arg0, String arg1, String[] arg2) {
        return execute(arg0, arg1, arg2);
    }

}
