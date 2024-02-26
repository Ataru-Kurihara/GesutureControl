package com.iwai.cpslab_plugin.Utils;

import com.google.gson.Gson;
import com.iwai.cpslab_plugin.Model.Data;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class ControlMode {
    static Gson gson;

    public Data data;

    private static String lastGesture = null;

    public ControlMode() {
        gson = new Gson();
    }

    public Data getData(String text) {
        Gson gson = new Gson();
        return gson.fromJson(text, Data.class);
    }

    public String getI2cGesture(String text) {
        Data data = getData(text);
        return (data.gesture_R != null) ? data.gesture_R : data.gesture_L;
    }

    public String groveGesture(String text) {
        Data data = getData(text);
        return data.gesture;
    }
    
    public static void changeMode(String text, Player player) {
        Gson gson = new Gson();
        Data data = gson.fromJson(text, Data.class);
        String i2cGesture = new ControlMode().getI2cGesture(text);
        String groveGesture = new ControlMode().groveGesture(text);
        if (i2cGesture != null && !i2cGesture.equals(lastGesture)) {
            lastGesture = i2cGesture;
            switch (i2cGesture) {
                case "Up":
                    if (data.gesture_R != null || data.gesture_L != null) {
                        player.sendMessage("上向に視点を変更");
                        if (groveGesture != null) {
                            if (groveGesture.equals("Rotate Right") || groveGesture.equals("Rotate Left")) {
                                PlayerUtil.PlayerRotation(player, 0, -10);
                            }
                        }
                    }
                    break;
                case "Down":
                    if (data.gesture_R != null || data.gesture_L != null) {
                        player.sendMessage("下向に視点を変更");
                        if (groveGesture != null) {
                            if (groveGesture.equals("Rotate Right") || groveGesture.equals("Rotate Left")) {
                                PlayerUtil.PlayerRotation(player, 0, 10);
                            }
                        }
                    }
                    break;
                case "Right":
                    if (data.gesture_R != null) {
                        player.sendMessage("後ろ向きに移動");
                        if (groveGesture != null) {
                            PlayerUtil.backWardPlayer(player, groveGesture);
                        }
                    } else {
                      player.sendMessage("前向きに移動");
                      if (groveGesture != null) {
                          PlayerUtil.MovePlayer(player, groveGesture);
                      }
                    }
                    break;
                case "Left":
                    if (data.gesture_R != null && groveGesture != null) {
                        player.sendMessage("前向きに移動");
                        PlayerUtil.MovePlayer(player, groveGesture);

                        break;
                    } else {
                        player.sendMessage("後ろ向きに移動");
                        if (groveGesture != null) {
                            PlayerUtil.backWardPlayer(player, groveGesture);
                        }
                    }
                    break;
                case "Forward":
                    if (data.gesture_R != null) {
                        player.sendMessage("右向きに視点を変更");
                        if (groveGesture != null) {
                            if (groveGesture.equals("Rotate Right")) {
                                PlayerUtil.PlayerRotation(player, 10, 0);
                            }
                        }
                    } else {
                        player.sendMessage("左向きに視点を変更");
                        if (groveGesture != null) {
                            if (groveGesture.equals("Rotate Left")) {
                                PlayerUtil.PlayerRotation(player, -10, 0);
                            }
                        }
                    }
                    break;
            }
        }

    }

}
