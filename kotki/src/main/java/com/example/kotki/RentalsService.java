package com.example.kotki;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class RentalsService implements IRentalsService {

    @Autowired
    IReadersService readerService;

    @Autowired
    IBooksService bookService;

    private static List<Rentals> rentalsRepo = new ArrayList<>();
    private static int currentId = 0;

    public Collection<Rentals> getAllRentals() {
        return rentalsRepo;
    }

    public Rentals getRental(int id) {
        return rentalsRepo.stream()
                .filter(r -> r.getId() == id)
                .findAny()
                .orElse(null);
    }

    public Rentals addRental(RentalsRequestDTO dto) {
        Readers readers = readerService.getReader(dto.getReaderId());
        Books books = bookService.getBook(dto.getBookId());

        if (readers == null || books == null) {
            return null; // można wyjątek rzucić
        }

        Rentals rentals = new Rentals(++currentId, readers, books, dto.getRentDate(), dto.getReturnDate());
        rentalsRepo.add(rentals);
        return rentals;
    }

    public Rentals updateRental(int id, RentalsRequestDTO dto) {
        Readers readers = readerService.getReader(dto.getReaderId());
        Books books = bookService.getBook(dto.getBookId());

        if (readers == null || books == null) {
            return null;
        }

        for (Rentals r : rentalsRepo) {
            if (r.getId() == id) {
                r.setReader(readers);
                r.setBook(books);
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
