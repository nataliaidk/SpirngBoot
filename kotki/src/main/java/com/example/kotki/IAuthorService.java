package com.example.kotki;

import java.util.Collection;

public interface IAuthorService {
    public abstract Collection<Authors> getAuthors();

    public Authors getAuthor(int id);

    public Authors addAuthor(String name);

    public Authors updateAuthor(int id, Authors authors);

    public boolean deleteAuthor(int id);
}
