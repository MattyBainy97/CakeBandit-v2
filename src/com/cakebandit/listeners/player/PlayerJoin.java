package com.cakebandit.listeners.player;

import com.cakebandit.CakeBandit;
import com.cakebandit.GameState;
import com.cakebandit.GameState;
import com.cakebandit.handlers.Database;
import com.cakebandit.handlers.Game;
import com.cakebandit.handlers.CakeSB;
import com.cakebandit.handlers.CBItem;
import com.cakebandit.handlers.PlayerHandler;
import com.cakebandit.listeners.CBListener;
import com.cakebandit.utils.ChatUtilities;
import com.cakebandit.utils.LocationUtilities;
import java.lang.reflect.Field;
import java.util.UUID;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffectType;

public class PlayerJoin extends CBListener {

    public PlayerJoin(CakeBandit pl) {

        super(pl);

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoinEvent(PlayerJoinEvent e) {

        Database.openConnection();

        try {

            if (Database.playerTableContainsPlayer(e.getPlayer())) {

                Database.updateLastLogin(e.getPlayer());

            } else {

                Database.addPlayerToPlayerTable(e.getPlayer());

            }

            if (!Database.cbTableContainsPlayer(e.getPlayer())) {

                Database.addPlayerToCbTable(e.getPlayer());

            }

        } catch (Exception ex) {

            ex.printStackTrace();

        } finally {

            Database.closeConnection();

        }

        e.setJoinMessage("");
        final Player p = e.getPlayer();
        PlayerHandler.addPlayer(p);
        p.getInventory().clear();
        p.getInventory().setHelmet(null);
        
        CraftPlayer craftplayer = (CraftPlayer) p;

        PlayerConnection connection = craftplayer.getHandle().playerConnection;
        IChatBaseComponent header = ChatSerializer.a("{\"text\": \"   §bCake§9Bandit §cv2   \"}");
        IChatBaseComponent footer = ChatSerializer.a("{\"text\": \"   §4Development Test   \"}");
        PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();

        try {
            Field headerField = packet.getClass().getDeclaredField("a");
            headerField.setAccessible(true);
            headerField.set(packet, header);
            headerField.setAccessible(!headerField.isAccessible());

            Field footerField = packet.getClass().getDeclaredField("b");
            footerField.setAccessible(true);
            footerField.set(packet, footer);
            footerField.setAccessible(!footerField.isAccessible());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        connection.sendPacket(packet);

        if (GameState.isState(GameState.IN_LOBBY)) {
            
            LocationUtilities.teleportToLobby(p);
            ChatUtilities.broadcast(ChatColor.DARK_AQUA + e.getPlayer().getName() + ChatColor.GOLD + " joined the game");
            p.setHealth(20.0);
            p.setExp(0);
            p.removePotionEffect(PotionEffectType.INVISIBILITY);
            p.setGameMode(GameMode.ADVENTURE);
            Game.setCanStart(Bukkit.getOnlinePlayers().size() >= 2);

        } else {
            
            PlayerHandler.addSpec(p);
            
            for (Player everyone : Bukkit.getOnlinePlayers()) {
                for (UUID uuid : PlayerHandler.spec) {
                    everyone.hidePlayer(Bukkit.getPlayer(uuid));
                }
            }
            
            ChatUtilities.onePlayer(ChatColor.GOLD + "You are now a spectator!", p);
            p.setGameMode(GameMode.ADVENTURE);
            p.setAllowFlight(true);
            p.setFlying(true);
            p.getInventory().clear();
            p.setExp(0);
            p.teleport(LocationUtilities.spawns[0]);
            CakeSB.showScoreboard();
            
        }

    }
}