package com.iwai.cpslab_plugin.Utils.Http;

import com.iwai.cpslab_plugin.Utils.Rcon.Example;
import com.iwai.cpslab_plugin.Worlds.CpsLab;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class MyServer {
    public static void main(String[] args) throws IOException {
        int port = 8765;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", new RequestHandler());
        server.start();
    }
}

