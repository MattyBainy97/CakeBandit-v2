package com.cakebandit.handlers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class CakeSB {
    
    private static Scoreboard gameBoard = null;
    private static Objective hearts = null;
    private static Objective points = null;
    private static Score cake = null;
    private static Score untest = null;
    private static Score test = null;
    private static Score health = null;
    private static Team bandit = null;
    private static Team tested = null;
    private static Team untested = null;
    
    public static void initializeScoreboard(){
        
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        gameBoard = manager.getNewScoreboard();
        
        hearts = gameBoard.registerNewObjective("health", "dummy");
        hearts.setDisplaySlot(DisplaySlot.BELOW_NAME);
        hearts.setDisplayName(ChatColor.RED + "❤");
        
        points = gameBoard.registerNewObjective("test", "dummy");
        points.setDisplaySlot(DisplaySlot.SIDEBAR);
        points.setDisplayName(ChatColor.AQUA + "Cake" + ChatColor.BLUE + "Bandit");
        
        untested = gameBoard.registerNewTeam("untested");
        untested.setPrefix(ChatColor.DARK_AQUA + "");

        tested = gameBoard.registerNewTeam("tested");
        tested.setPrefix(ChatColor.GREEN + "");
        
        bandit = gameBoard.registerNewTeam("bandit");
        bandit.setPrefix(ChatColor.RED + "");
        
    }
    
    public static void showScoreboard(){
        
        for(Player p : Bukkit.getOnlinePlayers()){
            
            p.setScoreboard(gameBoard);
            cake = points.getScore(Bukkit.getOfflinePlayer(ChatColor.GOLD + "Cakes Left:"));
            cake.setScore(PlayerHandler.cakecount);
            
            untest = points.getScore(Bukkit.getOfflinePlayer(ChatColor.BLUE + "Untested:"));
            untest.setScore(PlayerHandler.untested.size() - PlayerHandler.tested.size());
            
            test = points.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Tested:"));
            test.setScore(PlayerHandler.tested.size());
            
        }
        
    }
    
    public static void addUntested(Player player){
        
        untested.addPlayer(player);
        untest.setScore(PlayerHandler.untested.size() - PlayerHandler.tested.size());
        
    }
    
    public static void addTested(Player player){
        
        tested.addPlayer(player);
        test.setScore(PlayerHandler.tested.size());
        untest.setScore(PlayerHandler.untested.size() - PlayerHandler.tested.size());
        
    }
    
    public static void addBandit(Player player){
        
        bandit.addPlayer(player);
        untest.setScore(PlayerHandler.untested.size() - PlayerHandler.tested.size());
        test.setScore(PlayerHandler.tested.size());
        
    }
    
    public static void setHealth(Player p){
        
        health = hearts.getScore(p);
        health.setScore((int) p.getHealth() / 2);
        
    }
    
    public static void setCake(int cakecount){
        
        cake.setScore(cakecount);
        
    }
    
    public static void unregisterTeams(){
        
        bandit.unregister();
        tested.unregister();
        untested.unregister();
        
    }
    
}