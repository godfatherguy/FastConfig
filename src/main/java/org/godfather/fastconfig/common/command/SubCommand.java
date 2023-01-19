package org.godfather.fastconfig.common.command;

import org.bukkit.command.CommandSender;

import java.util.List;

public interface SubCommand {

    String getName();

    boolean onCommand(CommandSender sender, String[] args);

    List<String> onTabComplete(CommandSender sender, String[] args);

    Command getCommand();
}
