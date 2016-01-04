package com.cakebandit.utils;

import com.cakebandit.GameState;
import com.cakebandit.handlers.AccusationHandler;
import com.cakebandit.handlers.Database;
import com.cakebandit.handlers.PlayerHandler;
import java.lang.reflect.Field;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import static org.bukkit.ChatColor.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ChatUtilities {

    public static void broadcast(String msg) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(starter() + msg);
        }
    }

    public static void infoBar(String msg) {

        for (Player p : Bukkit.getOnlinePlayers()) {
            CraftPlayer craftplayer = (CraftPlayer) p;
            PlayerConnection connection = craftplayer.getHandle().playerConnection;
            IChatBaseComponent warning = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + msg + "\"}");
            PacketPlayOutChat packet = new PacketPlayOutChat();

            try {
                Field field = packet.getClass().getDeclaredField("a");
                field.setAccessible(true);
                field.set(packet, warning);
                field.setAccessible(!field.isAccessible());

                Field Field2 = packet.getClass().getDeclaredField("b");
                Field2.setAccessible(true);
                Field2.set(packet, (byte) 2);
                Field2.setAccessible(!Field2.isAccessible());

            } catch (Exception ex) {
                ex.printStackTrace();
            }

            connection.sendPacket(packet);
        }

    }

    public static void showTitle(String msg) {

        IChatBaseComponent chatTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + msg + "\"}");
        PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, chatTitle, 5, 40, 5);

        for (Player p : Bukkit.getOnlinePlayers()) {

            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(title);

        }

    }

    public static void accusedPlayer(Player accuser, Player accused) {

        if (AccusationHandler.getAccusationCount(accuser) == 0) {
            AccusationHandler.newAccusation(accuser, accused);
            IChatBaseComponent accuse = ChatSerializer.a("{\"text\":\"" + starter() + ChatColor.GREEN + "Accuse §3" + accused.getName() + "§a?\",\"color\":\"green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/accuse " + accused.getName() + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Click to accuse §3" + accused.getName() + "\",\"color\":\"green\"}]}}}");
            PacketPlayOutChat packet = new PacketPlayOutChat(accuse);
            ((CraftPlayer) accuser).getHandle().playerConnection.sendPacket(packet);
            IChatBaseComponent noaccuse = ChatSerializer.a("{\"text\":\"" + starter() + ChatColor.DARK_RED + "Don't Accuse §3" + accused.getName() + "§4?\",\"color\":\"dark_red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/accuse " + accused.getName() + " no\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Click to not accuse §3" + accused.getName() + "\",\"color\":\"dark_red\"}]}}}");
            PacketPlayOutChat packet2 = new PacketPlayOutChat(noaccuse);
            ((CraftPlayer) accuser).getHandle().playerConnection.sendPacket(packet2);
        }else{
            onePlayer("You already have an accusation in progress!", accuser);
        }

    }

    public static void showSubTitle(String msg) {

        IChatBaseComponent chatSubTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + msg + "\"}");
        PacketPlayOutTitle subTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, chatSubTitle, 5, 40, 5);

        for (Player p : Bukkit.getOnlinePlayers()) {

            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(subTitle);

        }

    }

    public static void oneSubTitle(String msg, Player p) {

        IChatBaseComponent chatSubTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + msg + "\"}");
        PacketPlayOutTitle subTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, chatSubTitle, 5, 40, 5);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(subTitle);

    }

    public static void oneTitle(String msg, Player p) {

        IChatBaseComponent chatTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + msg + "\"}");
        PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, chatTitle, 5, 40, 5);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(title);

    }

    public static void onePlayer(String msg, Player player) {

        player.sendMessage(starter() + msg);

    }

    public static void chat(String msg, Player player) {

        Database.openConnection();
        if (GameState.isState(GameState.IN_GAME)) {
            if (PlayerHandler.alive.contains(player.getUniqueId())) {

                if (PlayerHandler.tested.contains(player.getUniqueId()) && player != PlayerHandler.getBandit()) {
                    Bukkit.broadcastMessage(GREEN + player.getDisplayName() + ChatColor.GRAY + " » " + WHITE + msg);
                } else if (player == PlayerHandler.bandit && PlayerHandler.isCaught == true) {
                    Bukkit.broadcastMessage(RED + player.getDisplayName() + ChatColor.GRAY + " » " + WHITE + msg);
                } else {
                    Bukkit.broadcastMessage(DARK_AQUA + player.getDisplayName() + ChatColor.GRAY + " » " + WHITE + msg);
                }

            } else {
                for (Player p : Bukkit.getOnlinePlayers()) {

                    if (PlayerHandler.spec.contains(p.getUniqueId())) {

                        p.sendMessage(DARK_RED + "DEAD " + DARK_AQUA + player.getDisplayName() + ChatColor.GRAY + " » " + WHITE + msg);

                    }

                }
            }
        } else {
            Bukkit.broadcastMessage(GRAY + "(" + YELLOW + Database.getCb(player, "points") + GRAY + ") " + DARK_AQUA + player.getDisplayName() + ChatColor.GRAY + " » " + WHITE + msg);
        }
        Database.closeConnection();

    }

    public static void records(Player player) {

        Database.openConnection();

        try {

            player.sendMessage(GRAY + "[" + AQUA + "Cake" + BLUE + "Bandit" + RED + " Records" + GRAY + "]");
            player.sendMessage(GOLD + "User: " + YELLOW + player.getName());
            player.sendMessage(GOLD + "Points: " + YELLOW + Database.getCb(player, "points"));
            player.sendMessage(GOLD + "Games: " + YELLOW + Database.getCb(player, "games"));
            player.sendMessage(GOLD + "Wins: " + YELLOW + Database.getCb(player, "wins"));
            player.sendMessage(GOLD + "Kills: " + YELLOW + Database.getCb(player, "kills"));
            player.sendMessage(GOLD + "Deaths: " + YELLOW + Database.getCb(player, "deaths"));
            player.sendMessage(GOLD + "Bandits Revealed: " + YELLOW + Database.getCb(player, "discovered"));
            player.sendMessage(GOLD + "Cakes Eaten: " + YELLOW + Database.getCb(player, "eaten"));
            player.sendMessage(GRAY + "[" + AQUA + "Cake" + BLUE + "Bandit" + RED + " Records" + GRAY + "]");

        } catch (Exception ex) {

            ex.printStackTrace();

        } finally {

            Database.closeConnection();

        }

    }

    private static String starter() {

        return GRAY + "[" + AQUA + "Cake" + BLUE + "Bandit" + GRAY + "] " + GOLD;

    }
}
