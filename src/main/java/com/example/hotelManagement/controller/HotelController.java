package com.example.hotelManagement.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hotelManagement.model.ApiResponse;
import com.example.hotelManagement.model.Hotel;
import com.example.hotelManagement.service.HotelService;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {

	@Autowired
	private HotelService hotelService;

	@PostMapping("/post")
	public ResponseEntity<ApiResponse> createHotel(@RequestBody Hotel hotel) {
		return ResponseEntity.ok(hotelService.addHotel(hotel));
	}

	@GetMapping("/all")
	public ResponseEntity<?> getAllHotels() {
		List<Hotel> hotels = hotelService.getAllHotels();
		if (hotels.isEmpty()) {
			return ResponseEntity.ok(new ApiResponse("GETFAILS", "Hotel list is empty"));
		}
		return ResponseEntity.ok(hotels);
	}

	@GetMapping("/{hotel_id}")
	public ResponseEntity<?> getHotelById(@PathVariable("hotel_id") Integer hotelId) {
		Hotel hotel = hotelService.getHotelById(hotelId);
		if (hotel == null) {
			return ResponseEntity.ok(new ApiResponse("GETFAILS", "Hotel doesn't exist"));
		}
		return ResponseEntity.ok(hotel);
	}

	@GetMapping("/amenity/{amenity_id}")
	public ResponseEntity<?> getHotelsByAmenity(@PathVariable("amenity_id") Integer amenityId) {
		List<Hotel> hotels = hotelService.getHotelsByAmenityId(amenityId);
		if (hotels.isEmpty()) {
			return ResponseEntity.ok(new ApiResponse("GETFAILS", "No hotel is found with the specific amenity"));
		}
		return ResponseEntity.ok(hotels);
	}

	@PutMapping("/update/{hotel_id}")
	public ResponseEntity<ApiResponse> updateHotel(@PathVariable("hotel_id") Integer hotelId,
			@RequestBody Hotel updatedHotel) {
		return ResponseEntity.ok(hotelService.updateHotel(hotelId, updatedHotel));
	}
}
