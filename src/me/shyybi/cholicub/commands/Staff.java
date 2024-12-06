package me.shyybi.cholicub.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.event.EventHandler;

public class Staff implements CommandExecutor, Listener {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args){
        Player player = (Player) sender;
        sender.sendMessage("test");
        player.getInventory().clear();
        ItemStack vanish = new ItemStack(Material.GHAST_TEAR);
        ItemMeta vanishMeta = vanish.getItemMeta();
        vanishMeta.setDisplayName("§cVanish");
        vanish.setItemMeta(vanishMeta);
        player.getInventory().setItem(8, vanish);


        return false;

    }

    @EventHandler
    public String onInteractStaffMode(PlayerInteractEvent event){
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack it = event.getItem();
        if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) {
            return null;
        }


        if (it== null) return null;

        if(it.getType() == Material.BOOK && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("§cVanish")){

            player.sendMessage("§cVanish");
            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 19999, 1, false, false));
        }
        return null;
    }
}
