package com.example.kotki;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class BooksService implements IBooksService {
    private static List<Books> booksRepo = new ArrayList<>();
    private static int currentId = 3;

    @Autowired
    private AuthorsService authorService;

    @PostConstruct
    private void initBooks() {
        booksRepo.add(new Books(1, "Potop", authorService.getAuthor(1), 936));
        booksRepo.add(new Books(2, "Wesele", authorService.getAuthor(2), 150));
        booksRepo.add(new Books(3, "Dziady", authorService.getAuthor(3), 292));
        booksRepo.add(new Books(4, "Book 4", authorService.getAuthor(4), 400));
        booksRepo.add(new Books(5, "Book 5", authorService.getAuthor(5), 250));
        booksRepo.add(new Books(6, "Book 6", authorService.getAuthor(6), 320));
        booksRepo.add(new Books(7, "Book 7", authorService.getAuthor(7), 180));
        booksRepo.add(new Books(8, "Book 8", authorService.getAuthor(6), 320));
        booksRepo.add(new Books(9, "Book 9", authorService.getAuthor(7), 180));
        booksRepo.add(new Books(10, "Book 10", authorService.getAuthor(1), 320));
        booksRepo.add(new Books(11, "Book 11", authorService.getAuthor(2), 180));
    }

    @Override
    public Collection<Books> getAllBooks() {
        return booksRepo;
    }

    //read - 2 gets
    @Override
    public Page<Books> getBooks(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        int start = Math.min((int) pageable.getOffset(), booksRepo.size());
        int end = Math.min((start + pageable.getPageSize()), booksRepo.size());
        List<Books> paginatedList = booksRepo.subList(start, end);
        return new PageImpl<>(paginatedList, pageable, booksRepo.size());
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
    public Books addBook(BooksRequestDTO dto) {
        Authors authors = authorService.getAuthor(dto.getAuthorId());
        if (authors == null) {
            return null;
        }

        Books books = new Books(++currentId, dto.getTitle(), authors, dto.getPages());
        booksRepo.add(books);
        return books;
    }

    public Books updateBook(int id, BooksRequestDTO dto) {
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
