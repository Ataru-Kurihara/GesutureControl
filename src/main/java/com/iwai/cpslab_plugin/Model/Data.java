package com.iwai.cpslab_plugin.Model;

public class Data {
    public String type;
    public Object value;

    public Data(String type, Object value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
