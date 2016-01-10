package com.cakebandit.utils;

import com.cakebandit.GameState;
import com.cakebandit.handlers.AccusationHandler;
import com.cakebandit.handlers.CBItem;
import com.cakebandit.handlers.Database;
import com.cakebandit.handlers.PlayerHandler;
import java.lang.reflect.Field;
import java.util.UUID;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import static org.bukkit.ChatColor.*;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

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

    public static void showList(Player p) {

        if (GameState.isState(GameState.IN_GAME)) {

            String specs = "";
            String alive = "";

            for (UUID uuid : PlayerHandler.alive) {

                Player c = Bukkit.getPlayer(uuid);
                if (uuid == PlayerHandler.alive.get(PlayerHandler.alive.size() - 1)) {
                    alive = alive + c.getName();
                } else {
                    alive = alive + c.getName() + ", ";
                }

            }

            for (UUID uuid : PlayerHandler.spec) {

                Player c = Bukkit.getPlayer(uuid);
                if (uuid == PlayerHandler.spec.get(PlayerHandler.spec.size() - 1)) {
                    specs = specs + c.getName();
                } else {
                    specs = specs + c.getName() + ", ";
                }

            }

            p.sendMessage(DARK_GRAY + "" + BOLD + "Online Players");
            p.sendMessage(GREEN + "ALIVE: " + GOLD + alive);
            if (!specs.equals("")) {
                p.sendMessage(DARK_RED + "DEAD: " + GOLD + specs);
            }

        } else {

            String online = "";

            for (UUID uuid : PlayerHandler.players) {

                Player c = Bukkit.getPlayer(uuid);
                if (uuid == PlayerHandler.players.get(PlayerHandler.players.size() - 1)) {
                    online = online + c.getName();
                } else {
                    online = online + c.getName() + ", ";
                }

            }

            p.sendMessage(DARK_GRAY + "" + BOLD + "Online Players");
            p.sendMessage(GREEN + "ONLINE: " + GOLD + online);

        }

    }

    public static void accusedPlayer(Player accuser, Player accused) {

        if (AccusationHandler.getAccusationCount(accuser) == 0) {
            AccusationHandler.newAccusation(accuser, accused);
            /*IChatBaseComponent accuse = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + starter() + ChatColor.GREEN + "Accuse §3" + accused.getName() + "§a?\",\"color\":\"green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/accuse " + accused.getName() + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Click to accuse §3" + accused.getName() + "\",\"color\":\"green\"}]}}}");
             PacketPlayOutChat packet = new PacketPlayOutChat(accuse);
             ((CraftPlayer) accuser).getHandle().playerConnection.sendPacket(packet);
             IChatBaseComponent noaccuse = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + starter() + ChatColor.DARK_RED + "Don't Accuse §3" + accused.getName() + "§4?\",\"color\":\"dark_red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/accuse " + accused.getName() + " no\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Click to not accuse §3" + accused.getName() + "\",\"color\":\"dark_red\"}]}}}");
             PacketPlayOutChat packet2 = new PacketPlayOutChat(noaccuse);
             ((CraftPlayer) accuser).getHandle().playerConnection.sendPacket(packet2);*/

            Inventory inv = Bukkit.createInventory(null, 9, "Accuse " + accused.getName() + "?");

            inv.setItem(2, CBItem.accuse);
            inv.setItem(6, CBItem.deny);

            accuser.openInventory(inv);

        } else {
            onePlayer("You already have an accusation in progress!", accuser);
        }

    }

    public static void onePlayer(String msg, Player player) {

        player.sendMessage(starter() + msg);

    }

    public static void chat(String msg, Player player) {

        Database.openConnection();
        if (GameState.isState(GameState.IN_GAME)) {
            if (PlayerHandler.alive.contains(player.getUniqueId())) {

                if (PlayerHandler.tested.contains(player.getUniqueId()) && player != PlayerHandler.getBandit()) {
                    Bukkit.broadcastMessage(chatStarter(player) + GREEN + player.getName() + ChatColor.GRAY + " » " + WHITE + msg);
                } else if (player == PlayerHandler.bandit && PlayerHandler.isCaught == true) {
                    Bukkit.broadcastMessage(chatStarter(player) + RED + player.getName() + ChatColor.GRAY + " » " + WHITE + msg);
                } else {
                    Bukkit.broadcastMessage(chatStarter(player) + DARK_AQUA + player.getName() + ChatColor.GRAY + " » " + WHITE + msg);
                }

            } else {
                for (Player p : Bukkit.getOnlinePlayers()) {

                    if (PlayerHandler.spec.contains(p.getUniqueId())) {

                        p.sendMessage(DARK_RED + "DEAD " + chatStarter(player) + DARK_AQUA + player.getName() + ChatColor.GRAY + " » " + WHITE + msg);

                    }

                }
            }
        } else {
            Bukkit.broadcastMessage(GRAY + "(" + YELLOW + Database.getCb(player, "points") + GRAY + ") " + chatStarter(player) + DARK_AQUA + player.getName() + ChatColor.GRAY + " » " + WHITE + msg);
        }
        Database.closeConnection();

    }

    public static String chatStarter(Player p) {

        Database.openConnection();

        int points = Database.getCb(p, "points");
        int highestPoints = Database.getHighestPoints();

        Database.closeConnection();

        if (points == highestPoints) {

            return RED + "§lM" + GOLD + "§la" + YELLOW + "§lr" + GREEN + "§ly " + BLUE + "§lB" + DARK_PURPLE + "§le" + LIGHT_PURPLE + "§lr" + RED + "§lr" + GOLD + "§ly " + YELLOW + "§l| ";

        } else {

            if (points < 0) {

                return DARK_RED + "§lCake Bait | ";

            } else if (points >= 0 && points <= 49) {

                return GOLD + "Brownie | ";

            } else if (points >= 50 && points <= 249) {

                return LIGHT_PURPLE + "Cupcake | ";

            } else if (points >= 250 && points <= 999) {

                return BLUE + "Scone | ";

            } else if (points >= 1000 && points <= 2499) {

                return YELLOW + "Cheese Cake | ";

            } else if (points >= 2500 && points <= 4999) {

                return GREEN + "Victoria Sponge | ";

            } else if (points >= 5000 && points <= 9999) {

                return RED + "Red Velvet Cake | ";

            } else if (points >= 10000 && points <= 49999) {

                return DARK_GRAY + "Black Forest Cake | ";

            } else if (points >= 50000) {

                return DARK_GREEN + "Paul Hollywood | ";

            }
        }

        return "";

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
