package com.iwai.cpslab_plugin.Utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class BlockUtil {
    public static void controlBlock(int offsetX, int offsetY, int offsetZ, Block block, Player player) {
        Location location = block.getLocation();
        World world = location.getWorld();
        Location newLocation = location.clone().add(offsetX, offsetY, offsetZ);
        assert world != null;
        Block newBlock = world.getBlockAt(newLocation);
        BlockState blockState = block.getState();
        newBlock.setType(block.getType(), false);
        newBlock.setBlockData(blockState.getBlockData());
        block.setType(Material.AIR);
    }

    public static void breakBlock(Player player, String text) {
        World world = player.getWorld();
        Location location = player.getLocation();
        Vector vector = location.getDirection();
        int maxDistance = 1;
        Material air = Material.AIR;
        switch (text) {
            case "Down" -> {
                player.sendMessage("Down");
                Location targetLocation = location.clone().add(vector.multiply(maxDistance));
                Block block = targetLocation.getBlock();
                player.sendMessage(block.getType().name());
                block.setType(air);
//                    if (block.getType() == org.bukkit.Material.AIR) player.sendMessage("ブロックがありません");
//                    else {
//                        block.setType(org.bukkit.Material.AIR);
//                    }
            }
            case "Up" -> player.sendMessage("振り上げ");
            case "Right" -> {
                player.sendMessage("右");
                Location targetLocation = location.clone().add(vector.multiply(-maxDistance));
                Block block = targetLocation.getBlock();
                block.setType(Material.DIAMOND_BLOCK);
            }
        }
    }
}
