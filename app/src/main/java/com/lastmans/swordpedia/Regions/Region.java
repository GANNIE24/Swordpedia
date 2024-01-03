package com.lastmans.swordpedia.Regions;

public class Region {

    private int imageResourceId;  // Resource ID for the drawable
    private String description;   // Description from the Realtime Database

    public Region() {
        // Default constructor required for DataSnapshot.getValue(Region.class)
    }

    public Region(int imageResourceId, String description) {
        this.imageResourceId = imageResourceId;
        this.description = description;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
