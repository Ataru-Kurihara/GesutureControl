package com.iwai.cpslab_plugin;

import com.iwai.cpslab_plugin.Commands.CpsCommand;
import com.iwai.cpslab_plugin.Worlds.CpsLab;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class CpsLab_Plugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        CpsLab cpslab = new CpsLab(this);
        CpsLab_Plugin plugin = this;
        Objects.requireNonNull(getCommand("cps")).setExecutor(new CpsCommand());
        
        for (Player player: Objects.requireNonNull(Bukkit.getWorld("cpslab")).getPlayers()) {
            player.sendMessage("Welcome to CpsLab!!");
            Bukkit.getScheduler().runTaskTimer(this, () -> {
                player.setGameMode(GameMode.CREATIVE);
            }, 0, 20);
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
