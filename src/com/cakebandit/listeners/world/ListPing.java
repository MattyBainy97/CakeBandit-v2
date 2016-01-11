package com.cakebandit.listeners.world;

import com.cakebandit.GameState;
import com.cakebandit.CakeBandit;
import com.cakebandit.handlers.Game;
import com.cakebandit.listeners.CBListener;
import org.bukkit.ChatColor;
import static org.bukkit.ChatColor.AQUA;
import static org.bukkit.ChatColor.BLUE;
import static org.bukkit.ChatColor.GRAY;
import org.bukkit.event.EventHandler;
import org.bukkit.event.server.ServerListPingEvent;

public class ListPing extends CBListener{

    public ListPing(CakeBandit pl){
        
        super(pl);
        
    }
    
    @EventHandler
    public void onPing(ServerListPingEvent e) {
        if (GameState.isState(GameState.IN_LOBBY)) {

            e.setMotd(GRAY + "[" + AQUA + "Cake" + BLUE + "Bandit" + GRAY + "] " + ChatColor.GREEN + "LOBBY " + ChatColor.GOLD + "- " + Game.currentMap);
            
        } else if (GameState.isState(GameState.IN_GAME)) {
            
            e.setMotd(GRAY + "[" + AQUA + "Cake" + BLUE + "Bandit" + GRAY + "] " + ChatColor.DARK_RED + "IN PROGRESS " + ChatColor.GOLD + "- " + Game.currentMap);
            
        } else if (GameState.isState(GameState.POST_GAME)) {
            
            e.setMotd(GRAY + "[" + AQUA + "Cake" + BLUE + "Bandit" + GRAY + "] " + ChatColor.DARK_RED + "GAME ENDING");
            
        } else if (GameState.isState(GameState.RESET)) {
            
            e.setMotd(GRAY + "[" + AQUA + "Cake" + BLUE + "Bandit" + GRAY + "] " + ChatColor.DARK_RED + "SERVER RESTART");
            
        }
        
    }
    
}
