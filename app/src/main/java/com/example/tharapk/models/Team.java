package com.example.tharapk.models;

public class Team {
    private String name, Title,image;
    private String linkedin, instagram, github;

    public Team() {
    }

    public Team(String name, String title, String image, String linkedin, String instagram, String github) {
        this.name = name;
        Title = title;
        this.image = image;
        this.linkedin = linkedin;
        this.instagram = instagram;
        this.github = github;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getGithub() {
        return github;
    }

    public void setGithub(String github) {
        this.github = github;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return Title;
    }
//
    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
