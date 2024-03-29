package com.iwai.cpslab_plugin.Worlds;

import com.iwai.cpslab_plugin.CpsLab_Plugin;
import com.iwai.cpslab_plugin.Utils.PlayerUtil;

import com.iwai.cpslab_plugin.Utils.TextUtil;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import java.io.IOException;

public class CpsLab implements Listener {
    CpsLab_Plugin plugin;
    World world;
    Location spawnLocation;
    int spawnX, spawnY, spawnZ;

    public CpsLab(CpsLab_Plugin plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.world = plugin.getServer().getWorld("cpslab");
        this.spawnX = 30;
        this.spawnY = 5;
        this.spawnZ = 231;
        this.spawnLocation = new Location(world, spawnX, spawnY, spawnZ);

    }
    //プレイヤーがサーバー内のワールド変更をした時に呼び出される
    //対して使う用途無いので、削除しても問題ない
    @EventHandler
    public void onPlayerChangeWorld(PlayerChangedWorldEvent e) {
        Player player = e.getPlayer();
        World world = player.getWorld();
        if (this.world != world) return;
        player.teleport(spawnLocation);
        player.sendTitle("Welcome to CpsLab", "ようこそ、CpsLabへ", 20, 40, 20);
        TextUtil.summonText(player, 28, 7, 228, "センサに向かって、\n手を押し出す、\n上下に振ってみる、\n左右にゆっくり動かす");
    }
    //プレイヤーがエンティティを右クリックした時に呼び出される
    //対して使う用途無いので、削除しても問題ない
    @EventHandler
    public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent e) {
        Player player = e.getPlayer();
        World world = player.getWorld();
        Entity entity = e.getRightClicked();
        Location location = player.getLocation();
        Vector vector = location.getDirection();
        int maxDistance = 1;
        Block block = location.clone().add(vector.multiply(maxDistance)).getBlock();
        Block mainThreadBlock = block.getWorld().getBlockAt(block.getLocation());
        if (this.world != world) return;
        player.sendMessage(block.getType().name());
        player.sendMessage(mainThreadBlock.getType().name());
    }
    //プレイヤーがブロックを右クリックした時に呼び出される
    //対して使う用途無いので、削除しても問題ない
    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent e) throws IOException {
        Player player = e.getPlayer();
        World world = player.getWorld();
        Location location = player.getLocation();
        Vector vector = location.getDirection();
        int maxDistance = 1;
        Block block = location.clone().add(vector.multiply(maxDistance)).getBlock();
        Block mainThreadBlock = block.getWorld().getBlockAt(block.getLocation());
        if (this.world != world) return;
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
            Location playerLocation = player.getLocation();
            Vector playerDirection = player.getLocation().getDirection();
            Location direction = playerLocation.clone().add(playerDirection);

            float deltaYaw = (float) (Math.atan2(direction.getZ(), direction.getX()) * 180 / Math.PI) - 90;
            float deltaPitch = (float) (Math.asin(direction.getY()) * 180 / Math.PI);
            player.sendMessage("Clicked");
            PlayerUtil.PlayerRotation(player, 10, 0);
        }
    }
    //プレイヤーがブロックを壊そうとした時に呼び出される
    //ワールド内のブロックを壊せないように制御します
    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent e) throws IOException {
        Player player = e.getPlayer();
        World world = player.getWorld();
        if (this.world != world) return;
        e.setCancelled(true);
        player.sendMessage("壊さないで！");
    }
}
