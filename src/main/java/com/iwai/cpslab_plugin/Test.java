package com.iwai.cpslab_plugin;

import com.iwai.cpslab_plugin.Utils.WebSocket.Client;
import com.iwai.cpslab_plugin.Utils.WebSocket.Client2;
import com.iwai.cpslab_plugin.Utils.WebSocket.MessageCallback;

import java.net.http.WebSocket;

public class Test {
    public static void main(String[] args) throws Exception {
        Client client = new Client();
        client.connectWebSocket("ws://172.16.1.19:1880/data", null, null);
    }
}