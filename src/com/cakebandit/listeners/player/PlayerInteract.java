package com.cakebandit.listeners.player;

import com.cakebandit.CakeBandit;
import com.cakebandit.handlers.Database;
import com.cakebandit.handlers.Game;
import com.cakebandit.handlers.PlayerHandler;
import com.cakebandit.listeners.CBListener;
import com.cakebandit.utils.ChatUtilities;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteract extends CBListener {

    public PlayerInteract(CakeBandit pl) {
        super(pl);
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent i) {

        if (i.getAction() == Action.RIGHT_CLICK_BLOCK) {

            if (i.getClickedBlock().getType() == Material.CAKE_BLOCK && i.getPlayer() == PlayerHandler.bandit) {

                i.getClickedBlock().setType(Material.AIR);
                PlayerHandler.eatCake();
                if(PlayerHandler.cakecount != 1){
                    ChatUtilities.oneTitle(ChatColor.RED + "" + PlayerHandler.cakecount + ChatColor.GOLD + " cakes left!", i.getPlayer());
                }else{
                    ChatUtilities.oneTitle(ChatColor.RED + "" + PlayerHandler.cakecount + ChatColor.GOLD + " cake left!", i.getPlayer());
                }
                ChatUtilities.oneSubTitle(ChatColor.GOLD + "You get" + ChatColor.GREEN + " 5 " + ChatColor.GOLD + "points for eating a cake!", i.getPlayer());
                
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