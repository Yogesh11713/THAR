package com.techknightsrtu.thar.models;

public class Sponsor {
    private String name,image,description;

    public Sponsor() {
    }

    public Sponsor(String name, String image) {
        this.name = name;
        this.image = image;

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
