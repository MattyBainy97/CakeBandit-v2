package com.cakebandit.listeners.player;

import com.cakebandit.CakeBandit;
import com.cakebandit.listeners.CBListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDrop extends CBListener{
    
    public PlayerDrop(CakeBandit pl){
        super(pl);
    }
    
    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }
    
}
