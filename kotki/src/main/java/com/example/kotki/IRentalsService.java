package com.example.kotki;

import org.springframework.data.domain.Page;

public interface IRentalsService {
    public abstract Page<Rentals> getRentals(int page, int size);
    public abstract Rentals getRental(int id);
    public abstract Rentals addRental(RentalsRequestDTO dto);
    public abstract Rentals updateRental(int id, RentalsRequestDTO rental);
    public abstract boolean deleteRental(int id);
}
