package me.shyybi.cholicub.eventmanager;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.InheritanceNode;
import net.md_5.bungee.api.CommandSender;
import org.apache.commons.lang.StringUtils;
import org.bukkit.*;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import net.md_5.bungee.api.config.ServerInfo;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.Arrays;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.model.user.UserManager;
import org.bukkit.plugin.messaging.Messenger;


public class PlayerManager implements Listener  {


    public static final String HR = ChatColor.STRIKETHROUGH + StringUtils.repeat(" ", 97);
    public static final String title = ChatColor.STRIKETHROUGH + StringUtils.repeat("-", 19) + ChatColor.GOLD + " CholicubUHC " + ChatColor.WHITE +ChatColor.STRIKETHROUGH +StringUtils.repeat("-", 19);
    @EventHandler
    public void onJoin(PlayerJoinEvent event){

        event.setJoinMessage(null);

        Player player = event.getPlayer();
        player.setFoodLevel(20);
        player.setHealth(20);
        player.setExp(0);

        player.sendMessage("");
        player.sendMessage(title);
        player.sendMessage("");
        player.sendMessage("            Bienvenue " + ChatColor.AQUA + player.getPlayerListName() + ChatColor.WHITE +" sur" + ChatColor.GOLD +" CholicubUHC");
        player.sendMessage("              Utilise " + ChatColor.GRAY +"l'étoile" + ChatColor.WHITE + " pour rejoindre une partie !");
        player.sendMessage("");
        player.sendMessage("  Rejoins notre" + ChatColor.DARK_AQUA + " discord "+ ChatColor.WHITE +"pour être au courant des parties !");
        player.sendMessage("                    §3§nhttps://discord.com/cholicubuhc");
        player.sendMessage("");
        player.sendMessage(HR);
        player.sendMessage("");

        player.setGameMode(GameMode.ADVENTURE);
        player.setFallDistance(0);
        player.getInventory().clear();
        // Clear inventaire
        Location targetLocation = new Location(Bukkit.getWorld("world"), -0.5, 100, -12.5, 180, 0);
        player.teleport(targetLocation);

        if(player.hasPermission("fly.use")){
            player.setAllowFlight(true);
        }

        ItemStack socialMenu = new ItemStack(Material.BOOK);
        ItemMeta socialMeta = socialMenu.getItemMeta();
        socialMeta.setDisplayName("§9§lNos réseaux");
        socialMenu.setItemMeta(socialMeta);
        player.getInventory().setItem(0, socialMenu);

        ItemStack genderMenu = new ItemStack(Material.BOOK);
        ItemMeta genderMeta = socialMenu.getItemMeta();
        genderMeta.setDisplayName("§9§lPronoms");
        genderMenu.setItemMeta(genderMeta);

        ItemStack playerProfileMenu = new ItemStack(Material.SKULL_ITEM, 1, (byte)3);
        SkullMeta playerProfileMeta = (SkullMeta) playerProfileMenu.getItemMeta();
        playerProfileMeta.setOwner(player.getPlayerListName());
        playerProfileMeta.setDisplayName("§6§lMon profil");
        playerProfileMenu.setItemMeta(playerProfileMeta);
        player.getInventory().setItem(1, playerProfileMenu);
        // Tete du joueur pour ouvrir GUI profile (pronoms etc)


        ItemStack gamesMenu = new ItemStack(Material.NETHER_STAR);
        ItemMeta gamesMeta = gamesMenu.getItemMeta();
        gamesMeta.setDisplayName("§7§lMenu");
        gamesMenu.setItemMeta(gamesMeta);
        player.getInventory().setItem(4, gamesMenu);
        // Ajout



        ItemStack shopMenu = new ItemStack(Material.GOLD_INGOT);
        ItemMeta shopMeta = shopMenu.getItemMeta();
        shopMeta.setDisplayName("§e§lBoutique");
        shopMenu.setItemMeta(shopMeta);
        player.getInventory().setItem(7, shopMenu);
        // Menu Boutique

        ItemStack hubMenu = new ItemStack(Material.BEACON);
        ItemMeta hubMeta = hubMenu.getItemMeta();
        hubMeta.setDisplayName("§3§lHub");
        hubMenu.setItemMeta(hubMeta);
        player.getInventory().setItem(8, hubMenu);

        player.updateInventory();
        player.getInventory().setHeldItemSlot(4);

        player.setGameMode(GameMode.ADVENTURE);
    }

