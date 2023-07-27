package com.iwai.cpslab_plugin.Utils.WebSocket;



import com.google.gson.Gson;
import com.iwai.cpslab_plugin.CpsLab_Plugin;
import com.iwai.cpslab_plugin.Model.Data;
import com.iwai.cpslab_plugin.Utils.DataUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import okhttp3.*;
import org.bukkit.util.Vector;
import org.json.JSONObject;

import java.util.Objects;

public class Client {
    private WebSocket webSocket;
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
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        Location location = player.getLocation();
                        Vector vector = location.getDirection();
                        int maxDistance = 1;
                        Location targetLocation = location.clone().add(vector.multiply(maxDistance));
                        Block block = targetLocation.getBlock();
                        Block mainThreadBlock = block.getWorld().getBlockAt(block.getLocation());
                        Location x = mainThreadBlock.getLocation().clone().add(1,0,0);
                        Location y = mainThreadBlock.getLocation().clone().add(0,1,0);
                        Location z = mainThreadBlock.getLocation().clone().add(-1,0,0);
                        Location playerLocation = player.getLocation();
                        float currentYaw = playerLocation.getYaw();
                        float RightYaw = currentYaw + 90;
                        float LeftYaw = currentYaw - 90;
                        if (data.type.equals("gesture")) {
                            switch ((String) data.value) {
                                case  "Up" -> {
                                    Bukkit.getScheduler().runTask(plugin, () -> {
                                        player.setVelocity(new Vector(0, 2, 0));
                                    });
                                }
                                case "Down" -> {
                                    Bukkit.getScheduler().runTask(plugin, () -> {
                                        y.add(0,1,0);
                                        if (mainThreadBlock.getType() == Material.AIR) {
                                            mainThreadBlock.setType(Material.DIAMOND_BLOCK);
                                        } else {
                                            mainThreadBlock.setType(Material.AIR);
                                        }
                                        y.add(0,-1,0);
//                                        player.setVelocity(new Vector(0, -2, 0));
                                    });
                                }
                                case "Forward" -> {
                                    Bukkit.getScheduler().runTask(plugin, () -> {
                                        player.setVelocity(player.getLocation().getDirection().multiply(2));
                                    });
                                }
                                case "Backward" -> {
                                    Bukkit.getScheduler().runTask(plugin, () -> {
                                        player.setVelocity(player.getLocation().getDirection().multiply(-2));
                                    });
                                }
                                case "Right" -> {
                                    Bukkit.getScheduler().runTask(plugin, () -> {
                                        playerLocation.setYaw(RightYaw);
                                        player.teleport(playerLocation);
                                    });
                                }
                                case "Left" -> {
                                    Bukkit.getScheduler().runTask(plugin, () -> {
                                        playerLocation.setYaw(LeftYaw);
                                        player.teleport(playerLocation);
                                    });
                                }
                                case "WaveSlowlyLeftRight" -> {
                                    Bukkit.getScheduler().runTask(plugin, () -> {
                                        player.sendMessage("こんにちわ");
                                    });
                                }
                            }
                        } else {
                            return;

//                            if ((double)data.value <= 400) {
//                                if (repeat) {
//                                    Bukkit.getScheduler().runTask(plugin, () -> {
//                                        player.sendMessage("距離 " + data.value);
//                                    });
//                                }
//                                repeat = true;
//                            } else {
//                                repeat = false;
//                            }
                        }



//                        if (data.type.equals("gesture")) {
//                            switch ((String) data.value) {
//                                case "Up" -> {
//                                    if (!swungUp) {
//                                        player.sendMessage("振り上げ");
//                                        Bukkit.getScheduler().runTask(plugin, () -> {
//                                            x.getBlock().setType(Material.DIAMOND_BLOCK);
//                                            for (int i = 0; i <= 10; i++) {
//                                                x.add(0, 1, 0);
//                                                y.add(0, 1, 0);
//                                                z.add(0, 1, 0);
//                                                x.getBlock().setType(Material.DIAMOND_BLOCK);
//                                                y.getBlock().setType(Material.DIAMOND_BLOCK);
//                                                z.getBlock().setType(Material.DIAMOND_BLOCK);
//                                            }
//                                        });
//                                        swungUp = true;
//                                    }
//                                }
//                                case "Down" -> {
//                                    player.sendMessage("振り下ろし");
//                                    Bukkit.getScheduler().runTask(plugin, () -> {
//                                        if (mainThreadBlock.getType() != Material.AIR) {
//                                            for (int i = 0; i <= 10; i++) {
//                                                x.add(0, 1, 0);
//                                                y.add(0, 1, 0);
//                                                z.add(0, 1, 0);
//                                                x.getBlock().setType(Material.AIR);
//                                                y.getBlock().setType(Material.AIR);
//                                                z.getBlock().setType(Material.AIR);
//                                            }
//                                        } else {
//                                            player.sendMessage(mainThreadBlock.getLocation().toString());
//                                            player.sendMessage(mainThreadBlock.getType().name());
//                                            player.sendMessage("ブロックがありません");
//                                        }
//                                    });
//                                    swungUp = false;
//                                }
//                                case "Forward" -> {
//                                    player.sendMessage("前進");
//                                    player.sendMessage(data.type);
//                                    if (data.type.equals("distance")) {
//                                        player.sendMessage("距離");
//                                        double distance = (double) data.value;
//                                        while (distance <= 400.0) {
//                                            Bukkit.getScheduler().runTask(plugin, () -> {
//                                                player.setVelocity(player.getLocation().getDirection().multiply(2));
//                                            });
//                                            distance = (double) data.value;
//                                            if (distance >= 400.0) {
//                                                break;
//                                            }
//                                        }
//                                        Bukkit.getScheduler().runTask(plugin, () -> {
//                                            player.setVelocity(player.getLocation().getDirection().multiply(2));
//                                        });
//                                    }
//                                }
//                                case "Backward" -> {
//                                    player.sendMessage("後退");
//                                    Bukkit.getScheduler().runTask(plugin, () -> {
//                                        player.setVelocity(player.getLocation().getDirection().multiply(-2));
//                                    });
//                                }
//                                case "Right" -> {
//                                    player.sendMessage("右");
//                                    Bukkit.getScheduler().runTask(plugin, () -> {
//                                        playerLocation.setYaw(RightYaw);
//                                        player.teleport(playerLocation);
//                                    });
//                                }
//                                case "Left" -> {
//                                    player.sendMessage("左");
//                                    Bukkit.getScheduler().runTask(plugin, () -> {
//                                        playerLocation.setYaw(LeftYaw);
//                                        player.teleport(playerLocation);
//                                    });
//                                }
//                                case "WaveSlowlyLeftRight" -> {
//                                    player.sendMessage("ゆっくり左右");
//                                    Bukkit.getScheduler().runTask(plugin, () -> {
//                                        player.sendMessage("こんにちわ");
//                                    });
//                                }
//                            }
//                        }
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
