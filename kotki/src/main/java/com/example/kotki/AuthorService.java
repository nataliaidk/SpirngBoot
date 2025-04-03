package com.example.kotki;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class AuthorService {
    private static List<Author> authorsRepo = new ArrayList<>();
    private static int currentId = 3;

    static {
        authorsRepo.add(new Author(1, "Henryk Sienkiewicz"));
        authorsRepo.add(new Author(2, "Stanisław Reymont"));
        authorsRepo.add(new Author(3, "Adam Mickiewicz"));
    }

    public Collection<Author> getAuthors() {
        return authorsRepo;
    }

    public Author getAuthor(int id) {
        return authorsRepo.stream()
                .filter(a -> a.getId() == id)
                .findAny()
                .orElse(null);
    }

    public Author addAuthor(String name) {
        Author newAuthor = new Author(++currentId, name);
        authorsRepo.add(newAuthor);
        return newAuthor;
    }

    public Author updateAuthor(int id, Author author) {
        for (Author existingAuthor : authorsRepo) {
            if(existingAuthor.getId() == id) {
                existingAuthor.setName(author.getName());
                return existingAuthor;
            }
        }
        return null;
    }

    public boolean deleteAuthor(int id) {
        return authorsRepo.removeIf(a -> a.getId() == id);
    }
}