    public String getPlayerRank(Player player) {
        LuckPerms luckPerms = LuckPermsProvider.get();
        UserManager userManager = luckPerms.getUserManager();
        User user = userManager.getUser(player.getUniqueId());

        if (user != null) {
            return user.getPrimaryGroup();
        } else {
            return null;
        }
    }
    @EventHandler
    public String onInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack it = event.getItem();
        if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) {
            return null;
        }
        // Si pas d'item dans la main ne rien faire
        if (it== null) return null;

        // Socials Item
        if(it.getType() == Material.BOOK && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("§9§lNos réseaux")){
            Inventory invSocial = Bukkit.createInventory(null, 27, "§8§lRéseaux sociaux");
            ItemStack twitter = new ItemStack(Material.PRISMARINE_SHARD);
            ItemMeta twitterMeta = twitter.getItemMeta();
            twitterMeta.setDisplayName("§3§lTwitter");
            twitter.setItemMeta(twitterMeta);
            invSocial.setItem(13, twitter);
            player.openInventory(invSocial);

        }

        // Profile Item
        if(it.getType() == Material.SKULL_ITEM && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("§6§lMon profil")){
            Inventory invProfile = Bukkit.createInventory(null, 45, "§8§lProfil de "+ player.getPlayerListName());

            // Head
            ItemStack playerHead = new ItemStack(Material.SKULL_ITEM, 1, (byte)3);
            SkullMeta playerHeadMeta = (SkullMeta) playerHead.getItemMeta();
            playerHeadMeta.setOwner(player.getPlayerListName());
            playerHeadMeta.setDisplayName("§b§l"+player.getPlayerListName());
            playerHead.setItemMeta(playerHeadMeta);
            invProfile.setItem(13, playerHead);

            ItemStack genderShe = new ItemStack(Material.INK_SACK, 1, (byte)9);
            ItemMeta genderSheMeta = genderShe.getItemMeta();
            genderSheMeta.setDisplayName("§3§nPronoms :");
            genderSheMeta.setLore(Arrays.asList("§d§lOuvrir le menu des pronoms"));
            genderShe.setItemMeta(genderSheMeta);
            invProfile.setItem(30, genderShe);

            ItemStack playerRank = new ItemStack(Material.ITEM_FRAME);
            ItemMeta playerRankMeta = playerRank.getItemMeta();
            playerRankMeta.setDisplayName("§7§nRang :");

            LuckPerms luckPerms = LuckPermsProvider.get();
            UserManager userManager = luckPerms.getUserManager();
            User user = userManager.getUser(player.getUniqueId());

            String rank;
            if (user != null) {
                String prefix = user.getCachedData().getMetaData().getPrefix();
                rank = prefix != null ? prefix : "Pas de Rank";
            } else {
                rank = "Pas de Rank";
            }
            String[] rankArray = {rank};
            //playerRankMeta.setLore(Arrays.asList(rankArray));
            playerRankMeta.setLore(Arrays.asList(ChatColor.translateAlternateColorCodes('&',rank)));
            playerRank.setItemMeta(playerRankMeta);
            invProfile.setItem(32, playerRank);

            ItemStack decoLedge = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte)9);
            ItemMeta decoLedgeMeta = decoLedge.getItemMeta();
            decoLedgeMeta.setDisplayName(" ");
            decoLedge.setItemMeta(decoLedgeMeta);

            // Profile Inventory
            invProfile.setItem(0, decoLedge);
            invProfile.setItem(1, decoLedge);
            invProfile.setItem(7, decoLedge);
            invProfile.setItem(8, decoLedge);
            invProfile.setItem(9, decoLedge);
            invProfile.setItem(17, decoLedge);
            invProfile.setItem(27, decoLedge);
            invProfile.setItem(35, decoLedge);
            invProfile.setItem(36, decoLedge);
            invProfile.setItem(37, decoLedge);
            invProfile.setItem(43, decoLedge);
            invProfile.setItem(44, decoLedge);
            player.openInventory(invProfile);
        }

        // Menu Item
        if(it.getType() == Material.NETHER_STAR && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("§7§lMenu")){
            Inventory invMenu = Bukkit.createInventory(player, 54, "§8§lMenu de sélection de jeu");
            ItemStack decoLedge = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte)9);
            ItemMeta decoLedgeMeta = decoLedge.getItemMeta();
            decoLedgeMeta.setDisplayName(" ");
            decoLedge.setItemMeta(decoLedgeMeta);

            ItemStack uhc = new ItemStack(Material.GOLDEN_APPLE);
            ItemMeta uhcMeta = uhc.getItemMeta();
            uhcMeta.setDisplayName("§6§lHost");
            uhc.setItemMeta(uhcMeta);

            ItemStack survie = new ItemStack(Material.BEACON);
            ItemMeta survieMeta = survie.getItemMeta();
            survieMeta.setDisplayName("§b§l???");
            survie.setItemMeta(survieMeta);

            ItemStack pvp = new ItemStack(Material.DIAMOND_SWORD);
            ItemMeta pvpMeta = pvp.getItemMeta();
            pvpMeta.setDisplayName("§4§lArena PvP");
            pvp.setItemMeta(pvpMeta);

            // Menu Inventory display
            invMenu.setItem(22, uhc);
            invMenu.setItem(33, survie);
            invMenu.setItem(29, pvp);
            invMenu.setItem(0, decoLedge);
            invMenu.setItem(1, decoLedge);
            invMenu.setItem(7, decoLedge);
            invMenu.setItem(8, decoLedge);
            invMenu.setItem(9, decoLedge);
            invMenu.setItem(17, decoLedge);
            invMenu.setItem(36, decoLedge);
            invMenu.setItem(44, decoLedge);
            invMenu.setItem(45, decoLedge);
            invMenu.setItem(46, decoLedge);
            invMenu.setItem(52, decoLedge);
            invMenu.setItem(53, decoLedge);
            player.openInventory(invMenu);
        }

        // Shop Item
        if(it.getType() == Material.GOLD_INGOT && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("§e§lBoutique")){
            player.sendMessage("§3§lLa boutique n'est pas encore disponible...");
        }

        // Hub Item
        if(it.getType() == Material.BEACON && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("§3§lHub")){
            Inventory invHub = Bukkit.createInventory(null, 9, "Sélection de Hub");
            ItemStack hub1 = new ItemStack(Material.BEACON);
            ItemMeta hub1Meta = hub1.getItemMeta();
            hub1Meta.setDisplayName("§3§lHub 1");
            hub1Meta.setLore(Arrays.asList("§cDéjà connecté !"));
            hub1.setItemMeta(hub1Meta);
            invHub.setItem(4, hub1);
            player.openInventory(invHub);
        }

        return null;
    }


    // Inventory Click Event
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        event.setCancelled(true);

        Inventory inv = event.getClickedInventory();
        if (inv == null) return;

        ItemStack clickedItem = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();

        if (clickedItem != null && clickedItem.getType() == Material.INK_SACK && clickedItem.hasItemMeta() && clickedItem.getItemMeta().hasDisplayName() && clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§3§nPronoms :")) {
            int clickedSlot = event.getRawSlot();
            Inventory invPronoms = Bukkit.createInventory(null, 9, "§8§lSélection des pronoms");
            // Elle
            ItemStack she = new ItemStack(Material.INK_SACK, 1, (byte)9);
            ItemMeta sheMeta = she.getItemMeta();
            sheMeta.setDisplayName("§d§lShe / Elle");
            she.setItemMeta(sheMeta);
            // Il
            ItemStack he = new ItemStack(Material.INK_SACK, 1, (byte)12);
            ItemMeta heMeta = he.getItemMeta();
            heMeta.setDisplayName("§3§lHe / Il");
            he.setItemMeta(heMeta);
            // None
            ItemStack none = new ItemStack(Material.INK_SACK, 1, (byte)8);
            ItemMeta noneMeta = none.getItemMeta();
            noneMeta.setDisplayName("§7§lNe pas afficher");
            none.setItemMeta(noneMeta);
            // Deco Vitres
            ItemStack decoLedge = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte)9);
            ItemMeta decoLedgeMeta = decoLedge.getItemMeta();
            decoLedgeMeta.setDisplayName(" ");
            decoLedge.setItemMeta(decoLedgeMeta);

            // Pronoms Inventory
            invPronoms.setItem(0, decoLedge);
            invPronoms.setItem(3, she);
            invPronoms.setItem(4, he);
            invPronoms.setItem(5, none);
            invPronoms.setItem(8, decoLedge);

            player.openInventory(invPronoms);

        }
        // Click She / Elle
        if (clickedItem != null && clickedItem.getType() == Material.INK_SACK && clickedItem.hasItemMeta() && clickedItem.getItemMeta().hasDisplayName() && clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§d§lShe / Elle")) {
            player.sendMessage("§lPronoms actuel : §d§lShe / Elle");
            LuckPerms luckPerms = LuckPermsProvider.get();
            UserManager userManager = luckPerms.getUserManager();
            User user = userManager.getUser(player.getUniqueId());

            if (user != null) {
                user.data().add(InheritanceNode.builder("she").build());
                user.data().remove(InheritanceNode.builder("him").build());
                luckPerms.getUserManager().saveUser(user);
            }
            player.closeInventory();
        }
        // Click He / Il
        if (clickedItem != null && clickedItem.getType() == Material.INK_SACK && clickedItem.hasItemMeta() && clickedItem.getItemMeta().hasDisplayName() && clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§3§lHe / Il")) {
            player.sendMessage("§lPronoms actuel : §b§lHe / Il");
            LuckPerms luckPerms = LuckPermsProvider.get();
            UserManager userManager = luckPerms.getUserManager();
            User user = userManager.getUser(player.getUniqueId());

            if (user != null) {
                user.data().add(InheritanceNode.builder("him").build());
                user.data().remove(InheritanceNode.builder("she").build());
                luckPerms.getUserManager().saveUser(user);
            }
            player.closeInventory();
        }
        // Pas de pronoms
        if (clickedItem != null && clickedItem.getType() == Material.INK_SACK && clickedItem.hasItemMeta() && clickedItem.getItemMeta().hasDisplayName() && clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§7§lNe pas afficher")) {
            player.sendMessage("§lVous n'affichez plus vos pronoms");
            LuckPerms luckPerms = LuckPermsProvider.get();
            UserManager userManager = luckPerms.getUserManager();
            User user = userManager.getUser(player.getUniqueId());

            if (user != null) {
                user.data().remove(InheritanceNode.builder("him").build());
                user.data().remove(InheritanceNode.builder("she").build());
                luckPerms.getUserManager().saveUser(user);
            }
            player.closeInventory();
        }
        if (clickedItem != null && clickedItem.getType() == Material.DIAMOND_SWORD && clickedItem.hasItemMeta() && clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§4§lArena PvP")) {
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Connect");
            out.writeUTF("arena");

            player.sendPluginMessage(Bukkit.getPluginManager().getPlugin("CholicubCore"), "BungeeCord", out.toByteArray());
            player.closeInventory();
        }
        if (clickedItem != null && clickedItem.getType() == Material.BEACON && clickedItem.hasItemMeta() && clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§b§l???")) {
            player.closeInventory();
            player.sendMessage("§b§lSoon...");
        }

            if (clickedItem != null && clickedItem.getType() == Material.GOLDEN_APPLE && clickedItem.hasItemMeta() && clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§6§lHost")) {
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Connect");
            out.writeUTF("host");

            player.sendPluginMessage(Bukkit.getPluginManager().getPlugin("CholicubCore"), "BungeeCord", out.toByteArray());
            player.closeInventory();
        }

        if (clickedItem != null && clickedItem.getType() == Material.PRISMARINE_SHARD && clickedItem.hasItemMeta() && clickedItem.getItemMeta().hasDisplayName() && clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§3§lTwitter")) {
            player.sendMessage("§3§lTwitter : §b§nhttps://x.com/shyybi_");
            player.closeInventory();
        }

    }


    // Inventory Drop Item
    @EventHandler
    public void onDropItem(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }

    // Inventory Drag item
    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        Inventory inv = event.getInventory();
        HumanEntity entity = event.getWhoClicked();
        if (entity instanceof Player) {
            Player player = (Player) entity;
            if (inv != null && inv.getName().equals("§8§lProfil de " + player.getName())) {
                event.setCancelled(true);
            }
            if (inv != null && inv.getName().equals("§7§lMenu")) {
                event.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onAchievement(PlayerAchievementAwardedEvent event) {
        event.setCancelled(true);
    }
    // FallDamage
    @EventHandler(priority = EventPriority.HIGH)
    public void onFallDamage(EntityDamageEvent event){
        if(event.getEntity() instanceof Player && event.getCause() == DamageCause.FALL)
            event.setCancelled(true);
    }

    // FoodLevelChange
    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event){
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location location = player.getLocation();

        if (location.getY() < 90) {
            Location targetLocation = new Location(Bukkit.getWorld("world"), -0.5, 100, -12.5, 180, 0);
            player.teleport(targetLocation);
            player.sendMessage(ChatColor.GREEN + "Ne vas pas si loin !");
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);
    }
}


