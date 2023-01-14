package org.godfather.fastconfig.common.player;

import org.bukkit.Location;
import org.godfather.fastconfig.common.config.Config;

import java.util.Optional;

public class PluginPlayer {

    private ClickThread clickThread = null;

    public Optional<ClickThread> getClickThread() {
        return Optional.ofNullable(clickThread);
    }

    public void putThread(Config config, String path, Location location) {
        clickThread = new ClickThread(this, config, path, location);
    }

    public void removeThread() {
        clickThread = null;
    }
}
