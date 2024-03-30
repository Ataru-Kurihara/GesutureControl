package com.iwai.cpslab_plugin.Utils.WebSocket;



import com.google.gson.Gson;
import com.iwai.cpslab_plugin.CpsLab_Plugin;
import com.iwai.cpslab_plugin.GestureProcessor;
import com.iwai.cpslab_plugin.Model.Data;
import com.iwai.cpslab_plugin.Utils.Grove.Gesture;
import com.iwai.cpslab_plugin.Utils.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import okhttp3.*;
import org.bukkit.util.Vector;


public class Client {
    private WebSocket webSocket;
    private String lastGesture = null;
    private MessageCallback messageCallback;

    private boolean swungUp = false;

    public float accX;
    public float accY;
    public float accZ;

    private Gson gson = new Gson();
    private boolean repeat = false;
    public void connectWebSocket(String url, Player player, CpsLab_Plugin plugin) {
        Request request = new Request.Builder().url(url).build();
        OkHttpClient client = new OkHttpClient();
        GestureProcessor gestureProcessor = new GestureProcessor();
        webSocket = client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                System.out.println("onOpen");
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
//                JSONObject jsonObject = new JSONObject(text);
//                System.out.println(jsonObject.getString("type"));
                Data data = gson.fromJson(text, Data.class);
                String gesture = (data.gesture_R != null) ? data.gesture_R : data.gesture_L;
                String groveGesture = data.gesture;
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        Location location = player.getLocation();
                        Vector vector = location.getDirection();
                        int maxDistance = 1;
                        Location targetLocation = location.clone().add(vector.multiply(maxDistance));
                        Block block = targetLocation.getBlock();
                        Block mainThreadBlock = block.getWorld().getBlockAt(block.getLocation());
                        Block targetBlock = player.getTargetBlock(null, maxDistance);

                        Location playerLocation = player.getLocation();
                        float currentYaw = playerLocation.getYaw();
                        float RightYaw = currentYaw + 90;
                        float LeftYaw = currentYaw - 90;
//                        ControlMode.changeMode(text, player);

                        if (data.gesture != null) {
                            Gesture.getGroveGesture(groveGesture, player);
                        }
                        if (gesture != null) {
                            lastGesture = gesture;
                            switch (gesture) {
                                case "Up":
                                    if (data.gesture_R != null) runRotationTask(0, -10);
                                    else runRotationTask(0, -10);
                                    break;
                                case "Down":
                                    if (data.gesture_R != null) runRotationTask(0, 10);
                                    else runRotationTask(0, 10);
                                    break;
                                case "Right":
                                    //backward
                                    if (data.gesture_R != null) runMoveTask(-2.0);
                                    //forward
                                    else runMoveTask(1.0);
                                    break;
                                case "Left":
                                    if (data.gesture_R != null) runMoveTask(1.0);
                                    else runMoveTask(-2.0);
                                    break;
                                case "Forward":
                                    if (data.gesture_R != null) runRotationTask(10, 0);
                                    else runRotationTask(-10, 0);
                                    break;
                            }
                        }

                    }

                    private void runRotationTask(float deltaYaw, float deltaPitch) {
                        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                            PlayerUtil.PlayerRotation(player, deltaYaw, deltaPitch);
                        });
                    }

                    private void runMoveTask(double speed) {
                        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                            Vector direction = player.getLocation().getDirection().normalize();
                            if (speed < 0) direction.multiply(-1);
                            Vector velocity = direction.multiply(Math.abs(speed));
                            player.setVelocity(velocity);
                        });
                    }
                }.runTask(plugin);
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                System.out.println("onClosed");
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                System.out.println("onFailure");
            }
        });
    }


    public void clientClose() {
        if (webSocket != null) {
            webSocket.close(1000, "Closing connection");
        }

    }

}
