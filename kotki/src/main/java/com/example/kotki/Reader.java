package com.example.kotki;


public class Reader {
    private int id;
    private String name;

    public Reader(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Reader() {}

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
