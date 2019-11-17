package com.paw.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "paw_lists")
public class PawList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int listId;
    private String name;
    @OneToMany(targetEntity = PawCard.class, fetch = FetchType.EAGER)
    private List<PawCard> pawCardList = new ArrayList<>();

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PawCard> getPawCardList() {
        return pawCardList;
    }

    public void setPawCardList(List<PawCard> pawCardList) {
        this.pawCardList = pawCardList;
    }

    public void addCardToCardList(PawCard pawCard) {
        this.pawCardList.add(pawCard);
    }

    public void deletedFromPawList(int cardId) {
        PawCard pawCard = this.pawCardList.stream().filter(card -> card.getCardId() == cardId).collect(Collectors.toList()).get(0);
        this.pawCardList.remove(pawCard);
    }

    @Override
    public String toString() {
        return "PawList{" +
                "id=" + listId +
                ", name='" + name + '\'' +
                '}';
    }
}
