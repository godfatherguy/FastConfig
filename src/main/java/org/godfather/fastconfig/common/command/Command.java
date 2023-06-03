package org.godfather.fastconfig.common.command;

import com.google.common.collect.Lists;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.godfather.fastconfig.common.FastConfigPlugin;

import java.util.*;

public abstract class Command implements CommandExecutor, TabCompleter {

    protected final FastConfigPlugin plugin;
    private final String name;
    private final Map<String, SubCommand> subCommands = new HashMap<>();

    public Command(FastConfigPlugin plugin, String name) {
        this.plugin = plugin;
        this.name = name;
        registerSubCommands();
    }

    protected abstract void registerSubCommands();

    protected void registerSubCommand(SubCommand subCommand) {
        subCommands.put(subCommand.getName(), subCommand);
    }

    @Override
    public final boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String s, String[] args) {
        if (!cmd.getName().equalsIgnoreCase(name))
            return false;

        if (args.length == 0 || subCommands.isEmpty())
            return execute(sender, args);

        Optional<SubCommand> subCommand = Optional.ofNullable(subCommands.get(args[0]));
        if (subCommand.isEmpty()) {
            sender.sendMessage("§cUtilizza: /" + name + " help");
            return false;
        }

        String[] finalArgs = Arrays.copyOfRange(args, 1, args.length);
        return subCommand.get().onCommand(sender, finalArgs);
    }

    @Override
    public final List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command cmd, String s, String[] args) {
        List<String> completions = Lists.newArrayList();
        if (args.length == 1) {
            for (String name : subCommands.keySet()) {
                completions.add(name.toLowerCase());
            }
            Collections.sort(completions);
            completions.removeIf(str -> !str.toLowerCase().startsWith(args[0].toLowerCase()));
            return completions;
        }
        Optional<SubCommand> subCommand = Optional.ofNullable(subCommands.get(args[0]));

        if (subCommand.isEmpty())
            return Collections.emptyList();

        String[] finalArgs = Arrays.copyOfRange(args, 1, args.length);

        List<String> subCompletions = subCommand.get().onTabComplete(sender, finalArgs);
        for (int i = 1; i < args.length; i++) {
            int finalI = i;
            subCompletions.removeIf(str -> !str.toLowerCase().startsWith(args[finalI].toLowerCase()));
        }
        return subCommand.get().onTabComplete(sender, finalArgs);
    }

    protected abstract boolean execute(CommandSender sender, String[] args);

    public String getName() {
        return name;
    }

    public Optional<SubCommand> getSubCommand(String name) {
        return Optional.ofNullable(subCommands.get(name));
    }

    public FastConfigPlugin getPlugin() {
        return plugin;
    }
}
