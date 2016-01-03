package com.cakebandit.listeners.player;

import com.cakebandit.CakeBandit;
import com.cakebandit.listeners.CBListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class PlayerRegen extends CBListener{

    public PlayerRegen(CakeBandit pl){
        
        super(pl);
        
    }
    
    @EventHandler
    public void onPlayerRegainHealthEvent(EntityRegainHealthEvent event) {
        if (event.getRegainReason() == EntityRegainHealthEvent.RegainReason.SATIATED || event.getRegainReason() == EntityRegainHealthEvent.RegainReason.REGEN) {
            event.setCancelled(true);
        }
    }
}
