package com.example.kotki;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class RentalService implements IRentalService {
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

    public Rental addRental(Rental rental) {
        rental = new Rental(++currentId, rental.getReader(), rental.getBook(), rental.getRentDate(), rental.getReturnDate());
        rentalsRepo.add(rental);
        return rental;
    }

    public Rental updateRental(int id, Rental rental) {
        for (Rental r : rentalsRepo) {
            if (r.getId() == id) {
                r.setReader(rental.getReader());
                r.setBook(rental.getBook());
                r.setRentDate(rental.getRentDate());
                r.setReturnDate(rental.getReturnDate());
                return r;
            }
        }
        return null;
    }

    public boolean deleteRental(int id) {
        return rentalsRepo.removeIf(r -> r.getId() == id);
    }
}
