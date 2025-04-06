package com.example.kotki;

import java.util.Collection;

public interface IRentalService {
    Collection<Rental> getAllRentals();

    Rental getRental(int id);

    Rental addRental(Rental rental);

    Rental updateRental(int id, Rental rental);

    boolean deleteRental(int id);
}
