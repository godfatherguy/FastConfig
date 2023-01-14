package org.godfather.fastconfig.bukkit.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.godfather.fastconfig.common.FastConfigPlugin;

public class PlayerCommonListener implements Listener {

    private final FastConfigPlugin plugin;

    public PlayerCommonListener(FastConfigPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        plugin.getPlayerManager().setupProfile(player);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onQuit(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        plugin.getPlayerManager().removeProfile(player);
    }
}
