package com.iwai.cpslab_plugin.Utils;

import com.iwai.cpslab_plugin.CpsLab_Plugin;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class BossBarUtil {
    private static String[] messages = {
        "test",
        "test2",
        "test3"
    };
    private static BossBar bossBar;

    private static int index = 0;

    public static void startUpdatingBossBar(Player player) {
       bossBar = Bukkit.createBossBar("test", BarColor.BLUE, BarStyle.SOLID);
       bossBar.setVisible(true);

       Bukkit.getScheduler().runTaskTimer(CpsLab_Plugin.getPlugin(CpsLab_Plugin.class), () -> {
           updateMessage(player);
       }, 0, 20 * 5);
    }
    public static void updateMessage(Player player) {
        bossBar.setTitle(messages[index]);
        index = (index + 1) % messages.length;
        bossBar.addPlayer(player);

    }
}
