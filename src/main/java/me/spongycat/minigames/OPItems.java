package me.spongycat.minigames;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

public class OPItems {
    public static ItemStack FireResistancePotion = getFireResistancePotion();
    public static ItemStack AllowPVP = getAllowPVP();
    private static ItemStack getFireResistancePotion() {
        ItemStack fireResistancePotion = new ItemStack(Material.POTION);
        PotionMeta potionMeta = (PotionMeta) fireResistancePotion.getItemMeta();

        potionMeta.setBasePotionData(new PotionData(PotionType.FIRE_RESISTANCE));

        // Add the Fire Resistance effect
        PotionEffect fireResistanceEffect = new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 3600, 0); // 3600 ticks = 3 minutes
        potionMeta.addCustomEffect(fireResistanceEffect, true);

        potionMeta.setDisplayName(ChatColor.YELLOW + "Fire Resistance Potion");

        fireResistancePotion.setItemMeta(potionMeta);

        return fireResistancePotion;
    }

    private static ItemStack getAllowPVP() {
        ItemStack PVP = new ItemStack(Material.GOLDEN_AXE);

        ItemMeta itemMeta = PVP.getItemMeta();
        itemMeta.setDisplayName("Enable PVP");

        PVP.setItemMeta(itemMeta);

        return PVP;
    }
}
