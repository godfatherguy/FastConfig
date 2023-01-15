package org.godfather.fastconfig.bukkit.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.godfather.fastconfig.common.FastConfigPlugin;
import org.godfather.fastconfig.common.command.Command;
import org.godfather.fastconfig.universal.Utils;
import org.godfather.fastconfig.universal.messages.MessageFlag;
import org.godfather.fastconfig.universal.messages.MessageType;

import java.util.Collections;
import java.util.List;

public class CommandHelp extends Command {

    public CommandHelp(FastConfigPlugin plugin, String name) {
        super(plugin, name);
    }

    @Override
    protected boolean execute(CommandSender sender, String[] args) {
        if(!sender.hasPermission("fastconfig.help") && !sender.isOp()) {
            sender.sendMessage("§cNon hai accesso a questo comando.");
            return false;
        }
        if(!(sender instanceof Player player)) {
            sender.sendMessage("§cThis command cannot be performed by console.");
            return false;
        }
        if(args.length != 0) {
            sender.sendMessage("§cUtilizza: /" + getName());
            return false;
        }

        player.sendMessage("");
        Utils.sendMessage(MessageType.CHAT, player, "§5§lFastConfig §8- §dHelp", MessageFlag.CENTERED);
        player.sendMessage("");
        player.sendMessage("§d/configcreate <nome> §8: §7Crea un config personalizzato.");
        player.sendMessage("§d/configdelete <nome> §8: §7Elimina un config personalizzato.");
        player.sendMessage("§d/configreload <nome> §8: §7Ricarica un config personalizzato.");
        player.sendMessage("§d/configmodify <nome> <path>... §8: §7Modifica i valori di un config.");
        player.sendMessage("§d/configabort §8: §7Annulla un'azione in corso di modifica.");
        player.sendMessage("");
        return true;
    }

    @Override
    protected List<String> tabComplete(CommandSender sender, String[] args) {
        return Collections.emptyList();
    }
}
