package me.spongycat.minigames;

import me.spongycat.minigames.commands.MinigamesCommand;
import me.spongycat.minigames.listeners.LavaSurvivalListener;
import me.spongycat.minigames.listeners.MinigamesGUIListener;
import me.spongycat.minigames.scoreboards.LavaSurvivalScoreBoard;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class Minigames extends JavaPlugin {
    public static String version = "1.0";
    public static Minigames plugin;

    @Override
    public void onEnable() {
        PluginManager pluginManager = getServer().getPluginManager();
        Plugin requiredPlugin = pluginManager.getPlugin("Multiverse-Inventories");

        if (requiredPlugin != null && requiredPlugin.isEnabled()) {
            getLogger().info("Required plugin is present and enabled!");
            // Plugin startup logic
            this.getConfig().options().copyDefaults();
            this.saveDefaultConfig();
            Bukkit.getPluginManager().registerEvents(new LavaSurvivalListener(), this);
            Bukkit.getPluginManager().registerEvents(new MinigamesGUIListener(), this);
            this.getCommand("minigames").setExecutor(new MinigamesCommand());
            plugin = this;
            LavaSurvivalScoreBoard.isFirstTimeAfterAnotherRound = true;
            getLogger().log(Level.INFO, "Minigame plugin initialized!");
        } else {
            getLogger().warning("Required plugin is not present or not enabled!");
            // Handle the situation where the required plugin is missing or not enabled
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
