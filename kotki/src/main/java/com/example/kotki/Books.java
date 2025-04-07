package com.example.kotki;

public class Books {
    private int id;
    private String title;
    private Authors authors;
    private int pages;
    public Books(int id, String title, Authors authors, int pages) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.pages = pages;  }
    public Books() {}
    public int getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public Authors getAuthor() { return authors; }
    public void setAuthor(Authors authors) { this.authors = authors; }
    public int getPages() { return pages; }
    public void setPages(int pages) { this.pages = pages; }
}