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
    
    public static ItemStack spec = new ItemStack(Material.COMPASS, 1);

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
        bstickMeta.addEnchant(Enchantment.DAMAGE_ALL, 4, true);
        bstick.setItemMeta(bstickMeta);

        ItemMeta mred = (ItemMeta) red.getItemMeta();
        mred.setDisplayName(ChatColor.RED + "BANDIT");
        red.setItemMeta(mred);

        ItemMeta mgreen = (ItemMeta) green.getItemMeta();
        mgreen.setDisplayName(ChatColor.GREEN + "CITIZEN");
        green.setItemMeta(mgreen);
        
        ItemMeta mspec = (ItemMeta) spec.getItemMeta();
        mspec.setDisplayName(ChatColor.YELLOW + "SPECTATE");
        spec.setItemMeta(mspec);

    }
}