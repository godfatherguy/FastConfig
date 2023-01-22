package org.godfather.fastconfig.bukkit.commands;

import org.bukkit.command.CommandSender;
import org.godfather.fastconfig.bukkit.commands.subcommands.*;
import org.godfather.fastconfig.common.FastConfigPlugin;
import org.godfather.fastconfig.common.command.Command;

public final class CommandConfig extends Command {

    public CommandConfig(FastConfigPlugin plugin, String name) {
        super(plugin, name);
    }

    @Override
    protected void registerSubCommands() {
        registerSubCommand(new SubcommandCreate(this, "create"));
        registerSubCommand(new SubcommandReload(this, "reload"));
        registerSubCommand(new SubcommandHelp(this, "help"));
        registerSubCommand(new SubcommandDelete(this, "delete"));
        registerSubCommand(new SubcommandAbort(this, "abort"));
        registerSubCommand(new SubcommandModify(this, "modify"));
    }

    @Override
    protected boolean execute(CommandSender sender, String[] args) {
        if(getSubCommand("help").isEmpty())
            return false;

        return getSubCommand("help").get().onCommand(sender, args);
    }
}
