package com.paw.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@javax.persistence.Table(name = "paw_tables")
public class PawTable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int tableId;
    private String name;
    private String owner;
    @ElementCollection
    private List<Integer> pawLists = new ArrayList<>();

    @ElementCollection
    private List<Integer> coloursList = new ArrayList<>();

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getPawLists() {
        return pawLists;
    }

    public void setPawLists(List<Integer> pawLists) {
        this.pawLists = pawLists;
    }

    public void addIdToList(int id) {
        pawLists.add(id);
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public List<Integer> getColoursList() {
        return coloursList;
    }

    public void setColoursList(List<Integer> coloursList) {
        this.coloursList = coloursList;
    }

    @Override
    public String toString() {
        return "Table{" +
                "id=" + tableId +
                ", name='" + name + '\'' +
                '}';
    }
}
