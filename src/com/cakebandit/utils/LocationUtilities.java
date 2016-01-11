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

        spawns[0] = new Location(Bukkit.getWorld("MountTraya"), 674.5, 82.0, 797.5, (float) 180, 0);
        spawns[1] = new Location(Bukkit.getWorld("MountTraya"), 675.5, 82.0, 797.5, (float) 180, 0);
        spawns[2] = new Location(Bukkit.getWorld("MountTraya"), 676.5, 82.0, 797.5, (float) 180, 0);
        spawns[3] = new Location(Bukkit.getWorld("MountTraya"), 674.5, 82.0, 795.5, (float) 180, 0);
        spawns[4] = new Location(Bukkit.getWorld("MountTraya"), 676.5, 82.0, 795.5, (float) 180, 0);
        spawns[5] = new Location(Bukkit.getWorld("MountTraya"), 676.5, 82.0, 793.5, (float) 90, 0);
        spawns[6] = new Location(Bukkit.getWorld("MountTraya"), 676.5, 82.0, 791.5, (float) 0, 0);
        spawns[7] = new Location(Bukkit.getWorld("MountTraya"), 674.5, 82.0, 791.5, (float) 0, 0);
        spawns[8] = new Location(Bukkit.getWorld("MountTraya"), 676.5, 82.0, 789.5, (float) 0, 0);
        spawns[9] = new Location(Bukkit.getWorld("MountTraya"), 674.5, 82.0, 789.5, (float) 0, 0);

    }

    public static void initializeGallerySpawns() {

        lobby = new Location(Bukkit.getWorld("Gallery"), 344, 4, 618);

        cakes[0] = new Location(Bukkit.getWorld("Gallery"), 380, 3, 663);
        cakes[1] = new Location(Bukkit.getWorld("Gallery"), 401, 4, 695);
        cakes[2] = new Location(Bukkit.getWorld("Gallery"), 416, 4, 673);
        cakes[3] = new Location(Bukkit.getWorld("Gallery"), 393, 4, 631);
        cakes[4] = new Location(Bukkit.getWorld("Gallery"), 420, 5, 649);
        cakes[5] = new Location(Bukkit.getWorld("Gallery"), 387, 10, 647);
        cakes[6] = new Location(Bukkit.getWorld("Gallery"), 348, 5, 667);
        cakes[7] = new Location(Bukkit.getWorld("Gallery"), 344, 4, 673);
        cakes[8] = new Location(Bukkit.getWorld("Gallery"), 361, 4, 651);

        spawns[0] = new Location(Bukkit.getWorld("Gallery"), 382.5, 4.0, 642.5, (float) 0, 0);
        spawns[1] = new Location(Bukkit.getWorld("Gallery"), 380.5, 4.0, 642.5, (float) 0, 0);
        spawns[2] = new Location(Bukkit.getWorld("Gallery"), 378.5, 4.0, 642.5, (float) 0, 0);
        spawns[3] = new Location(Bukkit.getWorld("Gallery"), 382.5, 4.0, 640.5, (float) 0, 0);
        spawns[4] = new Location(Bukkit.getWorld("Gallery"), 380.5, 4.0, 640.5, (float) 0, 0);
        spawns[5] = new Location(Bukkit.getWorld("Gallery"), 378.5, 4.0, 640.5, (float) 0, 0);
        spawns[6] = new Location(Bukkit.getWorld("Gallery"), 382.5, 4.0, 638.5, (float) 0, 0);
        spawns[7] = new Location(Bukkit.getWorld("Gallery"), 380.5, 4.0, 638.5, (float) 0, 0);
        spawns[8] = new Location(Bukkit.getWorld("Gallery"), 378.5, 4.0, 638.5, (float) 0, 0);
        spawns[9] = new Location(Bukkit.getWorld("Gallery"), 380.5, 4.0, 636.5, (float) 0, 0);

    }

    public static void initializeTerminalSpawns() {

        lobby = new Location(Bukkit.getWorld("Terminal"), 54.5, 71.5, -157.5, (float) -90, 0);

        cakes[0] = new Location(Bukkit.getWorld("Terminal"), 95, 71, -184);
        cakes[1] = new Location(Bukkit.getWorld("Terminal"), 114, 71, -199);
        cakes[2] = new Location(Bukkit.getWorld("Terminal"), 144, 71, -189);
        cakes[3] = new Location(Bukkit.getWorld("Terminal"), 112, 71, -171);
        cakes[4] = new Location(Bukkit.getWorld("Terminal"), 132, 71, -137);
        cakes[5] = new Location(Bukkit.getWorld("Terminal"), 97, 72, -140);
        cakes[6] = new Location(Bukkit.getWorld("Terminal"), 66, 66, -147);
        cakes[7] = new Location(Bukkit.getWorld("Terminal"), 64, 75, -132);
        cakes[8] = new Location(Bukkit.getWorld("Terminal"), 84, 71, -155);

        spawns[0] = new Location(Bukkit.getWorld("Terminal"), 86.5, 71.0, -183.5, (float) -90, 0);
        spawns[1] = new Location(Bukkit.getWorld("Terminal"), 84.5, 71.0, -183.5, (float) -90, 0);
        spawns[2] = new Location(Bukkit.getWorld("Terminal"), 82.5, 71.0, -183.5, (float) -90, 0);
        spawns[3] = new Location(Bukkit.getWorld("Terminal"), 76.5, 71.0, -183.5, (float) -90, 0);
        spawns[4] = new Location(Bukkit.getWorld("Terminal"), 72.5, 71.0, -183.5, (float) -90, 0);
        spawns[5] = new Location(Bukkit.getWorld("Terminal"), 70.5, 71.0, -183.5, (float) -90, 0);
        spawns[6] = new Location(Bukkit.getWorld("Terminal"), 68.5, 71.0, -183.5, (float) -90, 0);
        spawns[7] = new Location(Bukkit.getWorld("Terminal"), 66.5, 71.0, -183.5, (float) -90, 0);
        spawns[8] = new Location(Bukkit.getWorld("Terminal"), 64.5, 71.0, -183.5, (float) -90, 0);
        spawns[9] = new Location(Bukkit.getWorld("Terminal"), 62.5, 71.0, -183.5, (float) -90, 0);

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
