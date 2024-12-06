package me.shyybi.cholicub.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;
public class Broadcast implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args){
        if (args.length > 0) {
            String message = String.join(" ", args);
            Bukkit.broadcastMessage("§6[Broadcast] §r" + message);
            return true;
        } else {
            sender.sendMessage("§4Usage: /broadcast <message>");
            return false;
        }
    }
}
