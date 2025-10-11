package com.example.hotelManagement.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "Review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Integer reviewId;

    @OneToOne
    @JoinColumn(name = "reservation_id")
    @JsonBackReference
    private Reservation reservation;

    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must not exceed 5")
    @Column(name = "rating", nullable = false)
    private Integer rating;

    @Size(max = 10000, message = "Comment is too long")
    @Column(name = "comment", columnDefinition = "TEXT")
    private String comment;

    @NotNull(message = "Review date is required")
    @PastOrPresent(message = "Review date cannot be in the future")
    @Column(name = "review_date", nullable = false)
    private LocalDate reviewDate;

    // Constructors
    public Review() {
    }

    public Review(Reservation reservation, Integer rating, String comment, LocalDate reviewDate) {
        this.reservation = reservation;
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = reviewDate;
    }

    // Getters and Setters
    public Integer getReviewId() {
        return reviewId;
    }

    public void setReviewId(Integer reviewId) {
        this.reviewId = reviewId;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }

    @Override
    public String toString() {
        return "Review [reviewId=" + reviewId + ", reservation=" + reservation + ", rating=" + rating + ", comment="
                + comment + ", reviewDate=" + reviewDate + "]";
    }
}