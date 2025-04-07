package com.example.kotki;

public class Authors {
    private int id;
    private String name;

    //author probably shouldn't have a set of the books he authored

    public Authors(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public Authors(){}
    public int getId() {return id;}
    
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
}
