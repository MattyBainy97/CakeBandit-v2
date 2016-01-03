package com.cakebandit.listeners.player;

import com.cakebandit.GameState;
import com.cakebandit.CakeBandit;
import com.cakebandit.handlers.Database;
import com.cakebandit.handlers.CBItem;
import com.cakebandit.handlers.CakeSB;
import com.cakebandit.handlers.PlayerHandler;
import com.cakebandit.listeners.CBListener;
import com.cakebandit.utils.ChatUtilities;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerDamageByEntity extends CBListener {

    public static Player playerHit;

    public PlayerDamageByEntity(CakeBandit pl) {
        super(pl);
    }

    @EventHandler
    public void onPlayerDamageByEntity(EntityDamageByEntityEvent event) {

        if (GameState.isState(GameState.IN_GAME)) {
            
            Entity damaged = event.getEntity();
            Entity damageEntity = event.getDamager();

            if (damaged instanceof Player) {

                if (damageEntity instanceof Player) {

                    Player p = (Player) damageEntity;
                    
                    if (PlayerHandler.spec.contains(p.getUniqueId())) {
                        event.setCancelled(true);
                    }
                    
                    if (PlayerHandler.isCaught == true) {

                        if (p != PlayerHandler.bandit && (Player) damaged != PlayerHandler.bandit) {
                            
                            event.setCancelled(true);
                            
                        }
                        
                    } else {
                        
                        event.setCancelled(true);
                        
                    }

                } else {
                    
                    event.setCancelled(true);
                    
                }

            } else {

                event.setCancelled(true);

            }
        } else {

            event.setCancelled(true);

        }
    }
}
