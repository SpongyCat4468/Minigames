package me.spongycat.minigames.GUIs;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MinigamesGUI {
    public static Inventory inv;
    public static ItemStack lava_bucket;
    public static ItemStack sumo;


    public static void registerGUI() {
        // Create a new inventory, with no owner (as this isn't a real inventory), a size of nine, called example
        inv = Bukkit.createInventory(null, 9, "Minigames");

        // Put the items into the inventory
        initializeItems();
    }

    // You can call this whenever you want to put the items in
    public static void initializeItems() {
        lava_bucket = new ItemStack(Material.LAVA_BUCKET);
        ItemMeta lavaMeta = lava_bucket.getItemMeta();
        lavaMeta.setDisplayName(ChatColor.GOLD + "Lava Survival");
        lava_bucket.setItemMeta(lavaMeta);

        sumo = new ItemStack(Material.SLIME_BALL);
        ItemMeta sumoMeta = sumo.getItemMeta();
        sumoMeta.setDisplayName(ChatColor.GREEN + "Chaos Sumo");
        sumo.setItemMeta(sumoMeta);

        inv.setItem(0, lava_bucket);
        inv.setItem(1, sumo);
    }



    // You can open the inventory with this
    public static void openInventory(final HumanEntity ent) {
        ent.openInventory(inv);
    }
}
