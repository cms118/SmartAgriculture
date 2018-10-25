package com.example.shiva.smartagriculture.features;

/**
 * Created by shiva on 25-10-2018.
 */

public class Items {

    private String name;
    private int thumbnail;

    public Items(String name, int thumbnail) {
        this.name = name;
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

}
