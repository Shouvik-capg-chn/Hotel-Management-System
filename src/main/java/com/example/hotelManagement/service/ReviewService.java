package com.example.hotelManagement.service;

import com.example.hotelManagement.model.Review;
import java.util.List;

public interface ReviewService
{
    Review createReview(Review review);
    Review getReviewById(Integer id);
    List<Review> getAllReviews();
    Review updateReview(Integer id, Review review);
    void deleteReview(Integer id);
}
