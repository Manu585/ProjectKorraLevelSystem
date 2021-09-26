package me.manu.projectkorralevelsystem.configuration;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;

public class ConfigManager {

    public static Config defaultConfig;
    public static Config languageConfig;

    public ConfigManager() {
        defaultConfig = new Config(new File("config.yml"));
        languageConfig = new Config(new File("language.yml"));
        configCheck(ConfigType.DEFAULT);
    }

    public static void configCheck(final ConfigType type) {
        FileConfiguration config;
        if (type == ConfigType.DEFAULT) {
            config = defaultConfig.get();

            defaultConfig.save();
        } else if (type == ConfigType.LANGUAGE) {
            config = languageConfig.get();
            languageConfig.save();
        }
    }

    public static FileConfiguration getConfig() {
        return ConfigManager.defaultConfig.get();
    }
}