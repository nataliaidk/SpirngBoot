package com.example.kotki;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Service
public class BooksService implements IBooksService {
    private static List<Book> booksRepo = new ArrayList<>();
    private static int currentId = 3;

    @Autowired
    private AuthorService authorService;

    @PostConstruct
    private void initBooks() {
        booksRepo.add(new Book(1, "Potop", authorService.getAuthor(1), 936));
        booksRepo.add(new Book(2, "Wesele", authorService.getAuthor(2), 150));
        booksRepo.add(new Book(3, "Dziady", authorService.getAuthor(3), 292));
    }

    //read - 2 gets
    @Override
    public Collection<Book> getBooks() {
        return booksRepo;
    }

    @Override
    public Book getBook(int id) {
        return booksRepo.stream()
                .filter(b -> b.getId() == id)
                .findAny()
                .orElse(null);
    }

    //create - 1 post
    //zabezpieczenie/walidacja?
    @Override
    public Book addBook(String title, Author author, int pages) {
        Book newBook = new Book(++currentId, title, author, pages);
        booksRepo.add(newBook);
        return newBook;
    }

    //update - put, not patch
    @Override
    public Book updateBook(int id, Book book) {
        for (Book existingBook : booksRepo) {
            if (existingBook.getId() == id) { //keep id
                existingBook.setTitle(book.getTitle());
                existingBook.setAuthor(book.getAuthor());
                existingBook.setPages(book.getPages());
                return existingBook;
            }
        }
        return null;
    }
    


    //delete - delete
    @Override
    public boolean deleteBook(int id) {
        return booksRepo.removeIf(b -> b.getId() == id);
    }
}
