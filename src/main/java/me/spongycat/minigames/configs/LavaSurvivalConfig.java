package me.spongycat.minigames.configs;


import me.spongycat.minigames.Minigames;
import me.spongycat.minigames.scoreboards.LavaSurvivalScoreBoard;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.List;
import java.util.Random;
import java.util.logging.Level;

import static me.spongycat.minigames.Minigames.plugin;

public class LavaSurvivalConfig {
    public static World LOBBY = Bukkit.getWorld(findString("Lava-Survival.lobby"));
    public static int LOBBY_X = findInt("Lava-Survival.lobby-coords.x");
    public static int LOBBY_Y = findInt("Lava-Survival.lobby-coords.y");
    public static int LOBBY_Z = findInt("Lava-Survival.lobby-coords.z");

    public static World ORIGINAL_WORLD = Bukkit.getWorld(findString("Lava-Survival.original-world"));
    public static int ORIGINAL_WORLD_X = findInt("Lava-Survival.original-world-coords.x");
    public static int ORIGINAL_WORLD_Y = findInt("Lava-Survival.original-world-coords.y");
    public static int ORIGINAL_WORLD_Z = findInt("Lava-Survival.original-world-coords.z");
    public static int MIN_PLAYER = findInt("Lava-Survival.min-player");
    public static int MAX_PLAYER = findInt("Lava-Survival.max-player");
    public static int COUNTDOWN = findInt("Lava-Survival.countdown");
    public static int BUILDING_TIME = findInt("Lava-Survival.building-time");
    public static List<String> STARTING_ITEM = findStringList("Lava-Survival.starting-item");
    public static List<Integer> STARTING_ITEM_AMOUNT = findIntList("Lava-Survival.item-amount");
    public static List<String> REWARD = findStringList("Lava-Survival.reward");
    public static List<Integer> REWARD_AMOUNT = findIntList("Lava-Survival.reward-amount");
    public static boolean CAN_PVP = findBoolean("Lava-Survival.pvp-enabled");
    public static boolean DROP_ITEMS_ON_DEATH = findBoolean("Lava-Survival.drop-items-on-death");
    private static final List<String> MAP_NAMES = findStringList("Lava-Survival.map-names");

    private static String pickRandomMap() {
        List<String> mapNames = MAP_NAMES;
        if (mapNames.isEmpty()) {
            Bukkit.getLogger().log(Level.WARNING, "Map list cannot be empty!");
        }

        Random rand = new Random();
        int randomIndex = rand.nextInt(mapNames.size());
        return mapNames.get(randomIndex);
    }

    public static World GAME_WORLD;
    public static int FIRST_X;
    public static int FIRST_Y;
    public static int FIRST_Z;
    public static int SECOND_X;
    public static int SECOND_Y;
    public static int SECOND_Z;
    public static int THIRD_X;
    public static int THIRD_Y;
    public static int THIRD_Z;
    public static int GAME_WORLD_X;
    public static int GAME_WORLD_Y;
    public static int GAME_WORLD_Z;
    public static String rollMap() {
        String mapName = pickRandomMap();
        String worldName = findString("Lava-Survival." + mapName + ".world");
        GAME_WORLD = Bukkit.getWorld(worldName);

        GAME_WORLD_X = findInt("Lava-Survival." + mapName + ".world-coords.x");
        GAME_WORLD_Y = findInt("Lava-Survival." + mapName + ".world-coords.y");
        GAME_WORLD_Z = findInt("Lava-Survival." + mapName + ".world-coords.z");

        FIRST_X = findInt("Lava-Survival." + mapName + ".first-coords.x");
        FIRST_Y = findInt("Lava-Survival." + mapName + ".first-coords.y");
        FIRST_Z = findInt("Lava-Survival." + mapName + ".first-coords.z");

        SECOND_X = findInt("Lava-Survival." + mapName + ".second-coords.x");
        SECOND_Y = findInt("Lava-Survival." + mapName + ".second-coords.y");
        SECOND_Z = findInt("Lava-Survival." + mapName + ".second-coords.z");

        THIRD_X = findInt("Lava-Survival." + mapName + ".third-coords.x");
        THIRD_Y = findInt("Lava-Survival." + mapName + ".third-coords.y");
        THIRD_Z = findInt("Lava-Survival." + mapName + ".third-coords.z");

        return mapName;
    }


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
