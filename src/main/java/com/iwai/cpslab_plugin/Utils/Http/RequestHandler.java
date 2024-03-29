package com.iwai.cpslab_plugin.Utils.Http;


import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class RequestHandler implements HttpHandler {
    public static String json = "";
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("handle");
        if (exchange.getRequestMethod().equalsIgnoreCase("POST")) {
            InputStream requestBody = exchange.getRequestBody();
            json = convertStreamToString(requestBody);
            System.out.println(json);

            exchange.sendResponseHeaders(200, json.length());
            OutputStream responseBody = exchange.getResponseBody();
            responseBody.write(json.getBytes(StandardCharsets.UTF_8));
            responseBody.close();
        } else {
            String response = "Method not allowed";
            exchange.sendResponseHeaders(405, response.length());
            OutputStream responseBody = exchange.getResponseBody();
            responseBody.write(response.getBytes(StandardCharsets.UTF_8));
            responseBody.close();
        }
    }

    private String convertStreamToString(InputStream is) {
        Scanner scanner = new Scanner(is).useDelimiter("\\A");
        return scanner.hasNext() ? scanner.next() : "";
    }
}
