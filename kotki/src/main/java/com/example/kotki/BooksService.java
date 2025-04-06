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
    public Book addBook(BookRequestDTO dto) {
        Author author = authorService.getAuthor(dto.getAuthorId());
        if (author == null) {
            return null;
        }

        Book book = new Book(++currentId, dto.getTitle(), author, dto.getPages());
        booksRepo.add(book);
        return book;
    }

    public Book updateBook(int id, BookRequestDTO dto) {
        Author author = authorService.getAuthor(dto.getAuthorId());
        if (author == null) {
            return null;
        }

        for (Book b : booksRepo) {
            if (b.getId() == id) {
                b.setTitle(dto.getTitle());
                b.setAuthor(author);
                b.setPages(dto.getPages());
                return b;
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
