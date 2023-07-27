package com.iwai.cpslab_plugin.Commands;

import com.iwai.cpslab_plugin.Utils.WebSocket.Client;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class BlockCommand {

    public static void blockCommand(String type, Player player) {
        if (type.equals("block")) {
            Location playerLocation = player.getLocation();
            Vector vector = playerLocation.getDirection();
            int maxDistance = 1;
            Location tartgetLocation = playerLocation.clone().add(vector.multiply(maxDistance));
            Block block = tartgetLocation.getBlock();



        }
    }
}
