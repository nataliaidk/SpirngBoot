package com.example.kotki;

import java.util.Collection;
public interface IBooksService {
    public abstract Collection<Book> getBooks();

    //read - 2 gets
    public abstract Book getBook(int id);

    //create - 1 post
    public abstract Book addBook(String title, Author author, int pages);

    //update - put, not patch
    public abstract Book updateBook(int id, Book book);

    //delete - delete
    public abstract boolean deleteBook(int id);
}