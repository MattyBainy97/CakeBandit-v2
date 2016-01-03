package com.cakebandit.listeners.player;

import com.cakebandit.CakeBandit;
import com.cakebandit.handlers.CakeSB;
import com.cakebandit.handlers.PlayerHandler;
import com.cakebandit.listeners.CBListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerDamage extends CBListener {

    public PlayerDamage(CakeBandit pl) {
        
        super(pl);
        
    }
    
    @EventHandler
    public void onPlayerDamageEvent(EntityDamageEvent d) {
        Player p = (Player) d.getEntity();
        if (PlayerHandler.spec.contains(p.getUniqueId())) {
            d.setCancelled(true);
        }
        if (d.getCause() == EntityDamageEvent.DamageCause.FALL) {
            d.setCancelled(true);
        }
        if (PlayerHandler.isCaught == false) {
            if (d.getEntity() instanceof Player) {
                d.setCancelled(true);
            }
        }
    }
}
