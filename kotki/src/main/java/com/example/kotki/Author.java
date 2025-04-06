package com.example.kotki;

public class Author {
    private int id;
    private String name;

    //author probably shouldn't have a set of the books he authored

    public Author(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public Author(){}
    public int getId() {return id;}
    
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
}
