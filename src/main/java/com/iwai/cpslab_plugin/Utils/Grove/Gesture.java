package com.iwai.cpslab_plugin.Utils.Grove;

import com.iwai.cpslab_plugin.Utils.PlayerUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Gesture {
    public static void getGroveGesture(String gesture, Player player) {
        if (gesture != null) {
            switch (gesture) {
                case "1-finger", "3-finger", "5-finger", "2-finger", "4-finger":
                    PlayerUtil.MovePlayer(player, gesture);
                    break;
                case "Swipe Right":
                    PlayerUtil.giveItem(player, Material.DIAMOND_SWORD);
                    break;
                case "Swipe Left":
                    PlayerUtil.giveItem(player, Material.DIAMOND_AXE);
                    break;
                case "Rotate Right":
                    PlayerUtil.PlayerRotation(player, 10, 0);
                    break;
                case "Rotate Left":
                    PlayerUtil.PlayerRotation(player, -10, 0);
                    break;
                case "Pinch":
                    PlayerUtil.useItem(player);
                    break;
            }
        }
    }
}
