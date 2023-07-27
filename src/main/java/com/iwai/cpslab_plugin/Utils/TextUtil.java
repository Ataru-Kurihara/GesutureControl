package com.iwai.cpslab_plugin.Utils;

import org.bukkit.entity.Player;

public class TextUtil {
    public static void summonText(Player player, float x, float y, float z, String text) {
        player.performCommand("summon minecraft:text_display " + x + " " + y + " " + z + " {Text:\"" + text + "\"}");
    }
}
