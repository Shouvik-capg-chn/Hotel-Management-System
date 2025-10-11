package com.example.hotelManagement.controller;

import com.example.hotelManagement.model.Reservation;
import com.example.hotelManagement.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reservation")
@Tag(name = "Reservation Controller", description = "Operations related to hotel reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Operation(summary = "Create a new reservation")
    @PostMapping("/post")
    public ResponseEntity<?> createReservation(@RequestBody Reservation reservation) {
        Reservation saved = reservationService.createReservation(reservation);
        return ResponseEntity.ok().body(saved);
    }

    @Operation(summary = "Get all reservations")
    @GetMapping("/all")
    public ResponseEntity<?> getAllReservations() {
        List<Reservation> reservations = reservationService.getAllReservations();
        return ResponseEntity.ok().body(reservations);
    }
    

    @Operation(summary = "Get reservation by ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> getReservationById(@PathVariable Integer id) {
        Reservation reservation = reservationService.getReservationById(id);
        if (reservation != null) {
            return ResponseEntity.ok().body(reservation);
        } else {
            return ResponseEntity.status(404).body("Reservation doesn't exist");
        }
    }

    @Operation(summary = "Get reservations by date range")
    @GetMapping("/date-range")
    public ResponseEntity<?> getReservationsByDateRange(@RequestParam LocalDate startDate,
                                                        @RequestParam LocalDate endDate) {
        List<Reservation> reservations = reservationService.getReservationsByDateRange(startDate, endDate);
        return ResponseEntity.ok().body(reservations);
    }

    @Operation(summary = "Update a reservation by ID")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateReservation(@PathVariable Integer id,
                                               @RequestBody Reservation reservation) {
        Reservation updated = reservationService.updateReservation(id, reservation);
        if (updated != null) {
            return ResponseEntity.ok().body(updated);
        } else {
            return ResponseEntity.status(404).body("Reservation doesn't exist");
        }
    }

    @Operation(summary = "Delete a reservation by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReservation(@PathVariable Integer id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.ok().body("Reservation deleted successfully");
    }
}