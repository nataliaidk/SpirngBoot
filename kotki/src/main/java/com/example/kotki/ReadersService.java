package com.example.kotki;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;



@Service
public class ReadersService implements IReadersService {
    private static List<Readers> readersRepo = new ArrayList<>();
    private static int currentId = 3;

    static {
        readersRepo.add(new Readers(1, "Jan Kowalski"));
        readersRepo.add(new Readers(2, "Anna Nowak"));
        readersRepo.add(new Readers(3, "Piotr Zieliński"));
        readersRepo.add(new Readers(4, "Katarzyna Wiśniewska"));
        readersRepo.add(new Readers(5, "Tomasz Malinowski"));
        readersRepo.add(new Readers(6, "Monika Nowicka"));
        readersRepo.add(new Readers(7, "Wojciech Lewandowski"));
    }

    public Page<Readers> getReaders(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        int start = Math.min((int) pageable.getOffset(), readersRepo.size());
        int end = Math.min((start + pageable.getPageSize()), readersRepo.size());
        List<Readers> paginatedList = readersRepo.subList(start, end);
        return new PageImpl<>(paginatedList, pageable, readersRepo.size());
    }
    public Readers getReader(int id) {
        return readersRepo.stream()
                .filter(r -> r.getId() == id)
                .findAny()
                .orElse(null);
    }

    public Readers addReader(String name) {
        Readers newReaders = new Readers(++currentId, name);
        readersRepo.add(newReaders);
        return newReaders;
    }

    public Readers updateReader(int id, Readers readers) {
        for (Readers existing : readersRepo) {
            if (existing.getId() == id) {
                existing.setName(readers.getName());
                return existing;
            }
        }
        return null;
    }

    public boolean deleteReader(int id) {
        return readersRepo.removeIf(r -> r.getId() == id);
    }
}
