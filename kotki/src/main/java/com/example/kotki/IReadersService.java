package com.example.kotki;

import org.springframework.data.domain.Page;

public interface IReadersService {

    public abstract Page<Readers> getReaders(int page, int size);
    public abstract Readers getReader(int id);
    public abstract Readers addReader(String name);
    public abstract Readers updateReader(int id, Readers readers);
    public abstract boolean deleteReader(int id);
}
