package com.example.kotki;

import java.time.LocalDate;

public class Rentals {
    private int id;
    private Readers readers;
    private Books books;
    private LocalDate rentDate;
    private LocalDate returnDate;

    public Rentals(int id, Readers readers, Books books, LocalDate rentDate, LocalDate returnDate) {
        this.id = id;
        this.readers = readers;
        this.books = books;
        this.rentDate = rentDate;
        this.returnDate = returnDate;
    }

    public Rentals() {}

    public int getId() { return id; }
    public Readers getReader() { return readers; }
    public void setReader(Readers readers) { this.readers = readers; }
    public Books getBook() { return books; }
    public void setBook(Books books) { this.books = books; }
    public LocalDate getRentDate() { return rentDate; }
    public void setRentDate(LocalDate rentDate) { this.rentDate = rentDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }
}
