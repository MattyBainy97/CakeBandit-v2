package com.cakebandit.listeners.world;

import com.cakebandit.CakeBandit;
import com.cakebandit.listeners.CBListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreak extends CBListener{
    
    public BlockBreak(CakeBandit pl){
        
        super(pl);
    }
    
    @EventHandler
    public void onBreakBlockEvent(BlockBreakEvent b) {
        if (!b.getPlayer().isOp()) {
            b.setCancelled(true);
        }
    }
    
}
