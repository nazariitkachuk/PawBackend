package com.paw.model;

import javax.persistence.*;

import com.beust.jcommander.internal.Nullable;

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
    @Nullable
    private boolean status = true;
    @Nullable
    private int belongsToListId = 0;
    @ElementCollection
    private List<Integer> pawLists = new ArrayList<>();
    @ElementCollection
    List<Integer> historyIdList = new ArrayList<>();
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

    public void deleteIdFromList(int id) {
        pawLists.remove(pawLists.indexOf(id));
    }

    public void addIdToHistoryList(int id) {
        historyIdList.add(id);
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getBelongsToListId() {
        return belongsToListId;
    }

    public void setBelongsToListId(int belongsToListId) {
        this.belongsToListId = belongsToListId;
    }

    @Override
    public String toString() {
        return "Table{" +
                "id=" + tableId +
                ", name='" + name + '\'' +
                '}';
    }
}
