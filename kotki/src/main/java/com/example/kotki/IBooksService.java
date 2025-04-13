package com.example.kotki;

import org.springframework.data.domain.Page;

public interface IBooksService {

    public abstract Page<Books> getBooks(int page, int size) ;

    //read - 2 gets
    public abstract Books getBook(int id);

    //create - 1 post
    public abstract Books addBook(BooksRequestDTO dto);

    //update - put, not patch
    public abstract Books updateBook(int id, BooksRequestDTO book);

    //delete - delete
    public abstract boolean deleteBook(int id);
}