package com.example.kotki;


public class Readers {
    private int id;
    private String name;

    public Readers(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Readers() {}

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
