package com.lastmans.swordpedia.History;


public class Sword {
    private int imageResourceId; // Store the drawable resource ID locally
    private String description;   // Store the description from Firebase

    // Constructors, getters, and setters...

    public Sword() {
        // Required empty public constructor for Firebase
    }

    public Sword(int imageResourceId, String description) {
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
