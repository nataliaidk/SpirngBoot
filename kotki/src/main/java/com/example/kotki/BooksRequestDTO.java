package com.example.kotki;

public class BooksRequestDTO {
    private String title;
    private int authorId;
    private int pages;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getAuthorId() { return authorId; }
    public void setAuthorId(int authorId) { this.authorId = authorId; }

    public int getPages() { return pages; }
    public void setPages(int pages) { this.pages = pages; }
}
