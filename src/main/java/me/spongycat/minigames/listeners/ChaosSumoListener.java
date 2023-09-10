package me.spongycat.minigames.listeners;

import me.spongycat.minigames.configs.ChaosSumoConfig;
import me.spongycat.minigames.scoreboards.ChaosSumoScoreBoard;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

import static me.spongycat.minigames.Minigames.plugin;

public class ChaosSumoListener implements Listener {
    @EventHandler
    public void onPlayerDeathInGame(PlayerDeathEvent e) {
        if (e.getEntity().getWorld().equals(ChaosSumoConfig.GAME_WORLD)) {
            ChaosSumoScoreBoard.hideGameScoreBoard(e.getEntity());
        }
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent e) {
        if (e.getPlayer().getWorld().equals(ChaosSumoConfig.GAME_WORLD)) {
            ChaosSumoScoreBoard.hideGameScoreBoard(e.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent e) {
        if (Objects.equals(e.getFrom().getWorld(), ChaosSumoConfig.GAME_WORLD)) {
            ChaosSumoScoreBoard.hideScoreBoard(e.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        if (e.getPlayer().getWorld().equals(ChaosSumoConfig.GAME_WORLD)) {
            World original_world = ChaosSumoConfig.ORIGINAL_WORLD;
            int x = ChaosSumoConfig.ORIGINAL_WORLD_X;
            int y = ChaosSumoConfig.ORIGINAL_WORLD_Y;
            int z = ChaosSumoConfig.ORIGINAL_WORLD_Z;

            Location location = new Location(original_world, x, y, z);

            new BukkitRunnable() {
                @Override
                public void run() {
                    e.getPlayer().teleport(location);
                }
            }.runTaskLater(plugin, 5);
        }
    }
}