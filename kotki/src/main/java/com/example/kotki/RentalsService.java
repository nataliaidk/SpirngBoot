package com.example.kotki;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;

@Service
public class RentalsService implements IRentalsService {

    @Autowired
    IReadersService readerService;

    @Autowired
    IBooksService bookService;

    private static List<Rentals> rentalsRepo = new ArrayList<>();
    private static int currentId = 0;

    public Page<Rentals> getRentals(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        int start = Math.min((int) pageable.getOffset(), rentalsRepo.size());
        int end = Math.min((start + pageable.getPageSize()), rentalsRepo.size());
        List<Rentals> paginatedList = rentalsRepo.subList(start, end);
        return new PageImpl<>(paginatedList, pageable, rentalsRepo.size());
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
