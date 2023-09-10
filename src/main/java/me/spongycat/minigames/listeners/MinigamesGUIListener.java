package me.spongycat.minigames.listeners;

import me.spongycat.minigames.GUIs.MinigamesGUI;
import me.spongycat.minigames.configs.LavaSurvivalConfig;
import me.spongycat.minigames.scoreboards.LavaSurvivalScoreBoard;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static me.spongycat.minigames.GUIs.MinigamesGUI.inv;

public class MinigamesGUIListener implements Listener {
    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        if (!e.getInventory().equals(inv)) return;

        e.setCancelled(true);

        final ItemStack clickedItem = e.getCurrentItem();

        // verify current item is not null
        if (clickedItem == null || clickedItem.getType().isAir()) return;

        final Player p = (Player) e.getWhoClicked();

        if (clickedItem.isSimilar(MinigamesGUI.lava_bucket)) {
            boolean isLobbyFull = LavaSurvivalScoreBoard.currentPlayersWaiting == LavaSurvivalConfig.MAX_PLAYER;
            if (!isLobbyFull) {
                World lobby = LavaSurvivalConfig.LOBBY;
                int x = LavaSurvivalConfig.LOBBY_X;
                int y = LavaSurvivalConfig.LOBBY_Y;
                int z = LavaSurvivalConfig.LOBBY_Z;
                Location location = new Location(lobby, x, y, z);
                location.setWorld(lobby);
                p.teleport(location);
                LavaSurvivalScoreBoard.showScoreBoard(p);

                ItemStack quit = new ItemStack(Material.RED_BED);
                ItemMeta itemMeta1 = quit.getItemMeta();
                itemMeta1.setDisplayName(ChatColor.RED + "Back to game");
                quit.setItemMeta(itemMeta1);

                p.getInventory().setItem(8, quit);
            } else if (LavaSurvivalScoreBoard.isGameRunning) {
                p.closeInventory();
                p.sendMessage(ChatColor.RED + "Game currently running! Please wait for the game to end!");
            } else {
                p.closeInventory();
                p.sendMessage(ChatColor.RED + "Lobby full! Please wait for the next round!");
            }
        }
    }

    // Cancel dragging in our inventory
    @EventHandler
    public void onInventoryClick(final InventoryDragEvent e) {
        if (e.getInventory().equals(inv)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteractBed(PlayerInteractEvent e) {
        if (e.getPlayer().getWorld() == LavaSurvivalConfig.LOBBY) {
            ItemStack quit = new ItemStack(Material.RED_BED);
            ItemMeta itemMeta1 = quit.getItemMeta();
            itemMeta1.setDisplayName(ChatColor.RED + "Back to game");
            quit.setItemMeta(itemMeta1);
            if (e.getPlayer().getInventory().getItemInMainHand().isSimilar(quit) && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
                World original_world = LavaSurvivalConfig.ORIGINAL_WORLD;
                int x = LavaSurvivalConfig.ORIGINAL_WORLD_X;
                int y = LavaSurvivalConfig.ORIGINAL_WORLD_Y;
                int z = LavaSurvivalConfig.ORIGINAL_WORLD_Z;

                Location location = new Location(original_world, x, y, z);
                e.getPlayer().teleport(location);
                LavaSurvivalScoreBoard.hideScoreBoard(e.getPlayer());
                e.setCancelled(true);
            }
        }
    }
}
