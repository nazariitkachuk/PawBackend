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
    @OneToMany(targetEntity = PawList.class, fetch = FetchType.EAGER)
    private List<PawList> pawLists = new ArrayList<>();

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

    public List<PawList> getPawLists() {
        return pawLists;
    }

    public void setPawLists(List<PawList> pawLists) {
        this.pawLists = pawLists;
    }

    public void addPawList(PawList pawList) {
        this.pawLists.add(pawList);
    }

    public void deletedFromPawList(PawList pawList) {this.pawLists.remove(pawList);}

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Table{" +
                "id=" + tableId +
                ", name='" + name + '\'' +
                '}';
    }
}
