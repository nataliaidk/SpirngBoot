package com.example.kotki;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;

@Service
public class AuthorsService implements IAuthorsService {
    private static List<Authors> authorsRepo = new ArrayList<>();
    private static int currentId = 7;

    static {
        authorsRepo.add(new Authors(1, "Henryk Sienkiewicz"));
        authorsRepo.add(new Authors(2, "Stanisław Reymont"));
        authorsRepo.add(new Authors(3, "Adam Mickiewicz"));
        authorsRepo.add(new Authors(4, "Juliusz Słowacki"));
        authorsRepo.add(new Authors(5, "Bolesław Prus"));
        authorsRepo.add(new Authors(6, "Wisława Szymborska"));
        authorsRepo.add(new Authors(7, "Adam Małysz"));
    }

    public Page<Authors> getAuthors(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        int start = Math.min((int) pageable.getOffset(), authorsRepo.size());
        int end = Math.min((start + pageable.getPageSize()), authorsRepo.size());
        List<Authors> paginatedList = authorsRepo.subList(start, end);
        return new PageImpl<>(paginatedList, pageable, authorsRepo.size());
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
