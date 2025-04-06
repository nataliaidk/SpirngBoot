package com.example.kotki;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/books")
@Tag(name = "Books Controller", description = "Operations pertaining to books")
public class BooksController {
    @Autowired
    IBooksService booksService;

    @Operation(summary = "Get all books")
    @GetMapping
    public ResponseEntity<Object> getBooks(){
        return new ResponseEntity<>(booksService.getBooks(), HttpStatus.OK);
    }

    @Operation(summary = "Get a book by ID")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getBook(@PathVariable("id") int id){
        return new ResponseEntity<>(booksService.getBook(id), HttpStatus.OK);
    }

    @Operation(summary = "Add a new book")
    @PostMapping
    public ResponseEntity<Object> addBook(@RequestBody Book book) {
        Book savedBook = booksService.addBook(book.getTitle(), book.getAuthor(), book.getPages());
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    @Operation(summary = "Update a book")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateBook(@PathVariable int id, @RequestBody Book book) {
        Book updatedBook = booksService.updateBook(id, book);
        return updatedBook != null ? ResponseEntity.ok(updatedBook) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @Operation(summary = "Delete a book")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable int id) {
        boolean deletedBook = booksService.deleteBook(id);
        return deletedBook ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}