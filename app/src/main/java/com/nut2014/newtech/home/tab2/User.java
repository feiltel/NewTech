package com.nut2014.newtech.home.tab2;

/**
 * @author feiltel 2020/4/26 0026
 */
public class User {
    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    private int id;

    public int getId() {
        return id;
    }

    public User setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    private String name;
}
