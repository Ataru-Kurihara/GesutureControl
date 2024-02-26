package com.iwai.cpslab_plugin.Model;

public class Data {

    public String gesture_R;

    public String gesture_L;
    public String gesture;
    public int dist;

    public Data(String gesture_R, String gesture_L ,int dist, String gesture) {
        this.gesture_R = gesture_R;
        this.gesture_L = gesture_L;
        this.dist = dist;
        this.gesture = gesture;

    }
}
