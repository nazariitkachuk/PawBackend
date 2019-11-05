package com.paw.model;

import javax.persistence.*;

@Entity
@Table(name = "paw_lists")
public class PawList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;

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

    @Override
    public String toString() {
        return "PawList{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
