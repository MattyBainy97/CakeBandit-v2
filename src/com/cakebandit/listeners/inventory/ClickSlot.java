package com.cakebandit.listeners.inventory;

import com.cakebandit.GameState;
import com.cakebandit.CakeBandit;
import com.cakebandit.handlers.PlayerHandler;
import com.cakebandit.listeners.CBListener;
import com.cakebandit.utils.ChatUtilities;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

public class ClickSlot extends CBListener {

    public ClickSlot(CakeBandit pl) {
        super(pl);
    }

    @EventHandler
    public void onClickSlot(InventoryClickEvent e) {
        if (!GameState.isState(GameState.IN_LOBBY)) {
            if (e.getSlot() >= 0) {
                Player p = (Player) e.getWhoClicked();
                Player tp = Bukkit.getPlayer(e.getCurrentItem().getItemMeta().getDisplayName());
                if(PlayerHandler.spec.contains(p.getUniqueId())){
                    
                    p.teleport(tp);
                    
                }
                e.setResult(Event.Result.DENY);
                e.setCancelled(true);
            }
            if (e.getSlotType() == InventoryType.SlotType.ARMOR) {
                e.setResult(Event.Result.DENY);
                e.setCancelled(true);
            }
        }
    }
}
