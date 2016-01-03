package com.cakebandit.handlers;

import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CBItem {

    public static ItemStack stick = new ItemStack(Material.STICK, 1);
    
    public static ItemStack bstick = new ItemStack(Material.STICK, 1);
    
    public static ItemStack red = new ItemStack(Material.WOOL, 1, (short) 14);
    
    public static ItemStack green = new ItemStack(Material.WOOL, 1, (short) 5);

    public static void setMetas() {

        ItemMeta stickMeta = (ItemMeta) stick.getItemMeta();
        stickMeta.setDisplayName(ChatColor.RESET.GREEN + "Accusation Stick");
        stickMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
        ArrayList<String> lore = new ArrayList<String>();
        lore.add("Right click a player to accuse them!");
        stickMeta.setLore(lore);
        stick.setItemMeta(stickMeta);

        ItemMeta bstickMeta = (ItemMeta) bstick.getItemMeta();
        bstickMeta.setDisplayName(ChatColor.RESET.RED + "Citizen Beater");
        bstickMeta.addEnchant(Enchantment.KNOCKBACK, 256, true);
        if (PlayerHandler.players.size() >= 1 && PlayerHandler.players.size() < 5) {
            bstickMeta.addEnchant(Enchantment.DAMAGE_ALL, 4, true);
        } else if (PlayerHandler.players.size() >= 5 && PlayerHandler.players.size() < 9) {
            bstickMeta.addEnchant(Enchantment.DAMAGE_ALL, 5, true);
        } else if (PlayerHandler.players.size() >= 9) {
            bstickMeta.addEnchant(Enchantment.DAMAGE_ALL, 6, true);
        }
        bstick.setItemMeta(bstickMeta);

        ItemMeta mred = (ItemMeta) red.getItemMeta();
        mred.setDisplayName(ChatColor.RED + "BANDIT");
        red.setItemMeta(mred);

        ItemMeta mgreen = (ItemMeta) green.getItemMeta();
        mgreen.setDisplayName(ChatColor.GREEN + "CITIZEN");
        green.setItemMeta(mgreen);

    }
}