package com.example.hotelManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.hotelManagement.model.ApiResponse;
import com.example.hotelManagement.model.Hotel;
import com.example.hotelManagement.service.HotelService;

// Swagger/OpenAPI annotations
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

@Tag(name = "Hotel Controller", description = "Hotel management operations")
@RestController
@RequestMapping("/api/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @Operation(summary = "Create a new hotel")
    @PostMapping("/post")
    public ResponseEntity<ApiResponse> createHotel(@RequestBody Hotel hotel) {
        return ResponseEntity.ok(hotelService.addHotel(hotel));
    }

    @Operation(summary = "Get all hotels")
    @GetMapping("/all")
    public ResponseEntity<?> getAllHotels() {
        List<Hotel> hotels = hotelService.getAllHotels();
        if (hotels.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse("GETFAILS", "Hotel list is empty"));
        }
        return ResponseEntity.ok(hotels);
    }

    @Operation(summary = "Get hotel by ID")
    @GetMapping("/{hotel_id}")
    public ResponseEntity<?> getHotelById(@PathVariable("hotel_id") Integer hotelId) {
        Hotel hotel = hotelService.getHotelById(hotelId);
        if (hotel == null) {
            return ResponseEntity.ok(new ApiResponse("GETFAILS", "Hotel doesn't exist"));
        }
        return ResponseEntity.ok(hotel);
    }

    @Operation(summary = "Get hotels by amenity ID")
    @GetMapping("/amenity/{amenity_id}")
    public ResponseEntity<?> getHotelsByAmenity(@PathVariable("amenity_id") Integer amenityId) {
        List<Hotel> hotels = hotelService.getHotelsByAmenityId(amenityId);
        if (hotels.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse("GETFAILS", "No hotel is found with the specific amenity"));
        }
        return ResponseEntity.ok(hotels);
    }

    @Operation(summary = "Update hotel details")
    @PutMapping("/update/{hotel_id}")
    public ResponseEntity<ApiResponse> updateHotel(@PathVariable("hotel_id") Integer hotelId,
                                                   @RequestBody Hotel updatedHotel) {
        return ResponseEntity.ok(hotelService.updateHotel(hotelId, updatedHotel));
    }
    
    @Operation(summary = "Delete a hotel by ID")
    @DeleteMapping("/delete/{hotelId}")
    public ResponseEntity<ApiResponse> deleteHotel(@PathVariable("hotelId") Integer hotelId) {
        return ResponseEntity.ok(hotelService.deleteHotel(hotelId));
    }
}
