package org.godfather.fastconfig.bukkit.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.godfather.fastconfig.common.FastConfigPlugin;
import org.godfather.fastconfig.common.player.PluginPlayer;

public class PlayerClickListener implements Listener {

    private final FastConfigPlugin plugin;

    public PlayerClickListener(FastConfigPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if(event.getAction() != Action.LEFT_CLICK_BLOCK)
            return;

        if(plugin.getPlayerManager().getProfile(player).isEmpty() || plugin.getPlayerManager().getProfile(player).get().getClickThread().isEmpty())
            return;
        PluginPlayer plPlayer = plugin.getPlayerManager().getProfile(player).get();

        plPlayer.getClickThread().ifPresent(thread -> thread.conclude(event));
        player.sendMessage("§aHai impostato la location di questo blocco.");
        event.setCancelled(true);
    }
}
