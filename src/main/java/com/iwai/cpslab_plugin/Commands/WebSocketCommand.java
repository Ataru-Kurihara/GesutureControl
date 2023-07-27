package com.iwai.cpslab_plugin.Commands;

import com.iwai.cpslab_plugin.Utils.WebSocket.Client;
import com.iwai.cpslab_plugin.Utils.WebSocket.Client2;
import org.bukkit.entity.Player;

public class WebSocketCommand {
    public static void openCommand(String type) throws Exception {
        if (type.equals("open")) {
//            Client client = new Client();
//            client.connectWebSocket("ws://172.16.1.19:1880/data", );

//            Client2 client2 = new Client2();
//            client2.connectToServer("ws://172.16.1.19:1880/data");
        }
    }
    public static void closeCommand(String type, Player player) {
        if (type.equals("close")) {
//            Client client = new Client();
//            client.clientClose(player);

        }
    }
}
