package com.example.lifeline.models;

public class RecyclerViewModel {
    private String value;
    private int image;

    public RecyclerViewModel(String value, int image) {
        this.value = value;
        this.image = image;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
