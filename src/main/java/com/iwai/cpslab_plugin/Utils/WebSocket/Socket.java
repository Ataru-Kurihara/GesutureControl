package com.iwai.cpslab_plugin.Utils.WebSocket;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;

@ClientEndpoint
@ServerEndpoint("/data")
public class Socket {
    private final CountDownLatch latch = new CountDownLatch(1);

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Connected to endpoint: " + session);
    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
        System.out.println("Message received: " + message);

        if (message.toLowerCase(Locale.US).contains("bye")) {
            session.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "Client requested close"));
        }
    }

    @OnClose
    public void onClose(CloseReason reason) {
        System.out.println("Connection closed: " + reason);
        latch.countDown();
    }

    @OnError
    public void onError(Throwable throwable) {
        System.out.println("Connection error: " + throwable.getMessage());
    }

    public void awaitClosure() throws InterruptedException {
        System.out.println("Awaiting closure from server...");
        latch.await();
    }

}
