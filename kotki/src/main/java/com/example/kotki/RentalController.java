package com.example.kotki;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/rentals")
@Tag(name = "Rentals Controller", description = "Manage book rentals")
public class RentalController {

    @Autowired
    IRentalService rentalService;

    @Operation(summary = "Get all rentals")
    @GetMapping
    public ResponseEntity<Object> getAllRentals() {
        return new ResponseEntity<>(rentalService.getAllRentals(), HttpStatus.OK);
    }

    @Operation(summary = "Get a rental by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getRental(@PathVariable int id) {
        Rental rental = rentalService.getRental(id);
        return rental != null
                ? new ResponseEntity<>(rental, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



    @Operation(summary = "Add a new rental (with existing reader and book)")
    @PostMapping
    public ResponseEntity<Object> addRental(@RequestBody RentalRequestDTO dto) {
        Rental saved = rentalService.addRental(dto);
        return saved != null
                ? new ResponseEntity<>(saved, HttpStatus.CREATED)
                : new ResponseEntity<>("Reader or Book not found", HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "Update an existing rental")
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateRental(@PathVariable int id, @RequestBody RentalRequestDTO dto) {
        Rental updated = rentalService.updateRental(id, dto);
        return updated != null
                ? ResponseEntity.ok(updated)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Reader or Book not found");
    }

    @Operation(summary = "Delete a rental")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRental(@PathVariable int id) {
        boolean deleted = rentalService.deleteRental(id);
        return deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
