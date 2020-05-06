package com.techknightsrtu.thar.models;

public class EventCategory {
    private String categoryName;
    private int resourceId;

    public EventCategory(String categoryName, int resourceId) {
        this.categoryName = categoryName;
        this.resourceId = resourceId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }
}
