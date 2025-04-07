package com.example.kotki;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Service
public class BooksService implements IBooksService {
    private static List<Books> booksRepo = new ArrayList<>();
    private static int currentId = 3;

    @Autowired
    private AuthorService authorService;

    @PostConstruct
    private void initBooks() {
        booksRepo.add(new Books(1, "Potop", authorService.getAuthor(1), 936));
        booksRepo.add(new Books(2, "Wesele", authorService.getAuthor(2), 150));
        booksRepo.add(new Books(3, "Dziady", authorService.getAuthor(3), 292));
    }

    //read - 2 gets
    @Override
    public Collection<Books> getBooks() {
        return booksRepo;
    }

    @Override
    public Books getBook(int id) {
        return booksRepo.stream()
                .filter(b -> b.getId() == id)
                .findAny()
                .orElse(null);
    }

    //create - 1 post
    //zabezpieczenie/walidacja?
    public Books addBook(BookRequestDTO dto) {
        Authors authors = authorService.getAuthor(dto.getAuthorId());
        if (authors == null) {
            return null;
        }

        Books books = new Books(++currentId, dto.getTitle(), authors, dto.getPages());
        booksRepo.add(books);
        return books;
    }

    public Books updateBook(int id, BookRequestDTO dto) {
        Authors authors = authorService.getAuthor(dto.getAuthorId());
        if (authors == null) {
            return null;
        }

        for (Books b : booksRepo) {
            if (b.getId() == id) {
                b.setTitle(dto.getTitle());
                b.setAuthor(authors);
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
