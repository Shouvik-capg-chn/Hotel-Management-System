package com.example.hotelManagement.controller;

import com.example.hotelManagement.model.Review;
import com.example.hotelManagement.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    private Map<String, String> msg(String code, String message) {
        Map<String, String> m = new HashMap<>();
        m.put("code", code);
        m.put("message", message);
        return m;
    }

    @Operation(summary = "Create a new review")
    @PostMapping("/post")
    public ResponseEntity<?> createReview(@RequestBody Review review) {
        try {
            Review created = reviewService.createReview(review);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(msg("ADDFAILS", "Unable to add review"));
        }
    }

    @Operation(summary = "Get review by ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> getReviewById(@PathVariable Integer id) {
        try {
            Review review = reviewService.getReviewById(id);
            return ResponseEntity.ok(review);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(msg("GETFAILS", "Review not found"));
        }
    }

    @Operation(summary = "Get all reviews")
    @GetMapping("/all")
    public ResponseEntity<?> getAllReviews() {
        try {
            List<Review> reviews = reviewService.getAllReviews();
            if (reviews == null || reviews.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(msg("GETALLFAILS", "Review list is empty"));
            }
            return ResponseEntity.ok(reviews);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(msg("GETALLFAILS", "Unable to fetch reviews"));
        }
    }

    @Operation(summary = "Update review by ID")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateReview(@PathVariable Integer id, @RequestBody Review review) {
        try {
            Review updated = reviewService.updateReview(id, review);
            return ResponseEntity.ok(updated);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(msg("UPDTFAILS", "Unable to update review"));
        }
    }

    @Operation(summary = "Delete review by ID")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable Integer id) {
        try {
            reviewService.deleteReview(id);
            return ResponseEntity.ok(msg("DELETESUCCESS", "Review deleted successfully"));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(msg("DELETEFAILS", "Unable to delete review"));
        }
    }
}