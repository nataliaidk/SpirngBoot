package com.example.kotki;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Service
public class BooksService implements IBooksService {
    private static List<Book> booksRepo = new ArrayList<>();
    private static int currentId = 3;

    static {
        booksRepo.add(new Book(1,"Potop", "Henryk Sienkiewicz", 936));
        booksRepo.add(new Book(2,"Wesele", "Stanisław Reymont", 150));
        booksRepo.add(new Book(3,"Dziady", "Adam Mickiewicz", 292));
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
    public Book addBook(String title, String author, int pages) {
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
        for (int i = 0; i < booksRepo.size(); i++) {
            if (booksRepo.get(i).getId() == id) {
                booksRepo.remove(i);
                return true;
            }
        }
        return false;
    }
}
