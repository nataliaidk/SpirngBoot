package com.example.kotki;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class ReaderService implements IReaderService {
    private static List<Readers> readersRepo = new ArrayList<>();
    private static int currentId = 3;

    static {
        readersRepo.add(new Readers(1, "Jan Kowalski"));
        readersRepo.add(new Readers(2, "Anna Nowak"));
        readersRepo.add(new Readers(3, "Piotr Zieliński"));
    }

    public Collection<Readers> getReaders() {
        return readersRepo;
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
