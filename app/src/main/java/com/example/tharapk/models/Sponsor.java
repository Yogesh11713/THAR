package com.example.tharapk.models;

public class Sponsor {
    private String name,image;

    public Sponsor() {
    }

    public Sponsor(String name, String description) {
        this.name = name;
        this.image = image;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
