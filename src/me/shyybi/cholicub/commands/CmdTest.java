package me.shyybi.cholicub.commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;

public class CmdTest implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args){

        sender.sendMessage("Zizi kawa");
        return false;
    }
}
