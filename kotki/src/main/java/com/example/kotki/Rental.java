package com.example.kotki;

import java.time.LocalDate;

public class Rental {
    private int id;
    private Reader reader;
    private Book book;
    private LocalDate rentDate;
    private LocalDate returnDate;

    public Rental(int id, Reader reader, Book book, LocalDate rentDate, LocalDate returnDate) {
        this.id = id;
        this.reader = reader;
        this.book = book;
        this.rentDate = rentDate;
        this.returnDate = returnDate;
    }

    public Rental() {}

    public int getId() { return id; }
    public Reader getReader() { return reader; }
    public void setReader(Reader reader) { this.reader = reader; }
    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }
    public LocalDate getRentDate() { return rentDate; }
    public void setRentDate(LocalDate rentDate) { this.rentDate = rentDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }
}
