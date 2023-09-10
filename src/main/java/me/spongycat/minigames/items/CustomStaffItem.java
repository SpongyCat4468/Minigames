package me.spongycat.minigames.items;

import me.spongycat.minigames.utils.ItemStackUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class CustomStaffItem {
    public static ItemStack CHAOS_STAFF = getChaosStaff();
    public static ItemStack TELEPORT_STAFF = getTeleportStaff();
    public static ItemStack SHALL_NOT_PASS_STAFF = getShallNotPassStaff();
    private static ItemStack getChaosStaff() {
        ItemStack ChaosStaff = new ItemStack(Material.WARPED_FUNGUS_ON_A_STICK);
        ItemStackUtil.setDisplayName(ChaosStaff, ChatColor.YELLOW + "Chaos Staff");

        return ChaosStaff;
    }

    private static ItemStack getTeleportStaff() {
        ItemStack TeleportStaff = new ItemStack(Material.WARPED_FUNGUS_ON_A_STICK);
        ItemStackUtil.setDisplayName(TeleportStaff, ChatColor.GREEN + "Teleport Staff");

        return TeleportStaff;
    }

    private static ItemStack getShallNotPassStaff() {
        ItemStack ShallNotPassStaff = new ItemStack(Material.WARPED_FUNGUS_ON_A_STICK);
        ItemStackUtil.setDisplayName(ShallNotPassStaff, ChatColor.GRAY + "Shall Not Pass Staff");
        ShallNotPassStaff.addUnsafeEnchantment(Enchantment.KNOCKBACK, 3);
        ItemStackUtil.hideEnchant(ShallNotPassStaff);

        return ShallNotPassStaff;
    }
}
