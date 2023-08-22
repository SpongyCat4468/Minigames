package me.spongycat.minigames.configs;


import me.spongycat.minigames.scoreboards.LavaSurvivalScoreBoard;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.List;

import static me.spongycat.minigames.Minigames.plugin;

public class LavaSurvivalConfig {
    public static World LOBBY = Bukkit.getWorld(findString("Lava-Survival.lobby"));
    public static int LOBBY_X = findInt("Lava-Survival.lobby-coords.x");
    public static int LOBBY_Y = findInt("Lava-Survival.lobby-coords.y");
    public static int LOBBY_Z = findInt("Lava-Survival.lobby-coords.z");
    public static World GAME_WORLD = Bukkit.getWorld(findString("Lava-Survival.game-world"));
    public static int GAME_WORLD_X = findInt("Lava-Survival.game-world-coords.x");
    public static int GAME_WORLD_Y = findInt("Lava-Survival.game-world-coords.y");
    public static int GAME_WORLD_Z = findInt("Lava-Survival.game-world-coords.z");

    public static World ORIGINAL_WORLD = Bukkit.getWorld(findString("Lava-Survival.original-world"));
    public static int ORIGINAL_WORLD_X = findInt("Lava-Survival.original-world-coords.x");
    public static int ORIGINAL_WORLD_Y = findInt("Lava-Survival.original-world-coords.y");
    public static int ORIGINAL_WORLD_Z = findInt("Lava-Survival.original-world-coords.z");
    public static int MIN_PLAYER = findInt("Lava-Survival.min-player");
    public static int MAX_PLAYER = findInt("Lava-Survival.max-player");
    public static int FIRST_X = findInt("Lava-Survival.first-coords.x");
    public static int FIRST_Y = findInt("Lava-Survival.first-coords.y");
    public static int FIRST_Z = findInt("Lava-Survival.first-coords.z");
    public static int SECOND_X = findInt("Lava-Survival.second-coords.x");
    public static int SECOND_Y = findInt("Lava-Survival.second-coords.y");
    public static int SECOND_Z = findInt("Lava-Survival.second-coords.z");
    public static int THIRD_X = findInt("Lava-Survival.third-coords.x");
    public static int THIRD_Y = findInt("Lava-Survival.third-coords.y");
    public static int THIRD_Z = findInt("Lava-Survival.third-coords.z");



    public static int COUNTDOWN = findInt("Lava-Survival.countdown");
    public static int BUILDING_TIME = findInt("Lava-Survival.building-time");
    public static List<String> STARTING_ITEM = findStringList("Lava-Survival.starting-item");
    public static List<Integer> STARTING_ITEM_AMOUNT = findIntList("Lava-Survival.item-amount");
    public static List<String> REWARD = findStringList("Lava-Survival.reward");
    public static List<Integer> REWARD_AMOUNT = findIntList("Lava-Survival.reward-amount");
    public static boolean CAN_PVP = findBoolean("Lava-Survival.pvp-enabled");
    public static boolean DROP_ITEMS_ON_DEATH = findBoolean("Lava-Survival.drop-items-on-death");


    public static boolean findBoolean(String path) {
        return plugin.getConfig().getBoolean(path);
    }

    public static int findInt(String path) {
        return plugin.getConfig().getInt(path);
    }

    public static String findString(String path) {
        return plugin.getConfig().getString(path);
    }

    public static List<String> findStringList(String path) {
        return plugin.getConfig().getStringList(path);
    }

    public static List<Integer> findIntList(String path) {
        return plugin.getConfig().getIntegerList(path);
    }
}
