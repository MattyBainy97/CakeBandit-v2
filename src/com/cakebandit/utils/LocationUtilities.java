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

        lobby = new Location(Bukkit.getWorld("MountTraya"), -1047, 38, 603);

        cakes[0] = new Location(Bukkit.getWorld("MountTraya"), -1040, 41, 651);
        cakes[1] = new Location(Bukkit.getWorld("MountTraya"), -1004, 57, 588);
        cakes[2] = new Location(Bukkit.getWorld("MountTraya"), -996, 47, 648);
        cakes[3] = new Location(Bukkit.getWorld("MountTraya"), -1004, 47, 659);
        cakes[4] = new Location(Bukkit.getWorld("MountTraya"), -984, 48, 686);
        cakes[5] = new Location(Bukkit.getWorld("MountTraya"), -1019, 44, 643);
        cakes[6] = new Location(Bukkit.getWorld("MountTraya"), -1021, 42, 666);
        cakes[7] = new Location(Bukkit.getWorld("MountTraya"), -1014, 56, 628);
        cakes[8] = new Location(Bukkit.getWorld("MountTraya"), -1038, 49, 623);

        spawns[0] = new Location(Bukkit.getWorld("MountTraya"), -1032, 45, 624);
        spawns[1] = new Location(Bukkit.getWorld("MountTraya"), -1028, 46, 632);
        spawns[2] = new Location(Bukkit.getWorld("MountTraya"), -1034, 45, 632);
        spawns[3] = new Location(Bukkit.getWorld("MountTraya"), -1032, 43, 639);
        spawns[4] = new Location(Bukkit.getWorld("MountTraya"), -1034, 43, 641);
        spawns[5] = new Location(Bukkit.getWorld("MountTraya"), -1032, 41, 645);
        spawns[6] = new Location(Bukkit.getWorld("MountTraya"), -1036, 41, 648);
        spawns[7] = new Location(Bukkit.getWorld("MountTraya"), -1030, 42, 649);
        spawns[8] = new Location(Bukkit.getWorld("MountTraya"), -1027, 42, 650);
        spawns[9] = new Location(Bukkit.getWorld("MountTraya"), -1027, 42, 646);

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
