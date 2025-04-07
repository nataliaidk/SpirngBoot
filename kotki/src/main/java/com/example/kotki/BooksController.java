package com.example.kotki;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/books")
@Tag(name = "Books Controller", description = "Operations for books")
public class BooksController {
    @Autowired
    IBooksService bookService;

    @Operation(summary = "Add a new book (with existing author)")
    @PostMapping
    public ResponseEntity<Object> addBook(@RequestBody BooksRequestDTO dto) {
        Books books = bookService.addBook(dto);
        return books != null
                ? new ResponseEntity<>(books, HttpStatus.CREATED)
                : new ResponseEntity<>("Author not found", HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "Update a book")
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateBook(@PathVariable int id, @RequestBody BooksRequestDTO dto) {
        Books updated = bookService.updateBook(id, dto);
        return updated != null
                ? ResponseEntity.ok(updated)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Author not found");
    }

    @GetMapping
    public ResponseEntity<Object> getAllBooks() {
        return new ResponseEntity<>(bookService.getBooks(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getBook(@PathVariable int id) {
        Books books = bookService.getBook(id);
        return books != null
                ? new ResponseEntity<>(books, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable int id) {
        boolean deleted = bookService.deleteBook(id);
        return deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}