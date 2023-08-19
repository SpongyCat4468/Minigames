package me.spongycat.minigames.scoreboards;

import me.spongycat.minigames.configs.LavaSurvivalConfig;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static me.spongycat.minigames.Minigames.plugin;

public class LavaSurvivalScoreBoard {
    public static int currentPlayersWaiting = 0;
    private static Scoreboard scoreboard;
    private static Objective objective;
    public static List<Player> playersWaiting = new ArrayList<>();
    private static int previousCurrentPlayersWaiting = 1;
    private static boolean isTimerReset = false;
    private static String newScore;
    public static void showScoreBoard(Player p) {
        previousCurrentPlayersWaiting = currentPlayersWaiting;
        currentPlayersWaiting ++;

        ScoreboardManager manager = Bukkit.getScoreboardManager();
        scoreboard = manager.getNewScoreboard();

        objective = scoreboard.registerNewObjective("Lava_Survival_Scoreboard", "dummy", ChatColor.GOLD + "LAVA SURVIVAL");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score empty = objective.getScore(ChatColor.BLACK + "");
        empty.setScore(5);

        Score players = objective.getScore("Players: " + ChatColor.GREEN + currentPlayersWaiting + "/" + LavaSurvivalConfig.MAX_PLAYER);
        players.setScore(4);

        Score waitingTimer = objective.getScore(ChatColor.AQUA + "Waiting for players...");
        waitingTimer.setScore(3);

        Score empty2 = objective.getScore("");
        empty2.setScore(2);

        Score ip = objective.getScore(ChatColor.YELLOW + "varrock.apexmc.co");
        ip.setScore(1);

        p.setScoreboard(scoreboard);
        if (!playersWaiting.contains(p)){
            playersWaiting.add(p);
        }

        if (currentPlayersWaiting == LavaSurvivalConfig.MIN_PLAYER) {
            for (Player player : playersWaiting) {
                startTimer(player);
            }
        }
        updateScoreBoard();
    }

    public static void hideScoreBoard(Player p) {
        previousCurrentPlayersWaiting = currentPlayersWaiting;
        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        Scoreboard emptyScoreboard = scoreboardManager.getNewScoreboard();
        p.setScoreboard(emptyScoreboard);
        currentPlayersWaiting -= 1;
        playersWaiting.remove(p);
        updateScoreBoard();
    }

    public static void updateScoreBoard() {
        for (Player p : playersWaiting) {
            removeScore(p, "Players: " + ChatColor.GREEN + previousCurrentPlayersWaiting + "/" + LavaSurvivalConfig.MAX_PLAYER);
        }
        previousCurrentPlayersWaiting = currentPlayersWaiting;
        for (Player p : playersWaiting) {
            addScore(p, "Players: " + ChatColor.GREEN + previousCurrentPlayersWaiting + "/" + LavaSurvivalConfig.MAX_PLAYER);
        }
    }

    public static void removeScore(Player player, String scoreName) {
        Scoreboard scoreboard = player.getScoreboard();
        Objective objective = scoreboard.getObjective("Lava_Survival_Scoreboard"); // Replace with your objective name

        if (objective != null) {
            Score score = objective.getScore(scoreName);
            score.setScore(0); // Set the score to 0 or any other value you prefer
            scoreboard.resetScores(scoreName); // Remove the score from the scoreboard
        }
    }
    public static void addScore(Player player, String scoreName) {
        Scoreboard scoreboard = player.getScoreboard();
        Objective objective = scoreboard.getObjective("Lava_Survival_Scoreboard"); // Replace with your objective name

        if (objective != null) {
            Score score = objective.getScore(scoreName);
            score.setScore(4);
        }
    }

    public static void startTimer(Player player) {
        int x = LavaSurvivalConfig.COUNTDOWN;

        // Create a BukkitRunnable to run the timer
        new BukkitRunnable() {
            int currentValue = x;
            @Override
            public void run() {
                String previousScore = ChatColor.AQUA + "Waiting for players...";
                newScore = ChatColor.AQUA + "Starting in " + formatTime(currentValue);
                updateTimer(player, previousScore, newScore);

                boolean reachMinPlayer = currentPlayersWaiting >= LavaSurvivalConfig.MIN_PLAYER;
                if (currentValue >= 2 && reachMinPlayer) {
                    previousScore = ChatColor.AQUA + "Starting in " + formatTime(currentValue);

                    currentValue--;

                    newScore = ChatColor.AQUA + "Starting in " + formatTime(currentValue);
                    updateTimer(player, previousScore, newScore);
                } else {
                    // Timer finished
                    if (!reachMinPlayer) {
                        for (Player p : playersWaiting) {
                            resetTimer(p);
                        }
                        cancel();
                        isTimerReset = false;
                    } else {
                        cancel();
                        try {
                            if (!(currentPlayersWaiting < LavaSurvivalConfig.MIN_PLAYER)) {
                                startGameGroup(playersWaiting);
                            }
                        } catch (Exception e) {
                            plugin.getLogger().log(Level.WARNING, "Minigame plugin has generated an exception, but you can safely ignore this warning");
                        }
                    }
                }
            }
        }.runTaskTimer(plugin, 0, 20); // Divide by x to evenly distribute updates
    }

