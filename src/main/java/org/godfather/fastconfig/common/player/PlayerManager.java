package org.godfather.fastconfig.common.player;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PlayerManager {

    private final Map<Player, PluginPlayer> players = new HashMap<>();

    public Optional<PluginPlayer> getProfile(Player player) {
        return Optional.ofNullable(players.get(player));
    }

    public void setupProfile(Player player) {
        players.put(player, new PluginPlayer());
    }

    public void removeProfile(Player player) {
        players.remove(player);
    }
}
