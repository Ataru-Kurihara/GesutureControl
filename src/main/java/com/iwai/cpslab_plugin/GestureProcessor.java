package com.iwai.cpslab_plugin;

import com.google.gson.Gson;
import com.iwai.cpslab_plugin.Model.Data;
import com.iwai.cpslab_plugin.Utils.PlayerUtil;
import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;

public class GestureProcessor {
    private String i2cGesture;
    private String groveGesture;

    public void processWebSocketData(String text, Player player) {
        Gson gson = new Gson();
        Data data = gson.fromJson(text, Data.class);
        i2cGesture = (data.gesture_R != null) ? data.gesture_R : data.gesture_L;
        groveGesture = data.gesture;
        if (i2cGesture != null && groveGesture != null) {
            performAction(i2cGesture, groveGesture, player);
        }
    }

    private void performAction(String i2cGesture, String groveGesture, Player player) {
        switch (i2cGesture) {
            case "Up", "Down":
                if (groveGesture.equals("Rotate Right") || groveGesture.equals("Rotate Left")) {
                    player.sendMessage("変更します");
                }
                break;
            case "Right":
                if (groveGesture.equals("3-finger")) {
                    PlayerUtil.MovePlayer(player, groveGesture);
                } else if (groveGesture.equals("1-finger")) {
                    PlayerUtil.MovePlayer(player, groveGesture);
                }
                break;
            case "Left":
                if (groveGesture.equals("1-finger")) {
                    PlayerUtil.MovePlayer(player, groveGesture);
                } else if (groveGesture.equals("3-finger")) {
                    PlayerUtil.MovePlayer(player, groveGesture);
                }
                break;
            case "Forward":
                if (groveGesture.equals("Rotate Right")) {
                    PlayerUtil.PlayerRotation(player, 10, 0);
                } else if (groveGesture.equals("Rotate Left")) {
                    PlayerUtil.PlayerRotation(player, -10, 0);
                }
                break;
        }
    }

    public CompletableFuture<Void> waitForGesture(Player player) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        CompletableFuture<Void> waitingTask = CompletableFuture.runAsync(() -> {
            while (i2cGesture == null || groveGesture == null) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        waitingTask.thenRun(() -> {
            performAction(i2cGesture, groveGesture, player);
            future.complete(null);
        });

        return future;
    }
}
