package com.cakebandit.listeners.inventory;

import com.cakebandit.CakeBandit;
import com.cakebandit.handlers.AccusationHandler;
import com.cakebandit.listeners.CBListener;
import com.cakebandit.utils.ChatUtilities;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class CloseInventory extends CBListener {

    public CloseInventory(CakeBandit pl) {

        super(pl);

    }

    @EventHandler
    public static void onInventoryClose(InventoryCloseEvent e) {

        if (e.getInventory().getName().contains("Accuse")) {

            if (AccusationHandler.hasAccusation((Player) e.getPlayer())) {
                ChatUtilities.onePlayer(ChatColor.GOLD + "You cancelled the accusation on " + ChatColor.DARK_AQUA + AccusationHandler.getAccused((Player) e.getPlayer()).getName() + ChatColor.GOLD + "!", (Player) e.getPlayer());
                AccusationHandler.removeAccusation((Player) e.getPlayer());
            }

        }

    }
}
