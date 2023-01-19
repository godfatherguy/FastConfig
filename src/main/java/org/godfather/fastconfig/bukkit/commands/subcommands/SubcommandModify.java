package org.godfather.fastconfig.bukkit.commands.subcommands;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.godfather.fastconfig.common.command.AbstractSubCommand;
import org.godfather.fastconfig.common.command.Command;
import org.godfather.fastconfig.common.config.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SubcommandModify extends AbstractSubCommand {

    public SubcommandModify(Command command, String name) {
        super(command, name);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (!sender.hasPermission("fastconfig.modify") && !sender.isOp()) {
            sender.sendMessage("§cNon hai accesso a questo comando.");
            return false;
        }
        if (args.length < 3) {
            sender.sendMessage("§cUtilizza: /" + getName() + " <nome config> <path> <value/LOCATION>");
            return false;
        }
        String configName = args[0];

        if (command.getPlugin().getConfigManager().getConfig(configName).isEmpty()) {
            sender.sendMessage("§cQuesto config non esiste.");
            return false;
        }
        Config config = command.getPlugin().getConfigManager().getConfig(configName).get();
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

                if (command.getPlugin().getPlayerManager().getProfile(player).isEmpty() ||
                        command.getPlugin().getPlayerManager().getProfile(player).get().getClickThread().isPresent()) {
                    player.sendMessage("§cDevi ancora cliccare un blocco! (/configabort per annullare)");
                    return false;
                }
                command.getPlugin().getPlayerManager().getProfile(player).get().putThread(config, path, new Location(player.getWorld(), 0, 0, 0, yaw, pitch));
                player.sendMessage("§eClicca su un blocco qualsiasi per impostare la sua location. (Digita /configabort per annullare l'operazione)");
                return true;
            }

            config.getConfig().set(path + ".x", getAdjusted(player.getLocation().getX()));
            config.getConfig().set(path + ".y", getAdjusted(player.getLocation().getY()));
            config.getConfig().set(path + ".z", getAdjusted(player.getLocation().getZ()));
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
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            return new ArrayList<>(command.getPlugin().getConfigManager().getConfigs().keySet());
        } else if (args.length == 3) {
            return Arrays.stream(new String[]{"location"}).toList();
        } else if (args.length > 3 && args[2].equalsIgnoreCase("location")) {
            List<String> completions = new ArrayList<>();
            completions.add("-adjustYaw");
            completions.add("-adjustPitch");
            completions.add("-onClick");
            for (int i = 3; i < args.length; i++) {
                if (args[i].equalsIgnoreCase("-adjustYaw"))
                    completions.remove("-adjustYaw");
                else if (args[i].equalsIgnoreCase("-adjustPitch"))
                    completions.remove("-adjustPitch");
                else if (args[i].equalsIgnoreCase("-onClick"))
                    completions.remove("-onClick");
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

    private double getAdjusted(double coordinate) {
        double rounded = Math.round(coordinate * 10.0) / 10.0;
        int intPart = Integer.parseInt(String.valueOf(rounded).split("\\.")[0]);
        double decimalPart = Integer.parseInt(String.valueOf(rounded).split("\\.")[1]) * 0.1;

        if (decimalPart >= 0 && decimalPart <= 0.2)
            return intPart;

        else if (decimalPart > 0.2 && decimalPart < 0.8)
            if (intPart < 0)
                return intPart - 0.5;
            else return intPart + 0.5;

        else if (decimalPart >= 0.8)
            if (intPart < 0)
                return intPart - 1;
            else return intPart + 1;

        else if (intPart < 0)
            return intPart - 0.5;
        else return intPart + 0.5;
    }
}
