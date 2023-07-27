package com.iwai.cpslab_plugin.Utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class PlayerUtil {
    public static void sendPlayerMessage(String message, Player player) {
        player.sendMessage(message);
    }
}
