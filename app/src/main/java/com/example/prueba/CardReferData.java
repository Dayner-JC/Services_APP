package com.example.prueba;

public class CardReferData {
    private final boolean esPopular;
    private int imageID;
    private String name;

    public CardReferData(String name, int imageID, boolean esPopular) {
        this.name = name;
        this.imageID = imageID;
        this.esPopular = esPopular;
    }

    public String getName() {
        return this.name;
    }

    public boolean getEsPopular() {
        return this.esPopular;
    }

    public int getImage() {
        return this.imageID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }
}
