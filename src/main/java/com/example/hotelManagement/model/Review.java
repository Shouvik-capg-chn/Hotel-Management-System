package com.example.hotelManagement.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Review")
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "review_id")
	private Integer reviewId;

	 // One-to-One with Reservation
    @OneToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

	@Column(name = "rating", nullable = false)
	private Integer rating;

	@Column(name = "comment", columnDefinition = "TEXT")
	private String comment;

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
