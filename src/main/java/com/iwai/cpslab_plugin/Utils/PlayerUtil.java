package com.iwai.cpslab_plugin.Utils;

import com.iwai.cpslab_plugin.CpsLab_Plugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class PlayerUtil {


    public static void sendPlayerMessage(String message, Player player) {
        player.sendMessage(message);
    }

    public static void PlayerRotation(Player player, float deltaYaw, float deltaPitch) {
        Location playerLocation = player.getLocation();
        float newYaw = playerLocation.getYaw() + deltaYaw;
        float newPitch = playerLocation.getPitch() + deltaPitch;

        new BukkitRunnable() {
            private float tick = 0;

            @Override
            public void run() {
                tick++;
                float totalTicks = 20;
                float ration = tick / totalTicks;
                float currentYaw = playerLocation.getYaw();
                float currentPitch = playerLocation.getPitch();
                float interpolatedYaw = currentYaw + (newYaw - currentYaw) * ration;
                float interpolatedPitch = currentPitch + (newPitch - currentPitch) * ration;

                playerLocation.setYaw(interpolatedYaw);
                playerLocation.setPitch(interpolatedPitch);
                player.teleport(playerLocation);

                if (tick >= totalTicks) {
                    cancel();
                }
            }
        }.runTaskTimer(CpsLab_Plugin.getPlugin(CpsLab_Plugin.class), 0, 1);
    }

    public static void MovePlayer(Player player, String finger) {
        double moveDistance = 0;
        switch (finger) {
            case "1-finger":
                moveDistance = 1.0;
                break;
            case "2-finger":
                moveDistance = 2.0;
                break;
            case "3-finger":
                moveDistance = 3.0;
                break;
            case "4-finger":
                moveDistance = 4.0;
                break;
            case "5-finger":
                moveDistance = 5.0;
                break;
            default:
                break;
        }
        float yaw = player.getLocation().getYaw();
        double radianYaw = Math.toRadians(yaw);

        double moveX = -Math.sin(radianYaw) * moveDistance;
        double moveZ = Math.cos(radianYaw) * moveDistance;

        Vector movement = new Vector(moveX, 0, moveZ);
        player.setVelocity(movement);
    }

    public static void backWardPlayer(Player player, String finger) {
        double moveDistance = 0;
        switch (finger) {
            case "1-finger":
                moveDistance = 1.0;
                break;
            case "2-finger":
                moveDistance = 2.0;
                break;
            case "3-finger":
                moveDistance = 3.0;
                break;
            case "4-finger":
                moveDistance = 4.0;
                break;
            case "5-finger":
                moveDistance = 5.0;
                break;
            default:
                break;
        }
        float yaw = player.getLocation().getYaw();
        double radianYaw = Math.toRadians(yaw);

        double moveX = Math.sin(radianYaw) * moveDistance;
        double moveZ = -Math.cos(radianYaw) * moveDistance;

        Vector movement = new Vector(moveX, 0, moveZ);
        player.setVelocity(movement);
        player.sendMessage("backward");
    }

    public static void giveItem(Player player, Material material) {
        PlayerInventory inventory = player.getInventory();
        ItemStack itemStack = new ItemStack(material);
        inventory.addItem(itemStack);
    }

    public static void useItem(Player player) {
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        String mainItem = itemStack.getType().toString();
        Location location = player.getLocation();
        Vector vector = location.getDirection();
        int maxDistance = 1;
        Location targetLocation = location.clone().add(vector.multiply(maxDistance));
        if (mainItem.equals("DIAMOND_SWORD")) {
//            if (targetLocation.getBlock().getType().equals(Material.AIR)) {
//                targetLocation.getBlock().setType(Material.DIAMOND_BLOCK);
//            } else {
//                targetLocation.getBlock().setType(Material.AIR);
//            }
            if (targetLocation.add(0, 1, 0).getBlock().getType().equals(Material.AIR)) {
                targetLocation.add(0, 1, 0).getBlock().setType(Material.DIAMOND_BLOCK);
            } else if (targetLocation.add(0, 1, 0).getBlock().getType().equals(Material.DIAMOND_BLOCK)) {
                targetLocation.add(0, 1, 0).getBlock().setType(Material.AIR);
            } else if (targetLocation.add(0, 2, 0).getBlock().getType().equals(Material.DIAMOND_BLOCK)) {
                targetLocation.add(0, 2, 0).getBlock().setType(Material.AIR);
            }
        }
    }




}
