package org.godfather.fastconfig.bukkit.commands.subcommands;

import org.bukkit.command.CommandSender;
import org.godfather.fastconfig.common.command.Command;
import org.godfather.fastconfig.common.command.SubCommand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public record SubcommandReload(Command command) implements SubCommand {

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (!sender.hasPermission("fastconfig.reload") && !sender.isOp()) {
            sender.sendMessage("§cNon hai accesso a questo comando.");
            return false;
        }
        if (args.length != 1) {
            sender.sendMessage("§cUtilizza: /" + command().getName() + " " + getName() + " <nome config>");
            return false;
        }
        String configName = args[0];

        if (command.getPlugin().getConfigManager().getConfig(configName).isEmpty()) {
            sender.sendMessage("§cQuesto config non esiste.");
            return false;
        }
        command.getPlugin().getConfigManager().getConfig(configName).get().reload();
        sender.sendMessage("§aConfig " + configName + ".yml aggiornato.");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            return new ArrayList<>(command.getPlugin().getConfigManager().getConfigs().keySet());
        }
        return Collections.emptyList();
    }
}
