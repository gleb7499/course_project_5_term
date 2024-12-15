package com.example.lifeline.models;

public class Info {
    private String value;
    private int image;

    public Info(String value, int image) {
        this.value = value;
        this.image = image;
    }

    public int getImage() {
        return image;
    }

    public String getValue() {
        return value;
    }
}
