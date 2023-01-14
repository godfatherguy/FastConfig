package org.godfather.fastconfig.common;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.godfather.fastconfig.common.command.Command;
import org.godfather.fastconfig.common.config.ConfigManager;

import java.util.logging.Logger;

public abstract class FastConfigPlugin extends JavaPlugin {

    public static final Logger LOGGER = Bukkit.getLogger();
    protected ConfigManager configManager;

    @Override
    public final void onEnable() {
        saveDefaultConfig();
        configManager = new ConfigManager(this);
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

    public final void registerCommand(Command command) {
        getCommand(command.getName()).setExecutor(command);
        getCommand(command.getName()).setTabCompleter(command);
    }
}
