package com.cakebandit.listeners.player;

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
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerDeath extends CBListener{
    
    public PlayerDeath(CakeBandit pl){
        
        super(pl);
        
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        
        e.getDrops().clear();
        e.setDeathMessage("");
        
        Player killed = (Player) e.getEntity();
        Player killer = (Player) e.getEntity().getKiller();
        
        PlayerHandler.addSpec(killed);
        PlayerHandler.removeAlive(killed);
        
        Database.openConnection();
        Database.updateCbTable(killed, "deaths", Database.getCb(killed, "deaths") + 1);
        Database.updateCbTable(killer, "kills", Database.getCb(killer, "kills") + 1);
        
        if ((Player) e.getEntity() == PlayerHandler.bandit) {
            
            ChatUtilities.broadcast(ChatColor.GREEN + e.getEntity().getKiller().getName() + " has killed " + ChatColor.RED + e.getEntity().getName() + ChatColor.GOLD + "!");
            ChatUtilities.oneSubTitle(ChatColor.GOLD + "You got" + ChatColor.GREEN + " 20 " + ChatColor.GOLD + "points for killing " + ChatColor.RED + e.getEntity().getName() + ChatColor.GOLD + "!", PlayerHandler.bandit);
            Database.updateCbTable(killer, "points", Database.getCb(killer, "points") + 20);
            Game.stop();

        } else if (e.getEntity().getKiller() == PlayerHandler.bandit) {
            
            ChatUtilities.broadcast(ChatColor.RED + PlayerHandler.bandit.getName() + " has killed " + ChatColor.GREEN + e.getEntity().getName() + ChatColor.GOLD + "!");
            PlayerHandler.bandit.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 80, 2));
            ChatUtilities.oneSubTitle(ChatColor.GOLD + "You got" + ChatColor.GREEN + " 5 " + ChatColor.GOLD + "points for killing " + ChatColor.GREEN + e.getEntity().getName() + ChatColor.GOLD + "!", PlayerHandler.bandit);
            Database.updateCbTable(killer, "points", Database.getCb(killer, "points") + 5);
            
            if(PlayerHandler.alive.size() == 1){
                
                Game.stop();
                
            }
            
        }
        
        Database.closeConnection();
        
    }
}
