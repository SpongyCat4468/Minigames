package me.spongycat.minigames.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemStackUtil {
    public static void setDisplayName(ItemStack item, String name) {
        ItemMeta itemMeta = item.getItemMeta();;
        itemMeta.setDisplayName(name);
        item.setItemMeta(itemMeta);
    }
    public static String getDisplayName(ItemStack item) {
        return item.getItemMeta().getDisplayName();
    }
    public static boolean isSimilarMaterial(ItemStack item, Material material) {
        return item.getType() == material;
    }

    public static boolean isSimilarItem(ItemStack item1, ItemStack item2) {
        return item1.isSimilar(item2);
    }

    public static void setLore(ItemStack item, String lore) {
        ItemMeta meta = item.getItemMeta();
        ArrayList<String> loreList = new ArrayList<>();
        loreList.add(lore);
        meta.setLore(loreList);
        item.setItemMeta(meta);
    }

    public static void addLore(ItemStack item, String lore) {
        ItemMeta meta = item.getItemMeta();
        List<String> loreList = meta.getLore();
        if (loreList != null) {
            loreList.add(lore);
        } else {
            setLore(item, lore);
            return;
        }

        meta.setLore(loreList);
        item.setItemMeta(meta);
    }

    public static void clearLore(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        List<String> loreList = meta.getLore();
        if (!loreList.isEmpty()) {
            loreList.clear();
        }
        meta.setLore(loreList);
        item.setItemMeta(meta);
    }

    public static void hideEnchant(ItemStack item) {
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(itemMeta);
    }

    public static void applyCustomTexture(ItemStack itemStack, int customModelData) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setCustomModelData(customModelData);
        itemStack.setItemMeta(itemMeta);
    }


}
