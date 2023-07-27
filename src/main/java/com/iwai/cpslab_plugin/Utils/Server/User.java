package com.iwai.cpslab_plugin.Utils.Server;

import com.google.gson.Gson;
import com.iwai.cpslab_plugin.Model.Data;
import com.iwai.cpslab_plugin.Utils.WebSocket.Client;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;

public class User {
    public static void sendMessage(String message) {
        World world = Bukkit.getWorld("cpslab");
        assert world != null;
        world.getPlayers().forEach(player -> {
            player.sendMessage(message);
        });
    }
}
