package com.example.kotki;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//requestbody
//requestmapping replace
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BooksController {
    @Autowired
    IBooksService booksService; 

    //read
    //get
    @GetMapping
    public ResponseEntity<Object> getBooks(){
        return new ResponseEntity<>(booksService.getBooks(), HttpStatus.OK);
    }
    //read
    //get
    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getBook(@PathVariable("id") int id){
        return new ResponseEntity<>(booksService.getBook(id), HttpStatus.OK);
    }

    //create
    //post
    @PostMapping
    public ResponseEntity<Object> addBook(@RequestBody Book book) {
        Book savedBook = booksService.addBook(book.getTitle(), book.getAuthor(), book.getPages());
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    //update
    //put
    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateBook(@PathVariable int id, @RequestBody Book book) {
        Book updatedBook = booksService.updateBook(id, book);
        return updatedBook != null ? ResponseEntity.ok(updatedBook) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }


    //delete
    //delete
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable int id) {
        boolean deletedBook = booksService.deleteBook(id);
        return deletedBook ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}