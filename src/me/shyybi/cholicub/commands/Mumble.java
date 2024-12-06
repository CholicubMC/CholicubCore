package me.shyybi.cholicub.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Mumble implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args){

        sender.sendMessage("§9§lMumble §f▶ §fhttps://mumble.cholicub.com/");
        return true;
    }
}
