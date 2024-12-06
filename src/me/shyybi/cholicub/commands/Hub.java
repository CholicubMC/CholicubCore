package me.shyybi.cholicub.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Hub implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args){
        if (sender instanceof Player) {
            Player player = (Player) sender;
            player.performCommand("world world");
        }
        return true;
    }
}