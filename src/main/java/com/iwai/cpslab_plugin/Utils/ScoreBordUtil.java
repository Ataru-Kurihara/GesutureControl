package com.iwai.cpslab_plugin.Utils;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class ScoreBordUtil {
    public static void setScoreBord(Player player) {
        ScoreboardManager manager = player.getServer().getScoreboardManager();
        if (manager != null) {
            Scoreboard board = manager.getNewScoreboard();
            Objective objective = board.registerNewObjective("test", "dummy", "test");

            int customScore = 0;
            Score score = objective.getScore("test");
//            score.setScore(customScore);
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            player.setScoreboard(board);
        }
    }
    static YamlConfiguration yamlConfiguration;
    public static void setTime(Player player, int time) {
        yamlConfiguration.set(player.getName(), time);
    }
    public static int getTime(Player player) {
        int time = 0;
        if (yamlConfiguration.get(player.getName()) != null) {
            time = (int) yamlConfiguration.get(player.getName());
        }
        return time;
    }
}
