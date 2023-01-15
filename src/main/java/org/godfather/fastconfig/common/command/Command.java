package org.godfather.fastconfig.common.command;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.godfather.fastconfig.common.FastConfigPlugin;

import java.util.List;

public abstract class Command implements CommandExecutor, TabCompleter {

    protected final FastConfigPlugin plugin;
    private final String name;

    public Command(FastConfigPlugin plugin, String name) {
        this.plugin = plugin;
        this.name = name;
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String s, String[] args) {
        if (!cmd.getName().equalsIgnoreCase(name))
            return false;

        return execute(sender, args);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command cmd, String s, String[] args) {
        return tabComplete(sender, args);
    }

    protected abstract boolean execute(CommandSender sender, String[] args);

    protected abstract List<String> tabComplete(CommandSender sender, String[] args);

    public final String getName() {
        return name;
    }
}
