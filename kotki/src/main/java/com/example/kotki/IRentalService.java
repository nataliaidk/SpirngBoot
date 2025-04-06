package com.example.kotki;

import java.util.Collection;

public interface IRentalService {
    Collection<Rental> getAllRentals();

    Rental getRental(int id);

    Rental addRental(RentalRequestDTO dto);

    Rental updateRental(int id, RentalRequestDTO rental);

    boolean deleteRental(int id);
}
