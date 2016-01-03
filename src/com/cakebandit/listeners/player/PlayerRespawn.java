package com.cakebandit.listeners.player;

import com.cakebandit.CakeBandit;
import com.cakebandit.handlers.CBItem;
import com.cakebandit.handlers.PlayerHandler;
import com.cakebandit.listeners.CBListener;
import com.cakebandit.utils.ChatUtilities;
import com.cakebandit.utils.LocationUtilities;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawn extends CBListener{
    
    public PlayerRespawn(CakeBandit pl){
        
        super(pl);
        
    }

    @EventHandler
    public void onPlayerRespawnEvent(final PlayerRespawnEvent r) {
        if (PlayerHandler.isCaught == true || r.getPlayer() == PlayerHandler.bandit) {
            Player player = r.getPlayer();
            for (Player everyone : Bukkit.getOnlinePlayers()) {
                everyone.hidePlayer(player);
            }
            ChatUtilities.onePlayer(ChatColor.GOLD + "You are now a spectator!", player);
            player.setGameMode(GameMode.ADVENTURE);
            player.setAllowFlight(true);
            player.setFlying(true);
            player.getInventory().clear();
            player.getInventory().addItem(CBItem.spec);
            r.setRespawnLocation(LocationUtilities.spawns[0]);
        } else {
            r.setRespawnLocation(LocationUtilities.lobby);
        }
    }
}
