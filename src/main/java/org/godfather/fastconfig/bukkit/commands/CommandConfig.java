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
        registerSubCommand(new SubcommandCreate(this));
        registerSubCommand(new SubcommandReload(this));
        registerSubCommand(new SubcommandHelp(this));
        registerSubCommand(new SubcommandDelete(this));
        registerSubCommand(new SubcommandAbort(this));
        registerSubCommand(new SubcommandModify(this));
    }

    @Override
    protected boolean execute(CommandSender sender, String[] args) {
        if(getSubCommand("help").isEmpty())
            return false;

        getSubCommand("help").get().onCommand(sender, args);
        return true;
    }
}
