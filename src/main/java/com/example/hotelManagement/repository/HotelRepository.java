package com.example.hotelManagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hotelManagement.model.Hotel;


@Repository
public interface HotelRepository extends JpaRepository<Hotel, Integer> {
	Optional<Hotel> findByNameAndLocation(String name, String location);

	List<Hotel> findByAmenitiesAmenityId(Integer amenityId); // For amenity-based search
}

