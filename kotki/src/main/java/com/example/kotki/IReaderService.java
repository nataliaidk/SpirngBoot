package com.example.kotki;

import java.util.Collection;

public interface IReaderService {

    public abstract Collection<Reader> getReaders();
    public Reader getReader( int id);
    public Reader addReader(String name);
    public Reader updateReader(int id, Reader reader);
    public boolean deleteReader(int id);
}
