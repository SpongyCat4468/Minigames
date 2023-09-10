package me.spongycat.minigames.scoreboards;

import me.spongycat.minigames.configs.ChaosSumoConfig;
import me.spongycat.minigames.items.CustomStaffItem;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.List;

import static me.spongycat.minigames.Minigames.plugin;

public class ChaosSumoScoreBoard {
    public static int currentPlayersWaiting = 0;
    private static Scoreboard scoreboard;
    private static Objective objective;
    public static List<Player> playersWaiting = new ArrayList<>();
    private static int previousCurrentPlayersWaiting = 1;
    private static String newScore;
    private static List<Player> playerInGame = new ArrayList<>();
    private static Scoreboard gameScoreboard;
    private static Objective gameObjective;
    private static int playersInGame;
    public static Player winner = null;
    public static boolean isGameRunning = false;
    public static boolean isFirstTimeAfterAnotherRound = false;

    private static List<Player> playersParticipate = new ArrayList<>();
    private static String mapName = "";
    public static void showScoreBoard(Player p) {
        previousCurrentPlayersWaiting = currentPlayersWaiting;
        currentPlayersWaiting ++;

        if (isFirstTimeAfterAnotherRound) {
            currentPlayersWaiting = 1;
            playersWaiting.clear();
            isFirstTimeAfterAnotherRound = false;
            mapName = ChaosSumoConfig.rollMap();
        }

        ScoreboardManager manager = Bukkit.getScoreboardManager();
        scoreboard = manager.getNewScoreboard();

        objective = scoreboard.registerNewObjective("Chaos_Sumo_Scoreboard", "dummy", ChatColor.GREEN + "CHAOS SUMO");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score map = objective.getScore(ChatColor.GREEN + "Map: " + ChatColor.YELLOW + mapName);
        map.setScore(7);

        Score empty3 = objective.getScore(ChatColor.YELLOW + "");
        empty3.setScore(6);

        Score players = objective.getScore("Players: " + ChatColor.GREEN + currentPlayersWaiting + "/" + ChaosSumoConfig.MAX_PLAYER);
        players.setScore(5);

        Score empty = objective.getScore(ChatColor.BLACK + "");
        empty.setScore(4);

        Score waitingTimer = objective.getScore(ChatColor.AQUA + "Waiting for players...");
        waitingTimer.setScore(3);

        Score empty2 = objective.getScore("");
        empty2.setScore(2);

        Score ip = objective.getScore(ChatColor.YELLOW + ChaosSumoConfig.IP);
        ip.setScore(1);

        p.setScoreboard(scoreboard);
        if (!playersWaiting.contains(p)){
            playersWaiting.add(p);
        }

        if (currentPlayersWaiting == ChaosSumoConfig.MIN_PLAYER) {
            for (Player player : playersWaiting) {
                startTimer(player);
            }
        }
        updateScoreBoard();
    }

