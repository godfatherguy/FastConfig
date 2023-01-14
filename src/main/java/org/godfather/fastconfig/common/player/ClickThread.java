package org.godfather.fastconfig.common.player;

import org.bukkit.Location;
import org.bukkit.event.player.PlayerInteractEvent;
import org.godfather.fastconfig.common.config.Config;

public final class ClickThread {

    private final PluginPlayer player;
    private final Config config;
    private final String path;
    private final Location location;

    public ClickThread(PluginPlayer player, Config config, String path, Location location) {
        this.player = player;
        this.config = config;
        this.path = path;
        this.location = location;
    }

    public void conclude(PlayerInteractEvent event) {
        config.getConfig().set(path + ".x", event.getClickedBlock().getX());
        config.getConfig().set(path + ".y", event.getClickedBlock().getY());
        config.getConfig().set(path + ".z", event.getClickedBlock().getZ());
        config.getConfig().set(path + ".yaw", location.getYaw());
        config.getConfig().set(path + ".pitch", location.getPitch());
        config.save();
        player.removeThread();
    }
}
