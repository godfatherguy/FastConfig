package org.godfather.fastconfig.bukkit.commands;

import org.bukkit.command.CommandSender;
import org.godfather.fastconfig.common.FastConfigPlugin;
import org.godfather.fastconfig.common.command.Command;

import java.util.Collections;
import java.util.List;

public class CommandCreate extends Command {

    public CommandCreate(FastConfigPlugin plugin, String name) {
        super(plugin, name);
    }

    @Override
    protected boolean execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("fastconfig.create") && !sender.isOp()) {
            sender.sendMessage("§cNon hai accesso a questo comando.");
            return false;
        }
        if (args.length != 1) {
            sender.sendMessage("§cUtilizza: /" + getName() + " <nome config>");
            return false;
        }
        String configName = args[0];

        if (plugin.getConfigManager().getConfig(configName).isPresent()) {
            sender.sendMessage("§cQuesto config esiste già.");
            return false;
        }
        plugin.getConfigManager().create(configName);
        sender.sendMessage("§aConfig " + configName + ".yml creato con successo.");
        return true;
    }

    @Override
    protected List<String> tabComplete(CommandSender sender, String[] args) {
        return Collections.emptyList();
    }
}
