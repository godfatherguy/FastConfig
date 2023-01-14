package org.godfather.fastconfig.common.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.godfather.fastconfig.bukkit.FastConfig;
import org.godfather.fastconfig.universal.FileUtil;

import java.io.File;
import java.io.IOException;

public class Config {

    private final String name;
    private final File directory;
    private File customFile;
    private FileConfiguration customConfiguration;

    public Config(JavaPlugin plugin, String name) {
        this.name = name;
        directory = plugin.getDataFolder();
    }

    public void create() {
        customFile = new File(directory, name + ".yml");
        if (!customFile.exists()) {

            boolean mkdir = customFile.getParentFile().mkdirs();
            if (!mkdir)
                FastConfig.LOGGER.info("§eFile created.");
        }

        customConfiguration = YamlConfiguration.loadConfiguration(customFile);
    }

    public void save() {
        try {
            customConfiguration.save(customFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reload() {
        customConfiguration = YamlConfiguration.loadConfiguration(customFile);
    }

    public void delete() {
        if(customFile != null)
            FileUtil.delete(customFile);
    }

    public FileConfiguration getConfig() {
        return customConfiguration;
    }
}
