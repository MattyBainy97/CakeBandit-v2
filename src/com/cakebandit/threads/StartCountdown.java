package com.cakebandit.threads;

import com.cakebandit.GameState;
import com.cakebandit.handlers.Game;
import com.cakebandit.utils.ChatUtilities;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class StartCountdown implements Runnable {

    private static int timeUntilStart;
    
    @Override
    public void run() {
        while (true) {
            if (GameState.isState(GameState.IN_LOBBY)) {
                if (Game.canStart() == true) {
                    ChatUtilities.broadcast("Minimum players reached! Countdown starting!");
                    timeUntilStart = 60;
                    for (; timeUntilStart >= 0; timeUntilStart--) {
                        
                        for(Player p : Bukkit.getOnlinePlayers()){
                            p.setLevel(timeUntilStart);
                        }
                        
                        if(Game.canStart() == false){
                            ChatUtilities.broadcast("Not enough players! Countdown stopped!");
                            break;
                        }
                        if (timeUntilStart == 0) {
                            Game.start();
                            break;
                        }

                        if (timeUntilStart % 10 == 0 || timeUntilStart < 6) {
                            ChatUtilities.broadcast(ChatColor.YELLOW + "" + timeUntilStart + ChatColor.GOLD + " seconds until the game starts!");
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
}
