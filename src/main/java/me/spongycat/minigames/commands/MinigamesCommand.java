package me.spongycat.minigames.commands;

import me.spongycat.minigames.GUIs.MinigamesGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MinigamesCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player p) {
            MinigamesGUI.registerGUI();
            MinigamesGUI.openInventory(p);
        }
        return true;
    }
}
