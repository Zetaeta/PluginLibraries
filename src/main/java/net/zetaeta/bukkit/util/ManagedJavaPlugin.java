package net.zetaeta.bukkit.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import net.zetaeta.bukkit.util.configuration.PluginConfiguration;
import net.zetaeta.util.FileUtil;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Zetaeta
 * 
 * A JavaPlugin with some predefined utility methods, useful (but not required) for using a CommandsManager.4
 * Not particularly useful now, may add more stuff in future.
 *
 */
@Useless
public abstract class ManagedJavaPlugin extends JavaPlugin {
    private File configFile;
    private PluginConfiguration config;
    
    @Override
    public PluginConfiguration getConfig() {
        if (config == null) {
            reloadConfig();
        }
        return config;
    }
    
    @Override
    public void reloadConfig() {
        if (configFile == null) {
            configFile = new File(getDataFolder(), "config.yml");
        }
        getDataFolder().mkdirs();
        try {
            configFile.createNewFile();
        } catch (IOException e) {
            getLogger().log(Level.SEVERE, "Could not create config file!", e);
            e.printStackTrace();
        }
        if (configFile.length() == 0 && getResource("config.yml") != null) {
            try {
                FileUtil.copyStreams(getResource("config.yml"), new FileOutputStream(configFile));
            } catch (FileNotFoundException e) {
                getLogger().log(Level.SEVERE, "Could not save defaults to config file", e);
                e.printStackTrace();
            }
        }
        config = PluginConfiguration.loadConfiguration(configFile);
        InputStream defaults = getResource("config.yml");
        if (defaults != null) {
            config.setDefaults(PluginConfiguration.loadConfiguration(defaults));
        }
    }
    
    @Override
    public void saveConfig() {
        if (configFile == null) {
            configFile = new File(getDataFolder(), "config.yml");
        }
        try {
            getConfig().save(configFile);
        } catch (IOException e) {
            getLogger().log(Level.SEVERE, "Could not save config to " + configFile, e);
        }
    }
}
