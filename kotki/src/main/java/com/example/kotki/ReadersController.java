package com.example.kotki;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/readers")
@Tag(name = "Readers Controller", description = "Operations for readers")
public class ReadersController {

    @Autowired
    ReadersService readerService;

     @Operation(summary = "Get all readers with pagination")
    @GetMapping
    public ResponseEntity<Object> getReaders(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "5") int size) {
        Page<Readers> readersPage = readerService.getReaders(page, size);
        return new ResponseEntity<>(readersPage, HttpStatus.OK);
    }

    @Operation(summary = "Get a reader by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getReader(@PathVariable int id) {
        Readers readers = readerService.getReader(id);
        return readers != null
                ? new ResponseEntity<>(readers, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Add a new reader")
    @PostMapping
    public ResponseEntity<Object> addReader(@RequestBody Readers readers) {
        Readers savedReaders = readerService.addReader(readers.getName());
        return new ResponseEntity<>(savedReaders, HttpStatus.CREATED);
    }

    @Operation(summary = "Update a reader")
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateReader(@PathVariable int id, @RequestBody Readers readers) {
        Readers updated = readerService.updateReader(id, readers);
        return updated != null
                ? ResponseEntity.ok(updated)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @Operation(summary = "Delete a reader")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReader(@PathVariable int id) {
        boolean deleted = readerService.deleteReader(id);
        return deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
