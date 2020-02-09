package com.example.tharapk.ui.sponsors.Model;

public class Sponsor {
    private String name, description,image;

    public Sponsor() {
    }

    public Sponsor(String name, String description) {
        this.name = name;
//        this.description = description;
        this.image = image;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
