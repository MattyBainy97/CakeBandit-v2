package com.cakebandit.utils;

import com.cakebandit.handlers.PlayerHandler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class LocationUtilities {

    public static Location[] cakes = new Location[9];
    public static Location[] spawns = new Location[10];
    public static Location lobby;

    public static void initializeSwordSpawns() {

        lobby = new Location(Bukkit.getWorld("Sword"), 1619, 65, 827);

        cakes[0] = new Location(Bukkit.getWorld("Sword"), 1653, 84, 791);
        cakes[1] = new Location(Bukkit.getWorld("Sword"), 1654, 84, 783);
        cakes[2] = new Location(Bukkit.getWorld("Sword"), 1691, 71, 793);
        cakes[3] = new Location(Bukkit.getWorld("Sword"), 1688, 85, 748);
        cakes[4] = new Location(Bukkit.getWorld("Sword"), 1666, 65, 753);
        cakes[5] = new Location(Bukkit.getWorld("Sword"), 1673, 77, 787);
        cakes[6] = new Location(Bukkit.getWorld("Sword"), 1687, 80, 748);
        cakes[7] = new Location(Bukkit.getWorld("Sword"), 1710, 81, 759);
        cakes[8] = new Location(Bukkit.getWorld("Sword"), 1673, 71, 743);

        spawns[0] = new Location(Bukkit.getWorld("Sword"), 1660, 65, 769);
        spawns[1] = new Location(Bukkit.getWorld("Sword"), 1660, 65, 766);
        spawns[2] = new Location(Bukkit.getWorld("Sword"), 1660, 65, 772);
        spawns[3] = new Location(Bukkit.getWorld("Sword"), 1663, 65, 769);
        spawns[4] = new Location(Bukkit.getWorld("Sword"), 1663, 65, 772);
        spawns[5] = new Location(Bukkit.getWorld("Sword"), 1663, 65, 766);
        spawns[6] = new Location(Bukkit.getWorld("Sword"), 1664, 65, 775);
        spawns[7] = new Location(Bukkit.getWorld("Sword"), 1665, 65, 764);
        spawns[8] = new Location(Bukkit.getWorld("Sword"), 1667, 65, 774);
        spawns[9] = new Location(Bukkit.getWorld("Sword"), 1667, 65, 765);

    }
    
    public static void initializeMountTrayaSpawns() {

        lobby = new Location(Bukkit.getWorld("MountTraya"), 600, 52, 795);

        cakes[0] = new Location(Bukkit.getWorld("MountTraya"), 668, 52, 809);
        cakes[1] = new Location(Bukkit.getWorld("MountTraya"), 652, 53, 778);
        cakes[2] = new Location(Bukkit.getWorld("MountTraya"), 598, 53, 820);
        cakes[3] = new Location(Bukkit.getWorld("MountTraya"), 626, 61, 786);
        cakes[4] = new Location(Bukkit.getWorld("MountTraya"), 597, 52, 808);
        cakes[5] = new Location(Bukkit.getWorld("MountTraya"), 607, 59, 809);
        cakes[6] = new Location(Bukkit.getWorld("MountTraya"), 600, 63, 793);
        cakes[7] = new Location(Bukkit.getWorld("MountTraya"), 598, 72, 806);
        cakes[8] = new Location(Bukkit.getWorld("MountTraya"), 612, 49, 804);

        spawns[0] = new Location(Bukkit.getWorld("MountTraya"), 674.5, 82.0, 797.5, (float)180, 0);
        spawns[1] = new Location(Bukkit.getWorld("MountTraya"), 675.5, 82.0, 797.5, (float)180, 0);
        spawns[2] = new Location(Bukkit.getWorld("MountTraya"), 676.5, 82.0, 797.5, (float)180, 0);
        spawns[3] = new Location(Bukkit.getWorld("MountTraya"), 674.5, 82.0, 795.5, (float)180, 0);
        spawns[4] = new Location(Bukkit.getWorld("MountTraya"), 676.5, 82.0, 795.5, (float)180, 0);
        spawns[5] = new Location(Bukkit.getWorld("MountTraya"), 676.5, 82.0, 793.5, (float)90, 0);
        spawns[6] = new Location(Bukkit.getWorld("MountTraya"), 676.5, 82.0, 791.5, (float)0, 0);
        spawns[7] = new Location(Bukkit.getWorld("MountTraya"), 674.5, 82.0, 791.5, (float)0, 0);
        spawns[8] = new Location(Bukkit.getWorld("MountTraya"), 676.5, 82.0, 789.5, (float)0, 0);
        spawns[9] = new Location(Bukkit.getWorld("MountTraya"), 674.5, 82.0, 789.5, (float)0, 0);

    }

    public static void teleportToLobby(Player p) {

        p.teleport(lobby);

    }

    public static void teleportToGame() {

        for (int i = 0; i < Bukkit.getOnlinePlayers().size(); i++) {

            Bukkit.getPlayer(PlayerHandler.players.get(i)).teleport(spawns[i]);

        }

    }

    public static void spawnCakes() {

        for (int i = 0; i < 9; i++) {

            cakes[i].getBlock().setType(Material.CAKE_BLOCK);

        }

    }
}