    public static String formatTime(int seconds) {
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;

        return minutes + " m " + remainingSeconds + " s";
    }

    public static void updateTimer(Player player, String previousScoreName, String newScoreName) {
        Scoreboard scoreboard = player.getScoreboard();
        Objective objective = scoreboard.getObjective("Lava_Survival_Scoreboard"); // Replace with your objective name

        if (objective != null) {
            Score waitingTimer = objective.getScore(ChatColor.AQUA + "Waiting for players...");
            waitingTimer.setScore(0);
            scoreboard.resetScores(ChatColor.AQUA + "Waiting for players...");
            scoreboard.resetScores(previousScoreName);

            Score score = objective.getScore(newScoreName);
            score.setScore(3);
        }
    }

    public static void resetTimer(Player player) {
        Scoreboard scoreboard = player.getScoreboard();
        Objective objective = scoreboard.getObjective("Lava_Survival_Scoreboard"); // Replace with your objective name

        if (objective != null) {
            Score waitingTimer = objective.getScore(ChatColor.AQUA + "Waiting for players...");
            waitingTimer.setScore(3);
            scoreboard.resetScores(newScore);
        }
    }

    public static void startGameGroup(List<Player> playersWaiting) {
        List<Player> playerList = new ArrayList<>(playersWaiting);

        for (Player p : playerList) {
            hideScoreBoard(p);
            initialGameForPlayers(p);
        }
        startGame();
    }

    public static void initialGameForPlayers(Player player) {
        World world = LavaSurvivalConfig.GAME_WORLD;

        int x = LavaSurvivalConfig.GAME_WORLD_X;
        int y = LavaSurvivalConfig.GAME_WORLD_Y;
        int z = LavaSurvivalConfig.GAME_WORLD_Z;

        Location location = new Location(world, x, y, z);
        player.teleport(location);

        List<ItemStack> initialItems = new ArrayList<>();

        List<Material> initialMaterials = new ArrayList<>();

        List<Integer> initialAmounts = LavaSurvivalConfig.STARTING_ITEM_AMOUNT;

        for (String ID : LavaSurvivalConfig.STARTING_ITEM) {
            initialMaterials.add(Material.getMaterial(ID));
        }

        for (int i = 0; i < initialMaterials.size(); i++) {
            Material material = initialMaterials.get(i);
            int amount = initialAmounts.get(i);

            ItemStack itemStack = new ItemStack(material, amount);
            initialItems.add(itemStack);
        }

        player.getInventory().clear();
        for (ItemStack itemStack : initialItems) {
            player.getInventory().addItem(itemStack);
        }
    }

    public static void startGame() {
        int minX = LavaSurvivalConfig.FIRST_X;
        int minY = LavaSurvivalConfig.FIRST_Y;
        int minZ = LavaSurvivalConfig.FIRST_Z;
        int maxX = LavaSurvivalConfig.SECOND_X;
        int maxY = LavaSurvivalConfig.SECOND_Y;
        int maxZ = LavaSurvivalConfig.SECOND_Z;
        World world = LavaSurvivalConfig.GAME_WORLD;
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    Location currentLocation = new Location(world, x, y, z);
                    currentLocation.getBlock().setType(Material.LAVA);
                }
            }
        }

        int BminX = LavaSurvivalConfig.FIRST_X;
        int BminY = LavaSurvivalConfig.FIRST_Y - 1;
        int BminZ = LavaSurvivalConfig.FIRST_Z;
        int BmaxX = LavaSurvivalConfig.SECOND_X;
        int BmaxY = LavaSurvivalConfig.SECOND_Y - 1;
        int BmaxZ = LavaSurvivalConfig.SECOND_Z;
        World Bworld = LavaSurvivalConfig.GAME_WORLD;
        for (int x = BminX; x <= BmaxX; x++) {
            for (int y = BminY; y <= BmaxY; y++) {
                for (int z = BminZ; z <= BmaxZ; z++) {
                    Location currentLocation = new Location(Bworld, x, y, z);
                    currentLocation.getBlock().setType(Material.BARRIER);
                }
            }
        }
    }
}
