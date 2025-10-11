package com.example.hotelManagement;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.hotelManagement.model.Reservation;
import com.example.hotelManagement.model.Review;
import com.example.hotelManagement.repository.ReservationRepository;
import com.example.hotelManagement.repository.ReviewRepository;
import com.example.hotelManagement.serviceImpl.ReviewServiceImpl;

class ReviewServiceImplTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateReview_Success() {
        Reservation reservation = new Reservation();
        reservation.setReservationId(1);

        Review review = new Review();
        review.setReservation(reservation);

        when(reservationRepository.findById(1)).thenReturn(Optional.of(reservation));
        when(reviewRepository.save(review)).thenReturn(review);

        Review result = reviewService.createReview(review);

        assertNotNull(result);
        verify(reviewRepository).save(review);
    }

    @Test
    void testCreateReview_ReservationNotFound() {
        Reservation reservation = new Reservation();
        reservation.setReservationId(1);

        Review review = new Review();
        review.setReservation(reservation);

        when(reservationRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reviewService.createReview(review);
        });

        assertEquals("Reservation not found", exception.getMessage());
    }

    @Test
    void testCreateReview_ReservationIdMissing() {
        Review review = new Review();
        review.setReservation(new Reservation()); // no ID

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reviewService.createReview(review);
        });

        assertEquals("Reservation ID is required", exception.getMessage());
    }

    @Test
    void testGetReviewById_Found() {
        Review review = new Review();
        review.setComment("Great stay");

        when(reviewRepository.findById(1)).thenReturn(Optional.of(review));

        Review result = reviewService.getReviewById(1);

        assertNotNull(result);
        assertEquals("Great stay", result.getComment());
    }

    @Test
    void testGetReviewById_NotFound() {
        when(reviewRepository.findById(1)).thenReturn(Optional.empty());

        Review result = reviewService.getReviewById(1);

        assertNull(result);
    }

    @Test
    void testGetAllReviews() {
        Review r1 = new Review(); r1.setComment("Nice");
        Review r2 = new Review(); r2.setComment("Excellent");

        when(reviewRepository.findAll()).thenReturn(Arrays.asList(r1, r2));

        List<Review> result = reviewService.getAllReviews();

        assertEquals(2, result.size());
    }

    @Test
    void testUpdateReview_Success() {
        Review existing = new Review();
        existing.setComment("Old comment");

        Review updated = new Review();
        updated.setComment("New comment");
        updated.setRating(4);
        updated.setReviewDate(LocalDate.now());
        updated.setReservation(new Reservation());

        when(reviewRepository.findById(1)).thenReturn(Optional.of(existing));
        when(reviewRepository.save(any())).thenReturn(existing);

        Review result = reviewService.updateReview(1, updated);

        assertNotNull(result);
        assertEquals("New comment", result.getComment());
    }

    @Test
    void testUpdateReview_NotFound() {
        Review updated = new Review();
        updated.setComment("New comment");

        when(reviewRepository.findById(1)).thenReturn(Optional.empty());

        Review result = reviewService.updateReview(1, updated);

        assertNull(result);
    }

    @Test
    void testDeleteReview() {
        reviewService.deleteReview(1);
        verify(reviewRepository).deleteById(1);
    }
}
