package com.cakebandit.listeners.player;

import com.cakebandit.CakeBandit;
import com.cakebandit.handlers.CBItem;
import com.cakebandit.handlers.Database;
import com.cakebandit.handlers.PlayerHandler;
import com.cakebandit.listeners.CBListener;
import com.cakebandit.utils.ChatUtilities;
import com.cakebandit.utils.LocationUtilities;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class PlayerInteractEntity extends CBListener {

    public PlayerInteractEntity(CakeBandit pl) {
        super(pl);
    }

    @EventHandler
    public void onPlayerInteractEntity(final PlayerInteractEntityEvent ie) {

        final Player rightclick = (Player) ie.getRightClicked();
        if (rightclick instanceof Player && !PlayerHandler.tested.contains(rightclick.getUniqueId()) && PlayerHandler.isCaught == false) {
            if (ie.getPlayer().getItemInHand().equals(CBItem.stick)) {
                ChatUtilities.accusedPlayer(ie.getPlayer(), rightclick);
            }
        } else if (PlayerHandler.tested.contains(rightclick.getUniqueId()) && rightclick != PlayerHandler.bandit) {
            if (ie.getPlayer().getItemInHand().equals(CBItem.stick)) {
                Player msg = ie.getPlayer();
                ChatUtilities.onePlayer(ChatColor.GREEN + rightclick.getName() + ChatColor.GOLD + " is a " + ChatColor.GREEN + "CITIZEN" + ChatColor.GOLD + "!", msg);
            }
        }

    }
}
