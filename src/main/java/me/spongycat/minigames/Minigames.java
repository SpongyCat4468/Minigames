package me.spongycat.minigames;

import me.spongycat.minigames.listeners.LavaSurvivalListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Minigames extends JavaPlugin {
    public static String version = "1.0";
    public Minigames plugin = this;

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getConfig().options().copyDefaults();
        this.saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(new LavaSurvivalListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
