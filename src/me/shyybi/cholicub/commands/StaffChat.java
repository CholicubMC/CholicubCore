package me.shyybi.cholicub.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;

public class StaffChat implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args){
        Player player = (Player) sender;

        if (args.length > 0) {
            String message = String.join(" ", args);
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if (onlinePlayer.hasPermission("staff.use")) {
                    onlinePlayer.sendMessage("§b§l[Staff]§f " + player.getPlayerListName() +  " : " + message);
                }
            }
            return true;
        } else {
            sender.sendMessage("§4Usage: /staffchat <message>");
            return false;
        }

    }
}
