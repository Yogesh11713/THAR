package com.techknightsrtu.thar.models;

public class EventList {

   private String eventName;
   private String categoryName;
   private String eventKey;

    public EventList(String eventName, String categoryName, String eventKey) {
        this.eventName = eventName;
        this.categoryName = categoryName;
        this.eventKey = eventKey;
    }

    public EventList(String eventName, int background,String eventKey) {
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }
}
