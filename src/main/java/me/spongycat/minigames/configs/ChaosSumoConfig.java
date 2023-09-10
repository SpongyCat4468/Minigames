package me.spongycat.minigames.configs;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.List;
import java.util.Random;
import java.util.logging.Level;

import static me.spongycat.minigames.Minigames.plugin;

public class ChaosSumoConfig {

    public static World ORIGINAL_WORLD = Bukkit.getWorld(findString("Chaos-Sumo.original-world"));
    public static int ORIGINAL_WORLD_X = findInt("Chaos-Sumo.original-world-coords.x");
    public static int ORIGINAL_WORLD_Y = findInt("Chaos-Sumo.original-world-coords.y");
    public static int ORIGINAL_WORLD_Z = findInt("Chaos-Sumo.original-world-coords.z");
    public static World LOBBY = Bukkit.getWorld(findString("Chaos-Sumo.lobby"));
    public static int LOBBY_X = findInt("Chaos-Sumo.lobby-coords.x");
    public static int LOBBY_Y = findInt("Chaos-Sumo.lobby-coords.x");
    public static int LOBBY_Z = findInt("Chaos-Sumo.lobby-coords.x");
    public static int MIN_PLAYER = findInt("Chaos-Sumo.min-player");
    public static int MAX_PLAYER = findInt("Chaos-Sumo.max-player");
    public static int COUNTDOWN = findInt("Chaos-Sumo.countdown");
    public static List<String> MAP_NAMES = findStringList("Chaos-Sumo.map-names");
    public static List<String> REWARD = findStringList("Chaos-Sumo.reward");
    public static List<Integer> REWARD_AMOUNT = findIntList("Chaos-Sumo.reward-amount");

    public static List<String> REWARD_COMMANDS = findStringList("Chaos-Sumo.reward-commands");
    public static String IP = findString("Chaos-Sumo.ip");
    public static World GAME_WORLD;
    public static int GAME_WORLD_X;
    public static int GAME_WORLD_Y;
    public static int GAME_WORLD_Z;
    public static int FIRST_X;
    public static int FIRST_Y;
    public static int FIRST_Z;
    public static int SECOND_X;
    public static int SECOND_Y;
    public static int SECOND_Z;
    public static Material FILLING_BLOCK;

    public static String rollMap() {
        String mapName = pickRandomMap();
        String worldName = findString("Lava-Survival." + mapName + ".world");
        GAME_WORLD = Bukkit.getWorld(worldName);

        GAME_WORLD_X = findInt("Chaos-Sumo." + mapName + ".world-coords.x");
        GAME_WORLD_Y = findInt("Chaos-Sumo." + mapName + ".world-coords.y");
        GAME_WORLD_Z = findInt("Chaos-Sumo." + mapName + ".world-coords.z");

        FIRST_X = findInt("Chaos-Sumo." + mapName + ".first-coords.x");
        FIRST_Y = findInt("Chaos-Sumo." + mapName + ".first-coords.y");
        FIRST_Z = findInt("Chaos-Sumo." + mapName + ".first-coords.z");

        SECOND_X = findInt("Chaos-Sumo." + mapName + ".second-coords.x");
        SECOND_Y = findInt("Chaos-Sumo." + mapName + ".second-coords.y");
        SECOND_Z = findInt("Chaos-Sumo." + mapName + ".second-coords.z");

        FILLING_BLOCK = Material.getMaterial(findString("Chaos-Sumo." + mapName + "filling-block"));

        return mapName;
    }

    private static String pickRandomMap() {
        List<String> mapNames = MAP_NAMES;
        if (mapNames.isEmpty()) {
            Bukkit.getLogger().log(Level.WARNING, "Map list cannot be empty!");
        }

        Random rand = new Random();
        int randomIndex = rand.nextInt(mapNames.size());
        return mapNames.get(randomIndex);
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
