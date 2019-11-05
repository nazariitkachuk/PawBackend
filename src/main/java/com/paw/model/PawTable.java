package com.paw.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@javax.persistence.Table(name = "paw_tables")
public class PawTable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    @OneToMany(targetEntity = PawList.class, fetch = FetchType.EAGER)
    private List<PawList> pawLists = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Table{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
