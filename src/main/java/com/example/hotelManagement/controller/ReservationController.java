package com.example.hotelManagement.controller;

import com.example.hotelManagement.model.Reservation;
import com.example.hotelManagement.service.ReservationService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@JsonIgnoreProperties
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    /**
     * POST /api/reservation/
     * Success:
     *   { "code": "POSTSUCCESS", "message": "Reservation added successfully" }
     * Failure (overlap):
     *   { "code": "ADDFAILS", "message": "reservation already exist" }
     * Other validation errors:
     *   { "code": "ADDFAILS", "message": "<error>" }
     */
    @PostMapping("/reservation/post")
    public ResponseEntity<?> createReservation(@RequestBody Reservation reservation) {
    	
        try {
            reservationService.createReservation(reservation);
            return ResponseEntity.ok(Map.of(
                    "code", "POSTSUCCESS",
                    "message", "Reservation added successfully"
            ));
        } catch (IllegalStateException overlap) { // from ensureNoOverlap(...)
            return ResponseEntity.badRequest().body(Map.of(
                    "code", "ADDFAILS",
                    "message", "reservation already exist"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "code", "ADDFAILS",
                    "message", e.getMessage()
            ));
        }
        
    }

    /**
     * GET /api/reservation/all
     * Success: list of reservations
     * If empty:
     *   { "code": "GETALLFAILS", "message": "reservation list is empty" }
     */
    @GetMapping("/reservation/all")
    public ResponseEntity<?> getAllReservations() {
        List<Reservation> list = reservationService.getAllReservations();
        if (list == null || list.isEmpty()) {
            return ResponseEntity.ok(Map.of(
                    "code", "GETALLFAILS",
                    "message", "reservation list is empty"
            ));
        }
        return ResponseEntity.ok(list);
    }

    /**
     * GET /api/reservation/{reservation_id}
     * Success: reservation object
     * Not found:
     *   { "code": "GETFAILS", "message": "Reservation doesn't exist" }
     */
    @GetMapping("/reservation/{reservation_id}")
    public ResponseEntity<?> getReservationById(@PathVariable("reservation_id") Integer reservationId) {
        return reservationService.getReservationById(reservationId)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                        "code", "GETFAILS",
                        "message", "Reservation doesn't exist"
                )));
    }

    /**
     * GET /api/reservations/date-range?startDate=YYYY-MM-DD&endDate=YYYY-MM-DD
     * Success: list of reservations intersecting the date range (inclusive)
     * (No explicit failure response specified; returns empty list if none.)
     */
    @GetMapping("/reservations/date-range")
    public ResponseEntity<?> getReservationsByDateRange(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        try {
            List<Reservation> list = reservationService.getReservationsWithinDateRange(startDate, endDate);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            // If you want to standardize error output even here, uncomment below and choose an error schema.
            // return ResponseEntity.badRequest().body(Map.of("code", "RANGEFAIL", "message", e.getMessage()));
            return ResponseEntity.badRequest().body(Map.of(
                    "error", e.getMessage()
            ));
        }
    }

    /**
     * PUT /api/reservation/update/{reservation_id}
     * Success:
     *   { "code": "UPDATESUCCESS", "message": "Reservation updated successfully" }
     * Failure (not found/overlap/validation):
     *   { "code": "UPDTFAILS", "message": "Reservation doesn't updated successfully" }
     */
    @PutMapping("/reservation/update/{reservation_id}")
    public ResponseEntity<?> updateReservation(
            @PathVariable("reservation_id") Integer reservationId,
            @RequestBody Reservation reservationPatch) {
        try {
            return reservationService.updateReservation(reservationId, reservationPatch)
                    .<ResponseEntity<?>>map(r -> ResponseEntity.ok(Map.of(
                            "code", "UPDATESUCCESS",
                            "message", "Reservation updated successfully"
                    )))
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                            "code", "UPDTFAILS",
                            "message", "Reservation doesn't updated successfully"
                    )));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "code", "UPDTFAILS",
                    "message", "Reservation doesn't updated successfully"
            ));
        }
    }

    /**
     * DELETE /api/reservation/{reservation_id}
     * Success:
     *   { "code": "DELETESUCCESS", "message": "reservation deleted successfully" }
     * Failure (not found):
     *   { "code": "DLTFAILS", "message": "Reservation doesn't exist" }
     */
    @DeleteMapping("/reservation/{reservation_id}")
    public ResponseEntity<?> deleteReservation(@PathVariable("reservation_id") Integer reservationId) {
        boolean deleted = reservationService.deleteReservation(reservationId);
        if (deleted) {
            return ResponseEntity.ok(Map.of(
                    "code", "DELETESUCCESS",
                    "message", "reservation deleted successfully"
            ));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "code", "DLTFAILS",
                    "message", "Reservation doesn't exist"
            ));
        }
    }
}
