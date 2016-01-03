package com.cakebandit.listeners.player;

import com.cakebandit.CakeBandit;
import com.cakebandit.handlers.Database;
import com.cakebandit.handlers.Game;
import com.cakebandit.handlers.PlayerHandler;
import com.cakebandit.listeners.CBListener;
import com.cakebandit.utils.ChatUtilities;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
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
                ChatUtilities.oneTitle(ChatColor.GOLD + "You ate a cake! " + ChatColor.RED + PlayerHandler.cakecount + ChatColor.GOLD + " cakes remain!", i.getPlayer());
                ChatUtilities.oneSubTitle(ChatColor.GOLD + "You get" + ChatColor.GREEN + " 5 " + ChatColor.GOLD + "points for eating a cake!", i.getPlayer());
                
                Database.openConnection();
                Database.updateCbTable(i.getPlayer(), "points", Database.getCb(i.getPlayer(), "points") + 5);
                Database.updateCbTable(i.getPlayer(), "eaten", Database.getCb(i.getPlayer(), "eaten") + 1);
                Database.closeConnection();

                if (PlayerHandler.cakecount == 0) {

                    Game.stop();

                }
                
            }
            
        }
        
    }
    
}