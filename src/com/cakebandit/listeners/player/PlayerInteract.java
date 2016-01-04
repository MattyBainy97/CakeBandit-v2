package com.cakebandit.listeners.player;

import com.cakebandit.CakeBandit;
import com.cakebandit.GameState;
import com.cakebandit.handlers.CBItem;
import com.cakebandit.handlers.Database;
import com.cakebandit.handlers.Game;
import com.cakebandit.handlers.PlayerHandler;
import com.cakebandit.listeners.CBListener;
import com.cakebandit.utils.ChatUtilities;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerInteract extends CBListener {

    public PlayerInteract(CakeBandit pl) {
        super(pl);
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent i) {

        if (i.getAction() == Action.RIGHT_CLICK_AIR) {

            if (i.getPlayer().getItemInHand().equals(CBItem.spec)) {

                Inventory inv = Bukkit.createInventory(null, 9, "Spectate Players");

                for (int j = 0; j < PlayerHandler.alive.size(); j++) {
                    String playerName = Bukkit.getPlayer(PlayerHandler.alive.get(j)).getName();
                    ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
                    SkullMeta meta = (SkullMeta) skull.getItemMeta();
                    meta.setOwner(playerName);
                    meta.setDisplayName(playerName);
                    skull.setItemMeta(meta);
                    inv.setItem(j, skull);
                }

                i.getPlayer().openInventory(inv);

            }
        }
        
        if (i.getAction() == Action.RIGHT_CLICK_BLOCK) {

            if (i.getPlayer().getItemInHand().equals(CBItem.spec)) {

                Inventory inv = Bukkit.createInventory(null, 9, "Spectate Players");

                for (int j = 0; j < PlayerHandler.alive.size(); j++) {
                    String playerName = Bukkit.getPlayer(PlayerHandler.alive.get(j)).getName();
                    ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
                    SkullMeta meta = (SkullMeta) skull.getItemMeta();
                    meta.setOwner(playerName);
                    meta.setDisplayName(playerName);
                    skull.setItemMeta(meta);
                    inv.setItem(j, skull);
                }

                i.getPlayer().openInventory(inv);

            }

            if (i.getClickedBlock().getType() == Material.CAKE_BLOCK && i.getPlayer() == PlayerHandler.bandit && GameState.isState(GameState.IN_GAME)) {

                i.getClickedBlock().setType(Material.AIR);
                PlayerHandler.eatCake();
                PlayerHandler.bandit.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 120, 1));
                if (PlayerHandler.cakecount != 1) {
                    ChatUtilities.onePlayer(ChatColor.RED + "" + PlayerHandler.cakecount + ChatColor.GOLD + " cakes left!", i.getPlayer());
                } else {
                    ChatUtilities.onePlayer(ChatColor.RED + "" + PlayerHandler.cakecount + ChatColor.GOLD + " cake left!", i.getPlayer());
                }
                ChatUtilities.onePlayer(ChatColor.GOLD + "You get" + ChatColor.GREEN + " 5 " + ChatColor.GOLD + "points for eating a cake!", i.getPlayer());

                Database.openConnection();
                Database.updateCbTable(i.getPlayer(), "points", Database.getCb(i.getPlayer(), "points") + 5);
                Database.updateCbTable(i.getPlayer(), "eaten", Database.getCb(i.getPlayer(), "eaten") + 1);
                Database.closeConnection();

                if (PlayerHandler.cakecount == 0) {

                    ChatUtilities.broadcast(ChatColor.GOLD + "The " + ChatColor.RED + "BANDIT " + ChatColor.GOLD + "has eaten all of the cakes!");
                    ChatUtilities.broadcast(ChatColor.RED + PlayerHandler.bandit.getName() + ChatColor.GOLD + " gains" + ChatColor.GREEN + " 20 " + ChatColor.GOLD + "points for winning!");
                    Database.openConnection();
                    Database.updateCbTable(i.getPlayer(), "points", Database.getCb(i.getPlayer(), "points") + 20);
                    Database.updateCbTable(i.getPlayer(), "wins", Database.getCb(i.getPlayer(), "wins") + 1);
                    Database.closeConnection();

                    Game.stop();

                }

            }

        }

    }
}