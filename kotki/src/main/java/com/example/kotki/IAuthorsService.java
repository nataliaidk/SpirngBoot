package com.example.kotki;

import org.springframework.data.domain.Page;

import java.util.Collection;

public interface IAuthorsService {
    public abstract Page<Authors> getAuthors(int page, int size);
    public abstract Authors getAuthor(int id);
    public abstract Authors addAuthor(String name);
    public abstract Authors updateAuthor(int id, Authors authors);
    public abstract boolean deleteAuthor(int id);

}
