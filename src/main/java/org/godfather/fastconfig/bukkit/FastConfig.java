package org.godfather.fastconfig.bukkit;

import org.godfather.fastconfig.bukkit.commands.*;
import org.godfather.fastconfig.bukkit.events.PlayerClickListener;
import org.godfather.fastconfig.bukkit.events.PlayerCommonListener;
import org.godfather.fastconfig.common.FastConfigPlugin;

public final class FastConfig extends FastConfigPlugin {

    @Override
    protected void enable() {
        configManager.loadConfigs();
        registerCommand(new CommandCreate(this, "configcreate"));
        registerCommand(new CommandDelete(this, "configdelete"));
        registerCommand(new CommandModify(this, "configmodify"));
        registerCommand(new CommandReload(this, "configreload"));
        registerCommand(new CommandAbort(this, "configabort"));
        registerCommand(new CommandHelp(this, "confighelp"));

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
