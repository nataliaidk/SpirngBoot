package com.example.kotki;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class AuthorsService implements IAuthorsService {
    private static List<Authors> authorsRepo = new ArrayList<>();
    private static int currentId = 3;

    static {
        authorsRepo.add(new Authors(1, "Henryk Sienkiewicz"));
        authorsRepo.add(new Authors(2, "Stanisław Reymont"));
        authorsRepo.add(new Authors(3, "Adam Mickiewicz"));
    }

    public Collection<Authors> getAuthors() {
        return authorsRepo;
    }

    public Authors getAuthor(int id) {
        return authorsRepo.stream()
                .filter(a -> a.getId() == id)
                .findAny()
                .orElse(null);
    }

    public Authors addAuthor(String name) {
        Authors newAuthors = new Authors(++currentId, name);
        authorsRepo.add(newAuthors);
        return newAuthors;
    }

    public Authors updateAuthor(int id, Authors authors) {
        for (Authors existingAuthors : authorsRepo) {
            if(existingAuthors.getId() == id) {
                existingAuthors.setName(authors.getName());
                return existingAuthors;
            }
        }
        return null;
    }

    public boolean deleteAuthor(int id) {
        return authorsRepo.removeIf(a -> a.getId() == id);
    }
}
