package com.cakebandit.listeners.player;

import com.cakebandit.CakeBandit;
import com.cakebandit.listeners.CBListener;
import com.cakebandit.utils.ChatUtilities;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerChatEvent;

public class OnChat extends CBListener{
    
    public OnChat(CakeBandit pl){
        
        super(pl);
        
    }
    
    @EventHandler
    public void onChatEvent(PlayerChatEvent pc) {
        
        String msg = pc.getMessage();
        pc.setCancelled(true);
        ChatUtilities.chat(msg, pc.getPlayer());
        
    }
    
}
