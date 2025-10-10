package com.example.hotelManagement.controller;

import com.example.hotelManagement.model.Amenity;
import com.example.hotelManagement.service.AmenityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/amenity")
public class AmenityController {

    @Autowired
    private AmenityService amenityService;

    @PostMapping("/post")
    public ResponseEntity<?> createAmenity(@RequestBody Amenity amenity) {
        try {
            Amenity saved = amenityService.addAmenity(amenity);
            return ResponseEntity.ok().body(new ApiResponse("POSTSUCCESS", "Amenity added successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse("ADDFAILS", e.getMessage()));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllAmenities() {
        try {
            List<Amenity> amenities = amenityService.getAllAmenities();
            return ResponseEntity.ok(amenities);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse("GETALLFAILS", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAmenityById(@PathVariable Integer id) {
        try {
            Amenity amenity = amenityService.getAmenityById(id);
            return ResponseEntity.ok(amenity);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse("GETFAILS", e.getMessage()));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateAmenity(@PathVariable Integer id, @RequestBody Amenity amenity) {
        try {
            Amenity updated = amenityService.updateAmenity(id, amenity);
            return ResponseEntity.ok().body(new ApiResponse("UPDATESUCCESS", "Amenity updated successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse("UPDTFAILS", e.getMessage()));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAmenity(@PathVariable Integer id) {
        try {
            amenityService.deleteAmenity(id);
            return ResponseEntity.ok().body(new ApiResponse("DELETESUCCESS", "Amenity deleted successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse("DLTFAILS", e.getMessage()));
        }
    }

    // Helper class for API responses
    static class ApiResponse {
        private String code;
        private String message;

        public ApiResponse(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }
}
