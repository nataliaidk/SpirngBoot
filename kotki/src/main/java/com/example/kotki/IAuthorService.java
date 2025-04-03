package com.example.kotki;

import java.util.Collection;

public interface IAuthorService {
    public abstract Collection<Author> getAuthors();

    public Author getAuthor(int id);

    public Author addAuthor(String name);

    public Author updateAuthor(int id, Author author);

    public boolean deleteAuthor(int id);
}
