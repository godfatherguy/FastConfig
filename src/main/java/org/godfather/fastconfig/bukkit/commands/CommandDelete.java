package org.godfather.fastconfig.bukkit.commands;

import org.bukkit.command.CommandSender;
import org.godfather.fastconfig.common.FastConfigPlugin;
import org.godfather.fastconfig.common.command.Command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandDelete extends Command {

    public CommandDelete(FastConfigPlugin plugin, String name) {
        super(plugin, name);
    }

    @Override
    protected boolean execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("fastconfig.delete") && !sender.isOp()) {
            sender.sendMessage("§cNon hai accesso a questo comando.");
            return false;
        }
        if (args.length != 1) {
            sender.sendMessage("§cUtilizza: /" + getName() + " <nome config>");
            return false;
        }
        String configName = args[0];

        if (plugin.getConfigManager().getConfig(configName).isEmpty()) {
            sender.sendMessage("§cQuesto config non esiste.");
            return false;
        }
        plugin.getConfigManager().remove(configName);
        sender.sendMessage("§aConfig " + configName + ".yml eliminato dal database.");
        return true;
    }

    @Override
    protected List<String> tabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            return new ArrayList<>(plugin.getConfigManager().getConfigs().keySet());
        }
        return Collections.emptyList();
    }
}
