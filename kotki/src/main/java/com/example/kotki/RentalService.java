package com.example.kotki;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class RentalService implements IRentalService {

    @Autowired
    IReaderService readerService;

    @Autowired
    IBooksService bookService;

    private static List<Rental> rentalsRepo = new ArrayList<>();
    private static int currentId = 0;

    public Collection<Rental> getAllRentals() {
        return rentalsRepo;
    }

    public Rental getRental(int id) {
        return rentalsRepo.stream()
                .filter(r -> r.getId() == id)
                .findAny()
                .orElse(null);
    }

    public Rental addRental(RentalRequestDTO dto) {
        Reader reader = readerService.getReader(dto.getReaderId());
        Book book = bookService.getBook(dto.getBookId());

        if (reader == null || book == null) {
            return null; // można też rzucić wyjątek
        }

        Rental rental = new Rental(++currentId, reader, book, dto.getRentDate(), dto.getReturnDate());
        rentalsRepo.add(rental);
        return rental;
    }

    public Rental updateRental(int id, RentalRequestDTO dto) {
        Reader reader = readerService.getReader(dto.getReaderId());
        Book book = bookService.getBook(dto.getBookId());

        if (reader == null || book == null) {
            return null;
        }

        for (Rental r : rentalsRepo) {
            if (r.getId() == id) {
                r.setReader(reader);
                r.setBook(book);
                r.setRentDate(dto.getRentDate());
                r.setReturnDate(dto.getReturnDate());
                return r;
            }
        }
        return null;
    }

    public boolean deleteRental(int id) {
        return rentalsRepo.removeIf(r -> r.getId() == id);
    }
}
