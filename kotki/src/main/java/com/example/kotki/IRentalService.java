package com.example.kotki;

import java.util.Collection;

public interface IRentalService {
    Collection<Rentals> getAllRentals();

    Rentals getRental(int id);

    Rentals addRental(RentalRequestDTO dto);

    Rentals updateRental(int id, RentalRequestDTO rental);

    boolean deleteRental(int id);
}
