package org.godfather.fastconfig.bukkit;

import org.godfather.fastconfig.bukkit.commands.CommandCreate;
import org.godfather.fastconfig.bukkit.commands.CommandDelete;
import org.godfather.fastconfig.bukkit.commands.CommandModify;
import org.godfather.fastconfig.common.FastConfigPlugin;

public final class FastConfig extends FastConfigPlugin {

    @Override
    protected void enable() {
        configManager.loadConfigs();
        registerCommand(new CommandCreate(this, "configcreate"));
        registerCommand(new CommandDelete(this, "configdelete"));
        registerCommand(new CommandModify(this, "configmodify"));
    }

    @Override
    protected void disable() {
        configManager.unloadConfigs();
    }
}
