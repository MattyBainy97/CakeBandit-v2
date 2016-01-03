package com.cakebandit.threads;

import com.cakebandit.GameState;
import com.cakebandit.handlers.Game;
import com.cakebandit.utils.ChatUtilities;
import org.bukkit.ChatColor;

public class GameTimer implements Runnable {

    private static int timeUntilEnd;
    
    @Override
    public void run() {
        while (true) {
            if (GameState.isState(GameState.IN_GAME)) {
                timeUntilEnd = 300;
                for (; timeUntilEnd >= 0; timeUntilEnd--) {

                    if (timeUntilEnd == 0) {
                        Game.stop();
                        break;
                    }

                    if(timeUntilEnd == 240){
                        
                        ChatUtilities.broadcast(ChatColor.YELLOW + "4 " + ChatColor.GOLD + "minutes left!");
                        
                    }else if(timeUntilEnd == 180){
                        
                        ChatUtilities.broadcast(ChatColor.YELLOW + "3 " + ChatColor.GOLD + "minutes left!");
                        
                    }else if(timeUntilEnd == 120){
                        
                        ChatUtilities.broadcast(ChatColor.YELLOW + "2 " + ChatColor.GOLD + "minutes left!");
                        
                    }else if(timeUntilEnd == 60){
                        
                        ChatUtilities.broadcast(ChatColor.YELLOW + "1 " + ChatColor.GOLD + "minute left!");
                        
                    }else if(timeUntilEnd == 30 || timeUntilEnd < 6){
                        
                        ChatUtilities.broadcast(ChatColor.YELLOW + "" + timeUntilEnd + ChatColor.GOLD + " seconds left!");
                        
                    }

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                }
                
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
    }
}
