package com.cakebandit;

import java.sql.Connection;
import java.sql.SQLException;
import org.bukkit.plugin.java.JavaPlugin;
import com.cakebandit.handlers.Database;
import com.cakebandit.handlers.Game;
import com.cakebandit.handlers.CakeSB;
import com.cakebandit.handlers.CBItem;
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
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class CakeBandit extends JavaPlugin{
    
    public static Plugin plugin;
    
    @Override
    public void onEnable(){
        
        plugin = this;
        
        for(Player p : Bukkit.getOnlinePlayers()){
            
            p.kickPlayer(ChatColor.GREEN + "Reloading. Rejoin.");
            
        }
        
        for (World w : Bukkit.getServer().getWorlds()) {
            
            w.setTime(0);
            w.setStorm(false);
            w.setWeatherDuration(9999999);
            for (Entity e : w.getEntities()) {
                e.remove();
            }
            
        }
        
        GameState.setState(GameState.IN_LOBBY);
        Game.setCanStart(false);
        //new Thread(new StartCountdown()).start();
        CakeSB.initializeScoreboard();
        LocationUtilities.initializeSpawns();
        registerListeners();
        CBItem.setMetas();
        
    }
    
    @Override
    public void onDisable(){
        
        plugin = null;
        
        CakeSB.unregisterTeams();
        PlayerHandler.clearAll();
        
        try{
            
            Connection c = Database.getConnection();
            if(c != null && c.isClosed()){
                
                c.close();
                
            }
            
        }catch(SQLException e){
            
            e.printStackTrace();
            
        }
        
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        
        if (commandLabel.equalsIgnoreCase("start")) {
            
            Player p = (Player) sender;
            
            if (args.length == 0) {
            
                Game.start();
                
            }else{
                
                ChatUtilities.onePlayer("Wrong use of this command!", p);
                
            }
            
        }
        
        if (commandLabel.equalsIgnoreCase("records")) {
            
            Player p = (Player) sender;
            
            if (args.length == 0) {
            
                ChatUtilities.records((Player) sender);
                
            }else{
                
                ChatUtilities.onePlayer("Wrong use of this command!", p);
                
            }
            
        }
        
        return false;
        
    }
    
    public void registerListeners(){
        
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
