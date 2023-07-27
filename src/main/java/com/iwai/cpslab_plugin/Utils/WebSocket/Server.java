package com.iwai.cpslab_plugin.Utils.WebSocket;

import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/data")
public class Server {
    public String echo(String message) {
        System.out.println("echo: " + message);
        return message;
    }
}
