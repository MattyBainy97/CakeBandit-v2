package com.cakebandit.threads;

import com.cakebandit.GameState;
import com.cakebandit.handlers.Database;
import com.cakebandit.handlers.Game;
import com.cakebandit.handlers.PlayerHandler;
import com.cakebandit.utils.ChatUtilities;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class GameTimer implements Runnable {

    private static int timeUntilEnd;

    @Override
    public void run() {
        while (true) {
            if (GameState.isState(GameState.IN_GAME)) {
                timeUntilEnd = 600;
                for (; timeUntilEnd >= 0; timeUntilEnd--) {

                    if (timeUntilEnd == 0) {
                        ChatUtilities.broadcast(ChatColor.GOLD + "The " + ChatColor.RED + "BANDIT " + ChatColor.GOLD + "didn't eat all the cakes!");
                        ChatUtilities.broadcast(ChatColor.GOLD + "All alive " + ChatColor.GREEN + "CITIZENS" + ChatColor.GOLD + " gain" + ChatColor.GREEN + " 20 " + ChatColor.GOLD + "points for winning!");
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            if (PlayerHandler.alive.contains(p.getUniqueId())) {
                                Database.openConnection();
                                Database.updateCbTable(p, "points", Database.getCb(p, "points") + 20);
                                Database.updateCbTable(p, "wins", Database.getCb(p, "wins") + 1);
                                Database.closeConnection();
                            }
                        }
                        Game.stop();
                        break;
                    }

                    if (GameState.isState(GameState.POST_GAME)) {
                        break;
                    }
                    
                    if (timeUntilEnd == 300) {
                        
                        ChatUtilities.broadcast(ChatColor.YELLOW + "5 " + ChatColor.GOLD + "minutes left!");
                                
                    }  else if (timeUntilEnd == 60) {

                        ChatUtilities.broadcast(ChatColor.YELLOW + "1 " + ChatColor.GOLD + "minute left!");

                    } else if (timeUntilEnd == 30 || (timeUntilEnd < 6 && timeUntilEnd < 1)) {

                        ChatUtilities.broadcast(ChatColor.YELLOW + "" + timeUntilEnd + ChatColor.GOLD + " seconds left!");

                    } else if (timeUntilEnd == 1) {

                        ChatUtilities.broadcast(ChatColor.YELLOW + "" + timeUntilEnd + ChatColor.GOLD + " second left!");

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
