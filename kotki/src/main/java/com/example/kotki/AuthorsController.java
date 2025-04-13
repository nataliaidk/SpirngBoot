package com.example.kotki;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
@RestController
@RequestMapping("/authors")
@Tag(name = "Authors Controller", description = "Operations for authors")
public class AuthorsController {

    @Autowired
    IAuthorsService authorService;

    @Operation(summary = "Get all authors")
    @GetMapping
    public ResponseEntity<Object> getAuthors(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "5") int size)
    {
        Page<Authors> authorsPage = authorService.getAuthors(page, size);
        return new ResponseEntity<>(authorsPage, HttpStatus.OK);
    }

    @Operation(summary = "Get an author by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getAuthor(@PathVariable int id) {
        Authors authors = authorService.getAuthor(id);
        return authors != null
                ? new ResponseEntity<>(authors, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Add a new author")
    @PostMapping
    public ResponseEntity<Object> addAuthor(@RequestBody Authors authors) {
        Authors savedAuthors = authorService.addAuthor(authors.getName());
        return new ResponseEntity<>(savedAuthors, HttpStatus.CREATED);
    }

    @Operation(summary = "Update an author")
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateAuthor(@PathVariable int id, @RequestBody Authors authors) {
        Authors updatedAuthors = authorService.updateAuthor(id, authors);
        return updatedAuthors != null
                ? ResponseEntity.ok(updatedAuthors)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @Operation(summary = "Delete an author")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable int id) {
        boolean deleted = authorService.deleteAuthor(id);
        return deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}