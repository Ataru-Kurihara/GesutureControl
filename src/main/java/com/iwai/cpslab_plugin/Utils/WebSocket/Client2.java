package com.iwai.cpslab_plugin.Utils.WebSocket;



import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import javax.websocket.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@ClientEndpoint
public class Client2 {
    private Session session;

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        System.out.println("onOpen");
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("onMessage " + message);
        World world = Bukkit.getWorld("cpslab");
        assert world != null;
        List<Player> players = world.getPlayers();
        for (Player player : players) {
            player.sendMessage(message);
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        System.out.println("onClose");
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("onError");
    }

    public void connectToServer(String url) throws Exception {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        container.connectToServer(this, URI.create(url));
    }

    public void sendMessage(String message, Player player) throws Exception {
        session.getBasicRemote().sendText(message);
        player.sendMessage("test");
    }

    public void closeSession() throws Exception {
        session.close();
    }
}
