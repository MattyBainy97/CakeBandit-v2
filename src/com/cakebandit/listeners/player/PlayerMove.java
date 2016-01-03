package com.cakebandit.listeners.player;

import com.cakebandit.CakeBandit;
import com.cakebandit.GameState;
import com.cakebandit.handlers.CakeSB;
import com.cakebandit.listeners.CBListener;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class PlayerMove extends CBListener {

    public PlayerMove(CakeBandit pl) {
        super(pl);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent m) {

        m.getPlayer().setFoodLevel(40);
        
        if(GameState.isState(GameState.IN_GAME)){
            
            CakeSB.setHealth(m.getPlayer());
            
        }
        
        if (m.getTo().getBlock().getRelative(BlockFace.DOWN).getType() == Material.REDSTONE_BLOCK) {
            m.getPlayer().setVelocity(m.getPlayer().getLocation().getDirection().multiply(10));
            m.getPlayer().setVelocity(new Vector(m.getPlayer().getVelocity().getX(), 2.0D, m.getPlayer().getVelocity().getZ()));
        }

    }
}
