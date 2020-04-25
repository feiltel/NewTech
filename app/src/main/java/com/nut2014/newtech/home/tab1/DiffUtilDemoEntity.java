package com.nut2014.newtech.home.tab1;

public class DiffUtilDemoEntity {
    public DiffUtilDemoEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    private int id;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
