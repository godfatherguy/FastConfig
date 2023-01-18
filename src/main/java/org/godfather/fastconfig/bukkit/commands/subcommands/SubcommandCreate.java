package org.godfather.fastconfig.bukkit.commands.subcommands;

import org.bukkit.command.CommandSender;
import org.godfather.fastconfig.common.command.Command;
import org.godfather.fastconfig.common.command.SubCommand;

import java.util.Collections;
import java.util.List;

public record SubcommandCreate(Command command) implements SubCommand {

    @Override
    public String getName() {
        return "create";
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (!sender.hasPermission("fastconfig.create") && !sender.isOp()) {
            sender.sendMessage("§cNon hai accesso a questo comando.");
            return false;
        }
        if (args.length != 1) {
            sender.sendMessage("§cUtilizza: /" + command().getName() + " " + getName() + " <nome config>");
            return false;
        }
        String configName = args[0];

        if (command.getPlugin().getConfigManager().getConfig(configName).isPresent()) {
            sender.sendMessage("§cQuesto config esiste già.");
            return false;
        }
        command.getPlugin().getConfigManager().create(configName);
        sender.sendMessage("§aConfig " + configName + ".yml creato con successo.");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return Collections.emptyList();
    }
}
