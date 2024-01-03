package com.lastmans.swordpedia.Types;


public class SwordType  {
    // Public fields
    private int imageResourceId; // New field for drawable resource ID
    public String typeName;

    // Default constructor (required by Firebase)
    public SwordType() {
        // Default constructor is needed for Firebase
    }

    // Parameterized constructor
    public SwordType(int imageResourceId, String typeName) {
        this.imageResourceId = imageResourceId;
        this.typeName = typeName;
    }

    // Getter for imageResourceId
    public int getImageResourceId() {
        return imageResourceId;
    }

    // Setter for imageResourceId
    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }

    // Getter for typeName
    public String getTypeName() {
        return typeName;
    }

    // Setter for typeName
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
