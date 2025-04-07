package com.example.kotki;

import java.util.Collection;

public interface IReadersService {

    public abstract Collection<Readers> getReaders();
    public Readers getReader(int id);
    public Readers addReader(String name);
    public Readers updateReader(int id, Readers readers);
    public boolean deleteReader(int id);
}