    public static void showInGameScoreBoard(Player p) {
        playersInGame = playerInGame.size();
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        gameScoreboard = manager.getNewScoreboard();

        gameObjective = gameScoreboard.registerNewObjective("Chaos_Sumo_Game_Scoreboard", "dummy", ChatColor.GREEN + "CHAOS SUMO");
        gameObjective.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score map = gameObjective.getScore(ChatColor.GREEN + "Map: " + ChatColor.YELLOW + mapName);
        map.setScore(5);

        Score empty = gameObjective.getScore(ChatColor.YELLOW + "");
        empty.setScore(4);

        Score players = gameObjective.getScore(playersInGame + " players in game");
        players.setScore(3);

        Score empty2 = gameObjective.getScore("");
        empty2.setScore(2);

        Score ip = gameObjective.getScore(ChatColor.YELLOW + ChaosSumoConfig.IP);
        ip.setScore(1);

        p.setScoreboard(gameScoreboard);
        if (!playerInGame.contains(p)){
            playerInGame.add(p);
        }
        updateGameScoreBoard();
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

    public static void hideGameScoreBoard(Player p) {
        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        Scoreboard emptyScoreboard = scoreboardManager.getNewScoreboard();
        p.setScoreboard(emptyScoreboard);

        if (playerInGame.contains(p)) {
            playersInGame -= 1;
            playerInGame.remove(p);
            updateGameScoreBoard();
            Bukkit.getServer().broadcastMessage(ChatColor.BOLD + p.getDisplayName() + ChatColor.BLUE  + " has been eliminated from the game!");
            p.setGameMode(GameMode.SPECTATOR);
            World world = ChaosSumoConfig.GAME_WORLD;

            int x = ChaosSumoConfig.GAME_WORLD_X;
            int y = ChaosSumoConfig.GAME_WORLD_Y;
            int z = ChaosSumoConfig.GAME_WORLD_Z;

            Location location = new Location(world, x, y, z);
            p.teleport(location);

            if (playerInGame.size() == 1 && playersInGame == 1) {
                winner = playerInGame.get(0);
                endGame();
            }
        }
        for (PotionEffect potionEffect : p.getActivePotionEffects()) {
            p.removePotionEffect(potionEffect.getType());
        }
    }

    public static void updateScoreBoard() {
        for (Player p : playersWaiting) {
            removeScore(p, "Players: " + ChatColor.GREEN + previousCurrentPlayersWaiting + "/" + ChaosSumoConfig.MAX_PLAYER);
        }
        previousCurrentPlayersWaiting = currentPlayersWaiting;
        for (Player p : playersWaiting) {
            addScore(p, "Players: " + ChatColor.GREEN + previousCurrentPlayersWaiting + "/" + ChaosSumoConfig.MAX_PLAYER);
        }
    }

    public static void updateGameScoreBoard() {
        playersInGame = playerInGame.size();
        for (Player p : playerInGame) {
            removeScore(p, gameObjective);
        }

        for (Player player : playerInGame) {
            addGameScore(player, playersInGame + " players in game");
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
    public static void removeScore(Player player, Objective objective) {
        Scoreboard scoreboard = player.getScoreboard();

        if (objective != null) {
            for (String entry : scoreboard.getEntries()) {
            Score score = objective.getScore(entry);

            // Check if the score's value is 3
            if (score.getScore() == 3) {
                scoreboard.resetScores(entry);
            }
        }
        }
    }

    public static void addScore(Player player, String scoreName) {
        Scoreboard scoreboard = player.getScoreboard();
        Objective objective = scoreboard.getObjective("Lava_Survival_Scoreboard"); // Replace with your objective name

        if (objective != null) {
            Score score = objective.getScore(scoreName);
            score.setScore(5);
        }
    }
    public static void addGameScore(Player player, String scoreName) {
        Objective objective = player.getScoreboard().getObjective("Lava_Survival_Game_Scoreboard");
        if (objective != null) {
            Score score = objective.getScore(scoreName);
            score.setScore(3);
        }
    }

    public static void startTimer(Player player) {
        int x = ChaosSumoConfig.COUNTDOWN;

        // Create a BukkitRunnable to run the timer
        new BukkitRunnable() {
            int currentValue = x;
            @Override
            public void run() {
                String previousScore = ChatColor.AQUA + "Waiting for players...";
                newScore = ChatColor.AQUA + "Starting in " + formatTime(currentValue);
                updateTimer(player, previousScore, newScore);

                boolean reachMinPlayer = currentPlayersWaiting >= ChaosSumoConfig.MIN_PLAYER;
                if (currentValue >= 1 && reachMinPlayer) {
                    previousScore = ChatColor.AQUA + "Starting in " + formatTime(currentValue);

                    currentValue--;

                    newScore = ChatColor.AQUA + "Starting in " + formatTime(currentValue);
                    String newDisplayTitle = ChatColor.AQUA + String.valueOf(currentValue);
                    if (currentValue <= 5 && currentValue >= 1) {
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2.0f, 1.0f);
                        displayTitle(player, newDisplayTitle, 1);
                    } else if (currentValue == 0) {
                        cancel();
                        if (!(currentPlayersWaiting < ChaosSumoConfig.MIN_PLAYER)) {
                            startGameGroup(playersWaiting);
                            World world = ChaosSumoConfig.GAME_WORLD;

                            int x = ChaosSumoConfig.GAME_WORLD_X;
                            int y = ChaosSumoConfig.GAME_WORLD_Y;
                            int z = ChaosSumoConfig.GAME_WORLD_Z;

                            Location location = new Location(world, x, y, z);

                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    player.playSound(location, Sound.BLOCK_NOTE_BLOCK_PLING, 3.0f, 2.0f);
                                }
                            }.runTaskLater(plugin, 5);
                        }
                    }
                    updateTimer(player, previousScore, newScore);
                } else {
                    // Timer finished
                    if (!reachMinPlayer) {
                        for (Player p : playersWaiting) {
                            resetTimer(p);
                        }
                        cancel();
                    } else {
                        cancel();
                        if (!(currentPlayersWaiting < ChaosSumoConfig.MIN_PLAYER)) {
                            startGameGroup(playersWaiting);
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
        Objective objective = scoreboard.getObjective("Chaos_Sumo_Scoreboard"); // Replace with your objective name

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
        Objective objective = scoreboard.getObjective("Chaos_Sumo_Scoreboard"); // Replace with your objective name

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
        World world = ChaosSumoConfig.GAME_WORLD;

        int x = ChaosSumoConfig.GAME_WORLD_X;
        int y = ChaosSumoConfig.GAME_WORLD_Y;
        int z = ChaosSumoConfig.GAME_WORLD_Z;

        Location location = new Location(world, x, y, z);
        player.teleport(location);

        player.getInventory().clear();
        player.getInventory().addItem(
                CustomStaffItem.CHAOS_STAFF,
                CustomStaffItem.TELEPORT_STAFF,
                CustomStaffItem.SHALL_NOT_PASS_STAFF
        );

        showInGameScoreBoard(player);
    }

    public static void startGame() {
        isGameRunning = true;
        playersParticipate.addAll(playerInGame);

        updateGameScoreBoard();

        CommandSender sender = Bukkit.getConsoleSender();
        String command = "mv gamerule keepInventory true" + ChaosSumoConfig.GAME_WORLD.getName();
        Bukkit.dispatchCommand(sender, command);

        int minX = ChaosSumoConfig.FIRST_X;
        int minY = ChaosSumoConfig.FIRST_Y;
        int minZ = ChaosSumoConfig.FIRST_Z;
        int maxX = ChaosSumoConfig.SECOND_X;
        int maxY = ChaosSumoConfig.SECOND_Y;
        int maxZ = ChaosSumoConfig.SECOND_Z;
        World world = ChaosSumoConfig.GAME_WORLD;

        int AMAXX = findMax(maxX, minX);
        int AMAXY = findMax(maxY, minY);
        int AMAXZ = findMax(maxZ, minZ);

        int AMINX = findMin(maxX, minX);
        int AMINY = findMin(maxY, minY);
        int AMINZ = findMin(maxZ, minZ);

        if (!(playerInGame.size() == 1)) {
            for (int x = AMINX; x <= AMAXX; x++) {
                for (int y = AMINY; y <= AMAXY; y++) {
                    for (int z = AMINZ; z <= AMAXZ; z++) {
                        Location currentLocation = new Location(world, x, y, z);
                        currentLocation.getBlock().setType(ChaosSumoConfig.FILLING_BLOCK);
                    }
                }
            }
        }

        for (Player player : playerInGame) {
            player.setHealth(20);
            player.setFoodLevel(20);
            player.setSaturation(20.0f);
            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, -1, 5));
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, -1, 5));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, -1, 5));
        }
    }

    private static int findMax(int num1, int num2) {
        return Math.max(num1, num2);
    }

    private static int findMin(int num1, int num2) {
        return Math.min(num1, num2);
    }

    public static void endGame() {
        isGameRunning = false;
        isFirstTimeAfterAnotherRound = true;

        int AmaxX = ChaosSumoConfig.FIRST_X;
        int AmaxY = ChaosSumoConfig.FIRST_Y;
        int AmaxZ = ChaosSumoConfig.FIRST_Z;
        int AminX = ChaosSumoConfig.SECOND_X;
        int AminY = ChaosSumoConfig.SECOND_Y;
        int AminZ = ChaosSumoConfig.SECOND_Z;
        World Aworld = ChaosSumoConfig.GAME_WORLD;

        int MAXX = findMax(AmaxX, AminX);
        int MAXY = findMax(AmaxY, AminY);
        int MAXZ = findMax(AmaxZ, AminZ);

        int MINX = findMin(AmaxX, AminX);
        int MINY = findMin(AmaxY, AminY);
        int MINZ = findMin(AmaxZ, AminZ);

        for (int x = MINX; x <= MAXX; x++) {
            for (int y = MINY; y <= MAXY; y++) {
                for (int z = MINZ; z <= MAXZ; z++) {
                    Location currentLocation = new Location(Aworld, x, y, z);
                    currentLocation.getBlock().setType(ChaosSumoConfig.FILLING_BLOCK);
                }
            }
        }

        List<ItemStack> rewards = new ArrayList<>();

        List<Material> rewardMaterial = new ArrayList<>();

        List<Integer> rewardAmount = ChaosSumoConfig.REWARD_AMOUNT;

        for (String ID : ChaosSumoConfig.REWARD) {
            rewardMaterial.add(Material.getMaterial(ID));
        }

        for (int i = 0; i < rewardMaterial.size(); i++) {
            Material material = rewardMaterial.get(i);
            int amount = rewardAmount.get(i);

            ItemStack itemStack = new ItemStack(material, amount);
            rewards.add(itemStack);
        }

        for (Player player : playersParticipate) {
            World original_world = ChaosSumoConfig.ORIGINAL_WORLD;
            int x = ChaosSumoConfig.ORIGINAL_WORLD_X;
            int y = ChaosSumoConfig.ORIGINAL_WORLD_Y;
            int z = ChaosSumoConfig.ORIGINAL_WORLD_Z;

            Location location = new Location(original_world, x, y, z);
            player.teleport(location);
            player.setGameMode(GameMode.SURVIVAL);
        }

        for (ItemStack itemStack : rewards) {
            winner.getInventory().addItem(itemStack);
        }

        for (String config : ChaosSumoConfig.REWARD_COMMANDS) {
            boolean isEmpty = config.isEmpty() || config.isBlank();
            if (!isEmpty) {
                String playerName = winner.getName();

                // Replace %player% with the actual player's name
                String command = config.replace("%player%", playerName);

                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
            }
        }

        Bukkit.getServer().broadcastMessage(ChatColor.YELLOW + "The winner of the chaos sumo is: " + ChatColor.BOLD + winner.getDisplayName() + ChatColor.RESET+ "!");
        playerInGame.clear();
        playersParticipate.clear();
        playersInGame = 0;
        winner = null;
    }

    private static void displayTitle(Player player, String titleText, int delaySeconds) {
        // Set the title text and timings
        player.sendTitle(titleText, "", 5, (20 * delaySeconds) - 10, 5); // Title, Subtitle, fadeIn, stay, fadeOut
    }
}
