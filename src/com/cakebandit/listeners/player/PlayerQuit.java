package com.cakebandit.listeners.player;

import com.cakebandit.GameState;
import com.cakebandit.CakeBandit;
import com.cakebandit.handlers.Database;
import com.cakebandit.handlers.Game;
import com.cakebandit.handlers.PlayerHandler;
import com.cakebandit.listeners.CBListener;
import com.cakebandit.utils.ChatUtilities;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit extends CBListener {

    public PlayerQuit(CakeBandit pl) {
        super(pl);
    }

    @EventHandler
    public void onPlayerLeaveEvent(PlayerQuitEvent q) {

        Player p = q.getPlayer();
        q.setQuitMessage("");
        PlayerHandler.removePlayer(p);

        if (GameState.isState(GameState.IN_LOBBY)) {

            Game.setCanStart(Bukkit.getOnlinePlayers().size() - 1 >= 2);
            
            if (q.getPlayer() == PlayerHandler.bandit) {
                
                Database.openConnection();
                Database.updatePasses(p, Database.getPasses(p) + 1);
                Database.closeConnection();
                
                PlayerHandler.bandit = null;
                PlayerHandler.forceb = false;
                
            }


        } else {
            
            PlayerHandler.removeAlive(p);
            PlayerHandler.removeUntested(p);

            if (q.getPlayer() == PlayerHandler.bandit) {
                
                ChatUtilities.broadcast(ChatColor.GOLD + "The " + ChatColor.RED + "BANDIT " + ChatColor.GOLD + "has left the game!");
                Game.stop();
                
            }

        }
        
    }
    
}
