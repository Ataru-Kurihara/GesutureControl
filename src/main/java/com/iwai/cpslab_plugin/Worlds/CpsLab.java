package com.iwai.cpslab_plugin.Worlds;

import com.iwai.cpslab_plugin.CpsLab_Plugin;
import com.iwai.cpslab_plugin.HttpExchangeExample;
import com.iwai.cpslab_plugin.Utils.BossBarUtil;
import com.iwai.cpslab_plugin.Utils.Http.RequestHandler;
import com.iwai.cpslab_plugin.Utils.Rcon.Example;
import com.iwai.cpslab_plugin.Utils.ScoreBordUtil;
import com.iwai.cpslab_plugin.Utils.Socket.Server1;
import com.iwai.cpslab_plugin.Utils.TextUtil;
import com.iwai.cpslab_plugin.Utils.WebSocket.Client;
import com.iwai.cpslab_plugin.Utils.WebSocket.MessageCallback;
import net.md_5.bungee.api.ChatMessageType;
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

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class CpsLab implements Listener {
    private static String s;
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

    @EventHandler
    public void onPlayerChangeWorld(PlayerChangedWorldEvent e) {
        Player player = e.getPlayer();
        World world = player.getWorld();
        if (this.world != world) return;
        player.teleport(spawnLocation);
        player.sendTitle("Welcome to CpsLab", "ようこそ、CpsLabへ", 20, 40, 20);
        TextUtil.summonText(player, 28, 7, 228, "センサに向かって、\n手を押し出す、\n上下に振ってみる、\n左右にゆっくり動かす");
    }

    @EventHandler
    public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent e) {
        Player player = e.getPlayer();
        World world = player.getWorld();
        Entity entity = e.getRightClicked();
        if (this.world != world) return;
    }

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent e) throws IOException {
        Player player = e.getPlayer();
        World world = player.getWorld();
        if (this.world != world) return;
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
            if (player.getInventory().getItemInMainHand().getType().equals(Material.STICK)) {
                Block block = e.getClickedBlock();
//                Client client = new Client();
//                client.connectWebSocket("ws://172.16.0.1:1880/data", player, block);

            }
        }
    }
    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent e) throws IOException {
        Player player = e.getPlayer();
        World world = player.getWorld();
        if (this.world != world) return;
        e.setCancelled(true);
        player.sendMessage("壊さないで！");
    }
}
