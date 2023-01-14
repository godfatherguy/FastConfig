package org.godfather.fastconfig.bukkit.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.godfather.fastconfig.common.FastConfigPlugin;
import org.godfather.fastconfig.common.command.Command;
import org.godfather.fastconfig.common.player.PluginPlayer;

import java.util.Collections;
import java.util.List;

public class CommandAbort extends Command {

    public CommandAbort(FastConfigPlugin plugin, String name) {
        super(plugin, name);
    }

    @Override
    protected boolean execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("fastconfig.abort") && !sender.isOp()) {
            sender.sendMessage("§cNon hai accesso a questo comando.");
            return false;
        }
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cThis command cannot be performed by console.");
            return false;
        }
        if (args.length != 0) {
            sender.sendMessage("§cUtilizza: /" + getName());
            return false;
        }
        if (plugin.getPlayerManager().getProfile(player).isEmpty()) {
            player.sendMessage("§cNon sei registrato nel server.");
            return false;
        }
        PluginPlayer plPlayer = plugin.getPlayerManager().getProfile(player).get();

        if (plPlayer.getClickThread().isEmpty()) {
            player.sendMessage("§cNon c'è nessun'azione da annullare.");
            return false;
        }

        plPlayer.removeThread();
        player.sendMessage("§aHai annullato l'azione in corso. Nessuna location impostata.");
        return true;
    }

    @Override
    protected List<String> tabComplete(CommandSender sender, String[] args) {
        return Collections.emptyList();
    }
}
