package com.paw.model;

import javax.persistence.*;

@Entity
@Table(name = "paw_cards")
public class PawCard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int cardId;
    private String name;
    private String description;

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
