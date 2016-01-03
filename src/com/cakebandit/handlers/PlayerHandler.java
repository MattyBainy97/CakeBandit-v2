package com.cakebandit.handlers;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerHandler {

    public static Player bandit;
    public static int cakecount;
    public static boolean isCaught;
    public static ArrayList<UUID> players = new ArrayList<UUID>();
    public static ArrayList<UUID> alive = new ArrayList<UUID>();
    public static ArrayList<UUID> untested = new ArrayList<UUID>();
    public static ArrayList<UUID> tested = new ArrayList<UUID>();
    public static ArrayList<UUID> spec = new ArrayList<UUID>();

    public static void addPlayer(Player p) {
        
        players.add(p.getUniqueId());
        
    }
    
    public static void addAlive(Player p) {
        
        alive.add(p.getUniqueId());
        CakeSB.addUntested(p);
        
    }
    
    public static void addUntested(Player p) {
        
        untested.add(p.getUniqueId());
        CakeSB.addUntested(p);
        
    }
    
    public static void addTested(Player p) {
        
        tested.add(p.getUniqueId());
        CakeSB.addTested(p);
        
    }
    
    public static void addSpec(Player p) {
        
        spec.add(p.getUniqueId());
        CakeSB.addUntested(p);
        
    }

    public static void removePlayer(Player p) {
            
        players.remove(p.getUniqueId());
        
    }
    
    public static void removeAlive(Player p) {
        
        alive.remove(p.getUniqueId());
        
    }
    
    public static void removeUntested(Player p) {
        
        untested.remove(p.getUniqueId());
        
    }
    
    public static void removeTested(Player p) {
        
        tested.remove(p.getUniqueId());
        
    }
    
    public static void removeSpec(Player p) {
        
        spec.remove(p.getUniqueId());
        
    }
    
    public static void chooseBandit(){
        
        bandit = Bukkit.getPlayer(players.get(new Random().nextInt(players.size())));
        
    }
    
    public static Player getBandit(){
        
        return bandit;
        
    }
    
    public static void clearAll(){
        
        alive.clear();
        players.clear();
        tested.clear();
        spec.clear();
        bandit = null;
        
    }
    
    public static void initializeCake(){
        
        cakecount = 9;
        
    }
    
    public static void setCaught(boolean caught){
        
        isCaught = caught;
        
    }
    
    public static void eatCake(){
        
        cakecount--;
        CakeSB.setCake(cakecount);
        
    }
    
}
