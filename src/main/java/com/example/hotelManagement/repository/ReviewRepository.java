package com.example.hotelManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.hotelManagement.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer>
{
    // You can add custom query methods if needed
}
