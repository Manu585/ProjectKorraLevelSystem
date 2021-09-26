package me.manu.projectkorralevelsystem.configuration;

import me.manu.projectkorralevelsystem.ProjectKorraLevelSystem;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class Config {

    private final ProjectKorraLevelSystem plugin;

    private final File file;
    private final FileConfiguration config;

    /**
     * Creates a new {@link Config} with the file being the configuration file.
     *
     * @param file The file to create/load
     */
    public Config(final File file) {
        this.plugin = ProjectKorraLevelSystem.getInstance();
        this.file = new File(this.plugin.getDataFolder() + File.separator + file);
        this.config = YamlConfiguration.loadConfiguration(this.file);
        this.reload();
    }

    /**
     * Creates a file for the {@link FileConfiguration} object. If there are
     * missing folders, this method will try to create them before create a file
     * for the config.
     */
    public void create() {
        if (!this.file.getParentFile().exists()) {
            try {
                this.file.getParentFile().mkdir();
                this.plugin.getLogger().info("Generating new directory for " + this.file.getName() + "!");
            } catch (final Exception e) {
                this.plugin.getLogger().info("Failed to generate directory!");
                e.printStackTrace();
            }
        }

        if (!this.file.exists()) {
            try {
                this.file.createNewFile();
                this.plugin.getLogger().info("Generating new " + this.file.getName() + "!");
            } catch (final Exception e) {
                this.plugin.getLogger().info("Failed to generate " + this.file.getName() + "!");
                e.printStackTrace();
            }
        }
    }

    /**
     * Gets the {@link FileConfiguration} object from the {@link Config}.
     *
     * @return the file configuration object
     */
    public FileConfiguration get() {
        return this.config;
    }

    /**
     * Reloads the {@link FileConfiguration} object. If the config object does
     * not exist it will run {@link #create()} first before loading the config.
     */
    public void reload() {
        this.create();
        try {
            this.config.load(this.file);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves the {@link FileConfiguration} object.
     * {@code config.options().copyDefaults(true)} is called before saving the
     * config.
     */
    public void save() {
        try {
            this.config.options().copyDefaults(true);
            this.config.save(this.file);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}
