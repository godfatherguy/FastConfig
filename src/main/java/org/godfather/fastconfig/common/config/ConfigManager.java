package org.godfather.fastconfig.common.config;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class ConfigManager {

    private final JavaPlugin plugin;
    private final Map<String, Config> configs = new HashMap<>();

    public ConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public Optional<Config> getConfig(String name) {
        return Optional.ofNullable(configs.get(name));
    }

    public Map<String, Config> getConfigs() {
        return Collections.unmodifiableMap(configs);
    }

    public void create(String name) {
        Config config = new Config(plugin, name);
        configs.put(name, config);
        config.create();
        config.save();
        config.reload();
    }

    public void remove(String name) {
        getConfig(name).ifPresent(config -> {
            configs.remove(name);
            config.delete();
        });
    }

    public void loadConfigs() {
        plugin.getConfig().getStringList("active-configs").forEach(this::create);
    }

    public void unloadConfigs() {
        List<String> configs = this.configs.keySet().stream().toList();
        plugin.getConfig().set("active-configs", configs);
        this.configs.clear();
    }
}
