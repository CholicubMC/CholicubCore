package me.shyybi.cholicub.commands;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.model.user.UserManager;
import net.luckperms.api.node.types.InheritanceNode;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class Rank implements CommandExecutor, Listener {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        if (args.length == 1) {
            Player targetPlayer = Bukkit.getPlayer(args[0]);
            if (targetPlayer != null) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    // Create an inventory
                    Inventory rankInventory = Bukkit.createInventory(null, 9, "Rank Inventory");

                    ItemStack playerHead = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
                    SkullMeta playerHeadMeta = (SkullMeta) playerHead.getItemMeta();
                    playerHeadMeta.setOwner(targetPlayer.getName());
                    playerHeadMeta.setDisplayName("§b§l" + targetPlayer.getName());
                    playerHead.setItemMeta(playerHeadMeta);

                    ItemStack admin = new ItemStack(Material.WOOL, 1, (short) 14);
                    ItemMeta adminMeta = admin.getItemMeta();
                    adminMeta.setDisplayName("§4§lAdmin");
                    admin.setItemMeta(adminMeta);

                    ItemStack dev = new ItemStack(Material.WOOL, 1, (short) 1);
                    ItemMeta devMeta = dev.getItemMeta();
                    devMeta.setDisplayName("§6§lDév");
                    dev.setItemMeta(devMeta);

                    ItemStack mod = new ItemStack(Material.WOOL, 1, (short) 3);
                    ItemMeta modMeta = mod.getItemMeta();
                    modMeta.setDisplayName("§3§lMod");
                    mod.setItemMeta(modMeta);

                    ItemStack guest = new ItemStack(Material.WOOL, 1, (short) 10);
                    ItemMeta guestMeta = guest.getItemMeta();
                    guestMeta.setDisplayName("§5§lGuest");
                    guest.setItemMeta(guestMeta);

                    ItemStack friend = new ItemStack(Material.WOOL, 1, (short) 2);
                    ItemMeta friendMeta = friend.getItemMeta();
                    friendMeta.setDisplayName("§d§lFriend");
                    friend.setItemMeta(friendMeta);

                    ItemStack host = new ItemStack(Material.WOOL, 1, (short) 5);
                    ItemMeta hostMeta = host.getItemMeta();
                    hostMeta.setDisplayName("§2§lHost");
                    host.setItemMeta(hostMeta);

                    ItemStack clear = new ItemStack(Material.BARRIER);
                    ItemMeta clearMeta = clear.getItemMeta();
                    clearMeta.setDisplayName("§f§lClear");
                    clear.setItemMeta(clearMeta);

                    rankInventory.setItem(0, playerHead);
                    rankInventory.setItem(2, admin);
                    rankInventory.setItem(3, dev);
                    rankInventory.setItem(4, mod);
                    rankInventory.setItem(5, guest);
                    rankInventory.setItem(6, friend);
                    rankInventory.setItem(7, host);
                    rankInventory.setItem(8, clear);

                    // Open the inventory for the player who executed the command
                    player.openInventory(rankInventory);
                    return true;
                } else {
                    sender.sendMessage("§c§lOnly players can use this command.");
                    return false;
                }
            } else {
                sender.sendMessage("§c§lPlayer not found.");
                return false;
            }
        } else {
            sender.sendMessage("§lsage: §6§l/rank <Pseudo>");
            return false;
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        ItemStack clickedItem = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();
        String inventoryTitle = player.getOpenInventory().getTitle();

        if (inventoryTitle.startsWith("Rank Inventory")) {
            String targetPlayerName = inventoryTitle.replace("Rank Inventory", "").trim();
            Player targetPlayer = Bukkit.getPlayer(targetPlayerName);

            if (targetPlayer != null && clickedItem != null && clickedItem.hasItemMeta() && clickedItem.getItemMeta().hasDisplayName()) {
                LuckPerms luckPerms = LuckPermsProvider.get();
                UserManager userManager = luckPerms.getUserManager();
                User user = userManager.getUser(targetPlayer.getUniqueId());

                if (user != null) {
                    user.data().clear();
                    switch (clickedItem.getItemMeta().getDisplayName()) {
                        case "§4§lAdmin":
                            player.sendMessage("Rank : §4§lAdmin");
                            user.data().add(InheritanceNode.builder("administrateur").build());
                            break;
                        case "§6§lDév":
                            player.sendMessage("Rank : §6§lDév");
                            user.data().add(InheritanceNode.builder("developpeur").build());
                            break;
                        case "§3§lMod":
                            player.sendMessage("Rank : §3§lMod");
                            user.data().add(InheritanceNode.builder("moderateur").build());
                            break;
                        case "§5§lGuest":
                            player.sendMessage("Rank : §5§lGuest");
                            user.data().add(InheritanceNode.builder("guest").build());
                            break;
                        case "§d§lFriend":
                            player.sendMessage("Rank : §d§lFriend");
                            user.data().add(InheritanceNode.builder("friend").build());
                            break;
                        case "§2§lHost":
                            player.sendMessage("Rank : §2§lHost");
                            user.data().add(InheritanceNode.builder("host").build());
                            break;
                    }
                    luckPerms.getUserManager().saveUser(user);
                }
                player.closeInventory();
                userManager.deletePlayerData(targetPlayer.getUniqueId());
            }
            if (clickedItem != null && clickedItem.getType() == Material.BARRIER && clickedItem.hasItemMeta() && clickedItem.getItemMeta().hasDisplayName()) {
                LuckPerms luckPerms = LuckPermsProvider.get();
                UserManager userManager = luckPerms.getUserManager();
                User user = userManager.getUser(targetPlayer.getUniqueId());

                if (user != null) {
                    user.data().clear();
                    user.data().add(InheritanceNode.builder("default").build());
                    player.sendMessage("Rank : §7§lJoueur ");
                    luckPerms.getUserManager().saveUser(user);
                }
                player.closeInventory();
                userManager.deletePlayerData(targetPlayer.getUniqueId());
            }
        }
    }
}