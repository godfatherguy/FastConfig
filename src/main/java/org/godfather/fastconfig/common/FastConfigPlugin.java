package org.godfather.fastconfig.common;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.godfather.fastconfig.common.command.Command;
import org.godfather.fastconfig.common.config.ConfigManager;
import org.godfather.fastconfig.common.player.PlayerManager;

import java.util.logging.Logger;

public abstract class FastConfigPlugin extends JavaPlugin {

    public static final Logger LOGGER = Bukkit.getLogger();
    protected ConfigManager configManager;
    private PlayerManager playerManager;

    @Override
    public final void onEnable() {
        saveDefaultConfig();
        configManager = new ConfigManager(this);
        playerManager = new PlayerManager();
        enable();
    }

    @Override
    public final void onDisable() {
        disable();
    }

    protected abstract void enable();

    protected abstract void disable();

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public final void registerCommand(Command command) {
        getCommand(command.getName()).setExecutor(command);
        getCommand(command.getName()).setTabCompleter(command);
    }
}
