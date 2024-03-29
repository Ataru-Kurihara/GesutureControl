package com.iwai.cpslab_plugin.Commands;

import com.iwai.cpslab_plugin.CpsLab_Plugin;
import com.iwai.cpslab_plugin.Utils.WebSocket.Client;
import com.iwai.cpslab_plugin.Utils.WebSocket.Client2;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class CpsCommand implements CommandExecutor {
    Client client;
    private static final CpsLab_Plugin plugin = CpsLab_Plugin.getPlugin(CpsLab_Plugin.class);
    //自作のコマンドを作成
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player)commandSender;
        Location location = player.getLocation();
        Vector vector = location.getDirection();
        int maxDistance = 1;
        Location targetLocation = location.clone().add(vector.multiply(maxDistance));
        Material air = Material.AIR;
        Block block = targetLocation.getBlock();
//        String url = "ws://172.16.0.1:1880/data";
//        String url = "ws://172.16.1.27:1880/data";
        String url = "ws://nodered-sandbox.cps.private:1880/data";
//        String url = "ws://192.168.0.12:1880/data";
//        String url = "ws://133.14.205.70:1880/data";

        if (command.getName().equalsIgnoreCase("cps")) {
            if (strings.length == 0) {
                player.sendMessage("CPSコマンド一覧!!");
            } else {
                if (strings[0].equals("block")) BlockCommand.blockCommand(strings[0], player);
                if (strings[0].equals("socket")) {
                    if (strings[1].equals("open")) {
                        player.sendMessage("WebSocketを開きます");
                        client = new Client();
                        client.connectWebSocket(url, player, plugin);
                    }
                    if (strings[1].equals("close")) {
                        player.sendMessage("WebSocketを閉じます");
                        client.clientClose();
                    }
                }
            }
        }
        return false;
    }
    public void setBlock(Block block, Material material) {
        block.setType(material);
    }
}
