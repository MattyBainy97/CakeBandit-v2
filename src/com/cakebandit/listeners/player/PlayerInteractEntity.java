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
                ChatUtilities.onePlayer(ChatColor.GOLD + "You accused " + ChatColor.DARK_AQUA + rightclick.getName() + ChatColor.GOLD + "!", ie.getPlayer());
                if (rightclick == PlayerHandler.bandit) {

                    PlayerHandler.removeUntested(PlayerHandler.bandit);
                    PlayerHandler.addTested(PlayerHandler.bandit);
                    PlayerHandler.setCaught(true);

                    Player msg = ie.getPlayer();
                    PlayerHandler.bandit.getInventory().setHelmet(CBItem.red);

                    Database.openConnection();
                    Database.updateCbTable(msg, "points", Database.getCb(msg, "points") + 10);
                    Database.updateCbTable(msg, "discovered", Database.getCb(msg, "discovered") + 1);
                    Database.closeConnection();

                    ChatUtilities.oneSubTitle(ChatColor.GOLD + "You revealed the " + ChatColor.RED + "BANDIT" + ChatColor.GOLD + " and gained" + ChatColor.GREEN + " 10 " + ChatColor.GOLD + "points!", msg);
                    ChatUtilities.showTitle(ChatColor.RED + PlayerHandler.bandit.getName() + ChatColor.GOLD + " is the " + ChatColor.RED + "BANDIT" + ChatColor.GOLD + "!");
                } else {
                    PlayerHandler.addTested(rightclick);
                    PlayerHandler.removeUntested(rightclick);
                    
                    rightclick.getInventory().setHelmet(CBItem.green);
                    CakeBandit.plugin.getServer().getScheduler().scheduleAsyncDelayedTask(CakeBandit.plugin, new Runnable() {
                        @Override
                        public void run() {
                            Player player = ie.getPlayer();
                            for (Player everyone : Bukkit.getOnlinePlayers()) {
                                everyone.hidePlayer(player);
                            }
                            ChatUtilities.oneSubTitle(ChatColor.GOLD + "You lost" + ChatColor.RED + " 10 " + ChatColor.GOLD + "points for accusing " + ChatColor.GREEN + rightclick.getName() + ChatColor.GOLD + "!", player);
                            ChatUtilities.broadcast(ChatColor.GREEN + player.getName() + ChatColor.GOLD + " was removed for incorrectly accusing " + ChatColor.GREEN + rightclick.getName() + ChatColor.GOLD + "!");
                            Database.openConnection();
                            Database.updateCbTable(player, "points", Database.getCb(player, "points") - 10);
                            Database.updateCbTable(player, "deaths", Database.getCb(player, "deaths") + 1);
                            Database.closeConnection();
                            
                            player.setGameMode(GameMode.ADVENTURE);
                            player.setAllowFlight(true);
                            player.setFlying(true);
                            player.getInventory().clear();
                            player.teleport(LocationUtilities.spawns[0]);
                            
                            PlayerHandler.addSpec(player);
                            PlayerHandler.removeAlive(player);
                        }
                    }, 20L);
                }
            }
        }
        if (PlayerHandler.tested.contains(rightclick.getUniqueId()) && rightclick != PlayerHandler.bandit) {
            Player msg = ie.getPlayer();
            ChatUtilities.onePlayer(ChatColor.GREEN + rightclick.getName() + ChatColor.GOLD + " is a " + ChatColor.GREEN + "CITIZEN" + ChatColor.GOLD + "!", msg);
        }

    }
}
