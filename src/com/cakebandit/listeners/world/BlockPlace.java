package com.cakebandit.listeners.world;

import com.cakebandit.CakeBandit;
import com.cakebandit.listeners.CBListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlace extends CBListener{
    
    public BlockPlace(CakeBandit pl){
        
        super(pl);
        
    }
    
    @EventHandler
    public void onBlockPlaceEvent(BlockPlaceEvent p) {
        if (!p.getPlayer().isOp()) {
            p.setCancelled(true);
        }
    }
    
}
