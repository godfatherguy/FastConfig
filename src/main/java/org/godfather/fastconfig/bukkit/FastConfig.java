package org.godfather.fastconfig.bukkit;

import org.godfather.fastconfig.bukkit.commands.CommandConfig;
import org.godfather.fastconfig.bukkit.events.PlayerClickListener;
import org.godfather.fastconfig.bukkit.events.PlayerCommonListener;
import org.godfather.fastconfig.common.FastConfigPlugin;

public final class FastConfig extends FastConfigPlugin {

    @Override
    protected void enable() {
        configManager.loadConfigs();
        registerCommand(new CommandConfig(this, "config"));

        getServer().getPluginManager().registerEvents(new PlayerCommonListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerClickListener(this), this);

        LOGGER.info("§d[FastConfig] Plugin enabled!");
    }

    @Override
    protected void disable() {
        configManager.unloadConfigs();
        LOGGER.info("§d[FastConfig] Plugin disabled...");
    }
}
