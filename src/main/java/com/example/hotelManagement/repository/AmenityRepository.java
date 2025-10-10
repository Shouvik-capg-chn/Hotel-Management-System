package com.example.hotelManagement.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.example.hotelManagement.model.Amenity;

import java.util.Optional;

public interface AmenityRepository extends JpaRepository<Amenity, Integer> {
    Optional<Amenity> findByName(String name);
}
