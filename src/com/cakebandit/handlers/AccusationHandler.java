package com.cakebandit.handlers;

import java.util.HashMap;
import org.bukkit.entity.Player;

public class AccusationHandler {
    
    private static HashMap<Player, Player> accusations = new HashMap<Player, Player>();
    
    public static void newAccusation(Player accuser, Player accused){
        
        accusations.put(accuser, accused);
        
    }
    
    public static void removeAccusation(Player p){
        
        accusations.remove(p);
        
    }
    
    public static int getAccusationCount(Player p){
        
        int count = 0;
        
        for(Player key : accusations.keySet()){
            
            if(key == p){
                
                count++;
                
            }
            
        }
        
        return count;
        
    }
    
    public static Player getAccused(Player p){
        
        return accusations.get(p);
        
    }
    
    public static boolean hasAccusation(Player p){
        
        return accusations.containsKey(p);
        
    }
    
}
