package me.shyybi.cholicub.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
public class Fly implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Cette commande ne peut être utilisée que par un joueur !");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("fly.use")) {
            player.sendMessage("Vous n'avez pas la permission d'utiliser cette commande !");
            return true;
        }

        if (args.length > 0) {
            player.sendMessage("Utilisation : /fly");
            return true;
        }

        if (player.getAllowFlight()) {
            player.setAllowFlight(false);
            player.setFlying(false);
            player.sendMessage("Le vol a été désactivé !");
        } else {
            player.setAllowFlight(true);
            player.sendMessage("Le vol a été activé !");
        }

        return true;
    }
}
