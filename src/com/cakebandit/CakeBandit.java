package com.cakebandit;

import com.cakebandit.handlers.AccusationHandler;
import com.cakebandit.handlers.CBItem;
import java.sql.Connection;
import java.sql.SQLException;
import org.bukkit.plugin.java.JavaPlugin;
import com.cakebandit.handlers.Database;
import com.cakebandit.handlers.Game;
import com.cakebandit.handlers.CakeSB;
import com.cakebandit.handlers.PlayerHandler;
import com.cakebandit.listeners.inventory.ClickSlot;
import com.cakebandit.listeners.player.AsyncPlayerPreLogin;
import com.cakebandit.listeners.player.OnChat;
import com.cakebandit.listeners.player.PlayerDamage;
import com.cakebandit.listeners.player.PlayerDamageByEntity;
import com.cakebandit.listeners.player.PlayerDeath;
import com.cakebandit.listeners.player.PlayerDrop;
import com.cakebandit.listeners.player.PlayerInteract;
import com.cakebandit.listeners.player.PlayerInteractEntity;
import com.cakebandit.listeners.player.PlayerJoin;
import com.cakebandit.listeners.player.PlayerMove;
import com.cakebandit.listeners.player.PlayerQuit;
import com.cakebandit.listeners.player.PlayerRegen;
import com.cakebandit.listeners.player.PlayerRespawn;
import com.cakebandit.listeners.world.BlockBreak;
import com.cakebandit.listeners.world.BlockPlace;
import com.cakebandit.listeners.world.ListPing;
import com.cakebandit.threads.StartCountdown;
import com.cakebandit.utils.ChatUtilities;
import com.cakebandit.utils.LocationUtilities;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class CakeBandit extends JavaPlugin {

    public static Plugin plugin;

    @Override
    public void onEnable() {

        plugin = this;

        for (Player p : Bukkit.getOnlinePlayers()) {

            p.kickPlayer(ChatColor.GREEN + "CakeBandit is restarting! Rejoin to play another game!");

        }

        for (World w : Bukkit.getServer().getWorlds()) {

            w.setTime(0);
            w.setStorm(false);
            w.setWeatherDuration(9999999);
            for (Entity e : w.getEntities()) {
                e.remove();
            }

            if (w.getName().contains("Sword")) {
                LocationUtilities.initializeSwordSpawns();
            } else if (w.getName().contains("MountTraya")) {
                LocationUtilities.initializeMountTrayaSpawns();
            }

        }

        GameState.setState(GameState.IN_LOBBY);
        Game.setCanStart(false);
        new Thread(new StartCountdown()).start();
        CakeSB.initializeScoreboard();
        registerListeners();

    }

    @Override
    public void onDisable() {

        plugin = null;

        CakeSB.unregisterTeams();
        PlayerHandler.clearAll();

        try {

            Connection c = Database.getConnection();
            if (c != null && c.isClosed()) {

                c.close();

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if (commandLabel.equalsIgnoreCase("start")) {

            Player p = (Player) sender;

            if (args.length == 0) {

                if (GameState.isState(GameState.IN_LOBBY)) {

                    StartCountdown.forceStart = true;

                } else {

                    ChatUtilities.onePlayer("A game is currently running", p);

                }

            } else {

                ChatUtilities.onePlayer("Wrong use of this command!", p);

            }

        }

        if (commandLabel.equalsIgnoreCase("records")) {

            Player p = (Player) sender;

            if (args.length == 0) {

                ChatUtilities.records((Player) sender);

            } else {

                ChatUtilities.onePlayer("Wrong use of this command!", p);

            }

        }

        if (commandLabel.equalsIgnoreCase("passes")) {

            Player p = (Player) sender;
            if (args.length == 0) {

                Database.openConnection();
                ChatUtilities.onePlayer(ChatColor.GOLD + "You have " + ChatColor.GREEN + Database.getPasses(p) + ChatColor.GOLD + " passes remaining!", p);
                Database.closeConnection();

            } else {

                ChatUtilities.onePlayer("Wrong use of this command!", p);

            }

        }

        if (commandLabel.equalsIgnoreCase("pass")) {
            Player p = (Player) sender;
            Database.openConnection();
            if (p.isOp() == true) {
                if (args.length == 0) {
                    ChatUtilities.onePlayer("Wrong use of this command!", p);
                } else if (args.length == 1) {
                    ChatUtilities.onePlayer("Wrong use of this command!", p);
                } else if (args.length == 2) {
                    try {
                        Player targetPlayer = p.getServer().getPlayer(args[0]);
                        try {
                            int i = Integer.parseInt(args[1]);
                            if (i >= 0 && i <= 100) {
                                ChatUtilities.onePlayer(ChatColor.GOLD + "You have recieved " + ChatColor.GREEN + i + ChatColor.GOLD + " passes!", targetPlayer);
                                if (targetPlayer != p) {
                                    ChatUtilities.onePlayer(ChatColor.GOLD + "You have sent " + ChatColor.GREEN + i + ChatColor.GOLD + " passes to " + ChatColor.DARK_AQUA + targetPlayer.getName() + ChatColor.GOLD + "!", p);
                                }
                                Database.updatePasses(targetPlayer, Database.getPasses(targetPlayer) + i);
                            } else {
                                ChatUtilities.onePlayer(ChatColor.GOLD + "Enter a serious number... " + ChatColor.RED + i + ChatColor.GOLD + " is just too many...", p);
                            }
                        } catch (Exception e) {
                            ChatUtilities.onePlayer("Wrong use of this command!", p);
                        }

                    } catch (Exception e) {
                        ChatUtilities.onePlayer("Player is not online!", p);
                    }
                }
            }
            Database.closeConnection();
        }

        if (commandLabel.equalsIgnoreCase("accuse")) {
            final Player accuser = (Player) sender;
            if (AccusationHandler.hasAccusation(accuser) && PlayerHandler.isCaught == false) {
                final Player accused = AccusationHandler.getAccused(accuser);
                if (args.length == 1) {

                    ChatUtilities.onePlayer(ChatColor.GOLD + "You accused " + ChatColor.DARK_AQUA + accused.getName() + ChatColor.GOLD + "!", accuser);
                    if (accused == PlayerHandler.bandit) {

                        PlayerHandler.removeUntested(PlayerHandler.bandit);
                        PlayerHandler.addTested(PlayerHandler.bandit);
                        PlayerHandler.setCaught(true);

                        Player msg = accuser;
                        PlayerHandler.bandit.getInventory().setHelmet(CBItem.red);

                        Database.openConnection();
                        Database.updateCbTable(msg, "points", Database.getCb(msg, "points") + 10);
                        Database.updateCbTable(msg, "discovered", Database.getCb(msg, "discovered") + 1);
                        Database.closeConnection();
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            if (p == msg) {
                                ChatUtilities.onePlayer(ChatColor.GOLD + "You revealed the " + ChatColor.RED + "BANDIT" + ChatColor.GOLD + " and gained" + ChatColor.GREEN + " 10 " + ChatColor.GOLD + "points!", msg);
                            }
                        }
                        ChatUtilities.broadcast(ChatColor.RED + PlayerHandler.bandit.getName() + ChatColor.GOLD + " is the " + ChatColor.RED + "BANDIT" + ChatColor.GOLD + "!");
                    } else {
                        PlayerHandler.addTested(accused);
                        PlayerHandler.removeUntested(accused);

                        accused.getInventory().setHelmet(CBItem.green);
                        CakeBandit.plugin.getServer().getScheduler().scheduleAsyncDelayedTask(CakeBandit.plugin, new Runnable() {
                            @Override
                            public void run() {
                                Player player = accuser;
                                for (Player everyone : Bukkit.getOnlinePlayers()) {
                                    everyone.hidePlayer(player);
                                }
                                ChatUtilities.onePlayer(ChatColor.GOLD + "You lost" + ChatColor.RED + " 10 " + ChatColor.GOLD + "points for accusing " + ChatColor.GREEN + accused.getName() + ChatColor.GOLD + "!", player);
                                ChatUtilities.broadcast(ChatColor.GREEN + player.getName() + ChatColor.GOLD + " was removed for incorrectly accusing " + ChatColor.GREEN + accused.getName() + ChatColor.GOLD + "!");
                                Database.openConnection();
                                Database.updateCbTable(player, "points", Database.getCb(player, "points") - 10);
                                Database.updateCbTable(player, "deaths", Database.getCb(player, "deaths") + 1);
                                Database.closeConnection();

                                player.setGameMode(GameMode.ADVENTURE);
                                player.setAllowFlight(true);
                                player.setFlying(true);
                                player.getInventory().clear();
                                player.getInventory().addItem(CBItem.spec);
                                player.teleport(LocationUtilities.spawns[0]);

                                PlayerHandler.addSpec(player);
                                PlayerHandler.removeAlive(player);
                            }
                        }, 20L);
                    }
                    AccusationHandler.removeAccusation(accuser);
                } else if (args.length == 2) {

                    AccusationHandler.removeAccusation(accuser);
                    ChatUtilities.onePlayer(ChatColor.GOLD + "You cancelled the accusation on " + ChatColor.DARK_AQUA + accused.getName() + ChatColor.GOLD + "!", accuser);

                }
            }
        }

        if (commandLabel.equalsIgnoreCase("bandit")) {
            Player p = (Player) sender;
            if (args.length == 0 && GameState.isState(GameState.IN_LOBBY)) {
                Database.openConnection();
                int passnum = Database.getPasses(p);
                if (passnum > 1 && PlayerHandler.forceb == false) {
                    PlayerHandler.bandit = p;
                    PlayerHandler.forceb = true;
                    ChatUtilities.onePlayer(ChatColor.GOLD + "You will be the " + ChatColor.RED + "BANDIT " + ChatColor.GOLD + "this round!", p);
                    Database.updatePasses(p, Database.getPasses(p) - 1);
                    ChatUtilities.onePlayer(ChatColor.GOLD + "Taken" + ChatColor.RED + " 1 " + ChatColor.GOLD + "pass! You now have " + ChatColor.GREEN + Database.getPasses(p) + ChatColor.GOLD + "!", p);
                } else if (passnum > 1 && PlayerHandler.forceb == true) {
                    ChatUtilities.onePlayer(ChatColor.GOLD + "Someone has already used a pass!", p);
                } else if (passnum > -1 && PlayerHandler.forceb == true) {
                    ChatUtilities.onePlayer(ChatColor.GOLD + "You don't have any passes! Besides, someone's already used one!", p);
                } else if (passnum > -1 && PlayerHandler.forceb == false) {
                    ChatUtilities.onePlayer(ChatColor.GOLD + "You don't have any passes!", p);
                }
                Database.closeConnection();
            } else {

                ChatUtilities.onePlayer("You can't become the bandit during a game!", p);

            }

        }

        return false;

    }

    public void registerListeners() {

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerJoin(this), this);
        pm.registerEvents(new PlayerQuit(this), this);
        pm.registerEvents(new PlayerDamageByEntity(this), this);
        pm.registerEvents(new PlayerDamage(this), this);
        pm.registerEvents(new PlayerInteract(this), this);
        pm.registerEvents(new PlayerMove(this), this);
        pm.registerEvents(new PlayerInteractEntity(this), this);
        pm.registerEvents(new PlayerDrop(this), this);
        pm.registerEvents(new PlayerDeath(this), this);
        pm.registerEvents(new PlayerRespawn(this), this);
        pm.registerEvents(new PlayerRegen(this), this);
        pm.registerEvents(new ClickSlot(this), this);
        pm.registerEvents(new OnChat(this), this);
        pm.registerEvents(new AsyncPlayerPreLogin(this), this);
        pm.registerEvents(new ListPing(this), this);
        pm.registerEvents(new BlockBreak(this), this);
        pm.registerEvents(new BlockPlace(this), this);

    }
}
