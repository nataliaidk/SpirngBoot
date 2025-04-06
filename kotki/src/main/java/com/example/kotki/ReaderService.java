package com.example.kotki;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class ReaderService implements IReaderService {
    private static List<Reader> readersRepo = new ArrayList<>();
    private static int currentId = 3;

    static {
        readersRepo.add(new Reader(1, "Jan Kowalski"));
        readersRepo.add(new Reader(2, "Anna Nowak"));
        readersRepo.add(new Reader(3, "Piotr Zieliński"));
    }

    public Collection<Reader> getReaders() {
        return readersRepo;
    }

    public Reader getReader(int id) {
        return readersRepo.stream()
                .filter(r -> r.getId() == id)
                .findAny()
                .orElse(null);
    }

    public Reader addReader(String name) {
        Reader newReader = new Reader(++currentId, name);
        readersRepo.add(newReader);
        return newReader;
    }

    public Reader updateReader(int id, Reader reader) {
        for (Reader existing : readersRepo) {
            if (existing.getId() == id) {
                existing.setName(reader.getName());
                return existing;
            }
        }
        return null;
    }

    public boolean deleteReader(int id) {
        return readersRepo.removeIf(r -> r.getId() == id);
    }
}
