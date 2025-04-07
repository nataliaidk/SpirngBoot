package com.example.kotki;

import java.util.Collection;

public interface IRentalsService {
    Collection<Rentals> getAllRentals();

    Rentals getRental(int id);

    Rentals addRental(RentalsRequestDTO dto);

    Rentals updateRental(int id, RentalsRequestDTO rental);

    boolean deleteRental(int id);
}
