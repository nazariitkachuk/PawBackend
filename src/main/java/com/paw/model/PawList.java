package com.paw.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "paw_lists")
public class PawList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int listId;
    private String name;
    @ElementCollection
    private List<Integer> pawCardList = new ArrayList<>();

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

    public List<Integer> getPawCardList() {
        return pawCardList;
    }

    public void addIdToList(int id){
        pawCardList.add(id);
    }

    public void deleteIdFromList(int id) {
        pawCardList.remove(pawCardList.indexOf(id));
    }

    public void setPawCardList(List<Integer> pawCardList) {
        this.pawCardList = pawCardList;
    }

    @Override
    public String toString() {
        return "PawList{" +
                "id=" + listId +
                ", name='" + name + '\'' +
                '}';
    }
}
