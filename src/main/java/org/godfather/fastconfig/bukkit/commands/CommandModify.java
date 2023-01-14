package org.godfather.fastconfig.bukkit.commands;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.godfather.fastconfig.common.FastConfigPlugin;
import org.godfather.fastconfig.common.command.Command;
import org.godfather.fastconfig.common.config.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CommandModify extends Command {

    public CommandModify(FastConfigPlugin plugin, String name) {
        super(plugin, name);
    }

    @Override
    protected boolean execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("fastconfig.modify") && !sender.isOp()) {
            sender.sendMessage("§cNon hai accesso a questo comando.");
            return false;
        }
        if (args.length < 3) {
            sender.sendMessage("§cUtilizza: /" + getName() + " <nome config> <path> <value/LOCATION>");
            return false;
        }
        String configName = args[0];

        if (plugin.getConfigManager().getConfig(configName).isEmpty()) {
            sender.sendMessage("§cQuesto config non esiste.");
            return false;
        }
        Config config = plugin.getConfigManager().getConfig(configName).get();
        String path = args[1];

        if (args[2].equalsIgnoreCase("location")) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage("§cThis command cannot be performed by console.");
                return false;
            }

            boolean adjustYaw = false;
            boolean adjustPitch = false;
            boolean onClick = false;
            for (int i = 3; i < args.length; i++) {
                if (args[i].equalsIgnoreCase("-adjustYaw"))
                    adjustYaw = true;
                else if (args[i].equalsIgnoreCase("-adjustPitch"))
                    adjustPitch = true;
                else if (args[i].equalsIgnoreCase("-onClick"))
                    onClick = true;
            }
            float yaw = adjustYaw ? getNearestYaw(player.getLocation().getYaw()) : player.getLocation().getYaw();
            float pitch = adjustPitch ? 0.0f : player.getLocation().getPitch();

            if (onClick) {
                if (plugin.getPlayerManager().getProfile(player).isEmpty() || plugin.getPlayerManager().getProfile(player).get().getClickThread().isPresent()) {
                    player.sendMessage("§cDevi ancora cliccare un blocco! (/configabort per annullare)");
                    return false;
                }
                plugin.getPlayerManager().getProfile(player).get().putThread(config, path, new Location(player.getWorld(), 0, 0, 0, yaw, pitch));
                player.sendMessage("§eClicca su un blocco qualsiasi per impostare la sua location. (Digita /configabort per annullare l'operazione)");
                return true;
            }

            config.getConfig().set(path + ".x", Math.round(player.getLocation().getX() * 10.0) / 10.0);
            config.getConfig().set(path + ".y", Math.round(player.getLocation().getY() * 10.0) / 10.0);
            config.getConfig().set(path + ".z", Math.round(player.getLocation().getZ() * 10.0) / 10.0);
            config.getConfig().set(path + ".yaw", yaw);
            config.getConfig().set(path + ".pitch", pitch);
            config.save();
            player.sendMessage("§aHai impostato con successo la location.");
            return true;
        }
        config.getConfig().set(path, args[2]);
        sender.sendMessage("§aHai impostato con successo il valore.");
        return true;
    }

    @Override
    protected List<String> tabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            return new ArrayList<>(plugin.getConfigManager().getConfigs().keySet());
        } else if (args.length == 3) {
            return Arrays.stream(new String[]{"location"}).toList();
        } else if (args.length > 3 && args[2].equalsIgnoreCase("location")) {
            List<String> completions = new ArrayList<>();
            completions.add("-adjustYaw");
            completions.add("-adjustPitch");
            for (int i = 3; i < args.length; i++) {
                if (args[i].equalsIgnoreCase("-adjustYaw"))
                    completions.remove("-adjustYaw");
                else if (args[i].equalsIgnoreCase("-adjustPitch"))
                    completions.remove("-adjustPitch");
            }
            return completions;
        }
        return Collections.emptyList();
    }

    private float getNearestYaw(float yaw) {
        if (yaw >= -22.5 && yaw < 22.5) {
            return 0.0f;
        } else if (yaw >= 22.5 && yaw < 67.5) {
            return 45.0f;
        } else if (yaw >= 67.5 && yaw < 112.5) {
            return 90.0f;
        } else if (yaw >= 112.5 && yaw < 157.5) {
            return 135.0f;
        } else if (yaw >= 157.5 || yaw < -157.5) {
            return 180.0f;
        } else if (yaw >= -157.5 && yaw < -112.5) {
            return -135.0f;
        } else if (yaw >= -112.5 && yaw < -67.5) {
            return -90.0f;
        } else return -45.0f;
    }
}
