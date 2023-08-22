package me.spongycat.minigames;

import me.spongycat.minigames.commands.MinigamesCommand;
import me.spongycat.minigames.listeners.LavaSurvivalListener;
import me.spongycat.minigames.listeners.MinigamesGUIListener;
import me.spongycat.minigames.scoreboards.LavaSurvivalScoreBoard;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Minigames extends JavaPlugin {
    public static String version = "1.0";
    public static Minigames plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getConfig().options().copyDefaults();
        this.saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(new LavaSurvivalListener(), this);
        Bukkit.getPluginManager().registerEvents(new MinigamesGUIListener(), this);
        this.getCommand("minigames").setExecutor(new MinigamesCommand());
        plugin = this;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
