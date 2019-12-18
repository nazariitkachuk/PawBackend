package com.paw.model;

import javax.persistence.*;

@Entity
@Table(name = "colour")
public class Colour {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int colourId;

    String colourName;
    String ColourHex;

    public Colour() {
    }

    public Colour(String colourName, String colourHex) {
        this.colourName = colourName;
        ColourHex = colourHex;
    }

    public int getColourId() {
        return colourId;
    }

    public void setColourId(int colourId) {
        this.colourId = colourId;
    }

    public String getColourName() {
        return colourName;
    }

    public void setColourName(String colourName) {
        this.colourName = colourName;
    }

    public String getColourHex() {
        return ColourHex;
    }

    public void setColourHex(String colourHex) {
        ColourHex = colourHex;
    }
}
