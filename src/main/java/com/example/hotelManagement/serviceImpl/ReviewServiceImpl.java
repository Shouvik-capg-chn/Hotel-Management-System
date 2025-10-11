package com.example.hotelManagement.serviceImpl;

import com.example.hotelManagement.model.Reservation;
import com.example.hotelManagement.model.Review;
import com.example.hotelManagement.repository.ReservationRepository;
import com.example.hotelManagement.repository.ReviewRepository;
import com.example.hotelManagement.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;
    
    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public Review createReview(Review review) {
        if (review.getReservation() != null && review.getReservation().getReservationId() != null) {
            Reservation reservation = reservationRepository.findById(review.getReservation().getReservationId())
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
            review.setReservation(reservation);
        } else {
            throw new RuntimeException("Reservation ID is required");
        }

        return reviewRepository.save(review);
    }

    @Override
    public Review getReviewById(Integer id) {
        return reviewRepository.findById(id).orElse(null);
    }

    @Override
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    @Override
    public Review updateReview(Integer id, Review updatedReview) {
        Optional<Review> existingReviewOpt = reviewRepository.findById(id);
        if (existingReviewOpt.isPresent()) {
            Review existingReview = existingReviewOpt.get();
            existingReview.setRating(updatedReview.getRating());
            existingReview.setComment(updatedReview.getComment());
            existingReview.setReviewDate(updatedReview.getReviewDate());
            existingReview.setReservation(updatedReview.getReservation());
            return reviewRepository.save(existingReview);
        }
        return null;
    }

    @Override
    public void deleteReview(Integer id) {
        reviewRepository.deleteById(id);
    }
}

