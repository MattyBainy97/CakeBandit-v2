package com.cakebandit.threads;

import com.cakebandit.GameState;
import com.cakebandit.handlers.Game;
import com.cakebandit.utils.ChatUtilities;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
public class ReloadTimer implements Runnable {

    private static int timeUntilEnd;
    
    @Override
    public void run() {
        while (true) {
            if (GameState.isState(GameState.POST_GAME)) {
                timeUntilEnd = 10;
                for (; timeUntilEnd >= 0; timeUntilEnd--) {

                    if (timeUntilEnd == 0) {
                        GameState.setState(GameState.RESET);
                        Bukkit.reload();
                        break;
                    }
                    
                    if(timeUntilEnd == 10 || timeUntilEnd < 4){
                        
                        ChatUtilities.broadcast(ChatColor.YELLOW + "" + timeUntilEnd + ChatColor.GOLD + " seconds till server restart!");
                        
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
