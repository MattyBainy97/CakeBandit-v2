package com.cakebandit.handlers;

import com.cakebandit.GameState;
import com.cakebandit.CakeBandit;
import com.cakebandit.threads.GameTimer;
import com.cakebandit.threads.ReloadTimer;
import com.cakebandit.utils.ChatUtilities;
import com.cakebandit.utils.LocationUtilities;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Game {

    public static String currentMap = "";
    
    private static boolean canStart = false;
    private static boolean hasStarted = false;

    public static void intro() {
        //planned
    }

    public static void start() {

        CakeBandit.plugin.getServer().getScheduler().scheduleSyncDelayedTask(CakeBandit.plugin, new Runnable() {
            @Override
            public void run() {

                new Thread(new GameTimer()).start();
                GameState.setState(GameState.IN_GAME);

                if (PlayerHandler.forceb == false) {
                    PlayerHandler.chooseBandit();
                }
                CBItem.setMetas();

                for (Player p : Bukkit.getOnlinePlayers()) {

                    p.setHealth(20.0);
                    p.setFoodLevel(40);
                    p.setLevel(0);
                    p.setExp(0);
                    CakeSB.showScoreboard();
                    CakeSB.setHealth(p);
                    CakeSB.setCake(9);
                    PlayerHandler.addAlive(p);
                    PlayerHandler.addUntested(p);
                    p.setGameMode(GameMode.ADVENTURE);

                    Database.openConnection();
                    Database.updateCbTable(p, "games", Database.getCb(p, "games") + 1);
                    Database.closeConnection();

                    if (p != PlayerHandler.getBandit()) {
                        p.getInventory().clear();
                        p.getInventory().addItem(CBItem.stick);
                        ChatUtilities.onePlayer(ChatColor.GOLD + "You are a " + ChatColor.GREEN + "CITIZEN" + ChatColor.GOLD + "!", p);
                        ChatUtilities.onePlayer(ChatColor.GOLD + "Your job is to find out who the Bandit is and capture them before they eat all the cakes!", p);
                        ChatUtilities.onePlayer(ChatColor.GOLD + "Right click on a player with your stick to accuse them!", p);
                        ChatUtilities.onePlayer(ChatColor.GOLD + "If you falsely accuse though, you will be removed from the game!", p);
                    }

                }
                PlayerHandler.bandit.getInventory().clear();
                PlayerHandler.bandit.getInventory().addItem(CBItem.bstick);
                ChatUtilities.onePlayer(ChatColor.GOLD + "You are the " + ChatColor.RED + "BANDIT" + ChatColor.GOLD + "!", PlayerHandler.bandit);
                ChatUtilities.onePlayer(ChatColor.GOLD + "Your job is to eat all 9 cakes without the citizens noticing you!", PlayerHandler.bandit);

                ChatUtilities.broadcast(ChatColor.YELLOW + "10 " + ChatColor.GOLD + "minutes left!");
                PlayerHandler.isCaught = false;
                PlayerHandler.initializeCake();
                LocationUtilities.spawnCakes();
                LocationUtilities.teleportToGame();

            }
        }, 5L);

        hasStarted = true;

    }

    public static void stop() {

        for (final Location l : LocationUtilities.cakes) {

            FireworkHandler.newFirework(l);
            CakeBandit.plugin.getServer().getScheduler().scheduleSyncDelayedTask(CakeBandit.plugin, new Runnable() {
                @Override
                public void run() {

                    FireworkHandler.newFirework(l);

                }
            }, 20L);
            CakeBandit.plugin.getServer().getScheduler().scheduleSyncDelayedTask(CakeBandit.plugin, new Runnable() {
                @Override
                public void run() {

                    FireworkHandler.newFirework(l);

                }
            }, 40L);

        }

        GameState.setState(GameState.POST_GAME);
        new Thread(new ReloadTimer()).start();
        canStart = false;
        hasStarted = false;

    }

    public static boolean canStart() {

        return canStart;

    }

    public static boolean hasStarted() {

        return hasStarted;

    }

    public static void setCanStart(boolean b) {

        canStart = b;

    }
}
