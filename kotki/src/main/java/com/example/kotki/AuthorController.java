package com.example.kotki;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/authors")
@Tag(name = "Authors Controller", description = "Operations pertaining to authors")
public class AuthorController {

    @Autowired
    IAuthorService authorService;

    @Operation(summary = "Get all authors")
    @GetMapping
    public ResponseEntity<Object> getAuthors() {
        return new ResponseEntity<>(authorService.getAuthors(), HttpStatus.OK);
    }

    @Operation(summary = "Get an author by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getAuthor(@PathVariable int id) {
        Author author = authorService.getAuthor(id);
        return author != null
                ? new ResponseEntity<>(author, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Add a new author")
    @PostMapping
    public ResponseEntity<Object> addAuthor(@RequestBody Author author) {
        Author savedAuthor = authorService.addAuthor(author.getName());
        return new ResponseEntity<>(savedAuthor, HttpStatus.CREATED);
    }

    @Operation(summary = "Update an author")
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateAuthor(@PathVariable int id, @RequestBody Author author) {
        Author updatedAuthor = authorService.updateAuthor(id, author);
        return updatedAuthor != null
                ? ResponseEntity.ok(updatedAuthor)
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