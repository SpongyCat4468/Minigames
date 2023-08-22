package me.spongycat.minigames.listeners;

import me.spongycat.minigames.configs.LavaSurvivalConfig;
import me.spongycat.minigames.scoreboards.LavaSurvivalScoreBoard;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

import static me.spongycat.minigames.Minigames.plugin;

public class LavaSurvivalListener implements Listener {
    @EventHandler
    public void onPlayerDeathInGame(PlayerDeathEvent e) {
        if (e.getEntity().getWorld().equals(LavaSurvivalConfig.GAME_WORLD)) {
            LavaSurvivalScoreBoard.hideGameScoreBoard(e.getEntity());
        }
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent e) {
        if (Objects.equals(e.getFrom().getWorld(), LavaSurvivalConfig.GAME_WORLD)) {
            LavaSurvivalScoreBoard.hideScoreBoard(e.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerPVP(EntityDamageByEntityEvent e) {
        // Check if the damage is caused by a player attacking another player
        if (e.getDamager() instanceof org.bukkit.entity.Player &&
                e.getEntity() instanceof org.bukkit.entity.Player) {
            if (e.getDamager().getWorld().equals(LavaSurvivalConfig.GAME_WORLD) && !LavaSurvivalConfig.CAN_PVP) {
                e.setCancelled(true); // Cancel the PvP damage
            }
            if (e.getDamager().getWorld().equals(LavaSurvivalConfig.LOBBY)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        if (e.getPlayer().getWorld().equals(LavaSurvivalConfig.GAME_WORLD)) {
            World original_world = LavaSurvivalConfig.ORIGINAL_WORLD;
            int x = LavaSurvivalConfig.ORIGINAL_WORLD_X;
            int y = LavaSurvivalConfig.ORIGINAL_WORLD_Y;
            int z = LavaSurvivalConfig.ORIGINAL_WORLD_Z;

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
