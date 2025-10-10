package com.example.hotelManagement.controller;

import com.example.hotelManagement.model.ApiResponse;
import com.example.hotelManagement.model.RoomType;
import com.example.hotelManagement.service.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for RoomType endpoints.
 * Base path: /api/RoomType
 *
 * Endpoints:
 * 1) POST   /api/RoomType/post
 * 2) PUT    /api/RoomType/update/{RoomType_id}
 * 3) GET    /api/RoomType/{RoomType_id}
 */
@RestController
@RequestMapping("/api/RoomType")
public class RoomTypeController {

    @Autowired
    private RoomTypeService roomTypeService;

    /**
     * POST /api/RoomType/post
     * Create a new RoomType.
     * Success: {"code":"POSTSUCCESS","message":"roomType added successfully"}
     * Failure: {"code":"ADDFAILS","message":"RoomType already exist"}
     */
    @PostMapping("/post")
    public ResponseEntity<ApiResponse> createRoomType(@RequestBody RoomType roomType) {
        // Assume service returns true if created, false if it already exists.
    	System.out.println(roomType);
        boolean created = roomTypeService.addRoomType(roomType);
        if (created) {
            return ResponseEntity.ok(new ApiResponse("POSTSUCCESS", "RoomType added successfully"));
        } else {
            return ResponseEntity.ok(new ApiResponse("ADDFAILS", "RoomType already exist"));
        }
    }

    /**
     * PUT /api/RoomType/update/{RoomType_id}
     * Update an existing RoomType.
     * Success: {"code":"UPDATESUCCESS","message":"RoomType updated successfully"}
     * Failure: {"code":"UPDTFAILS","message":"RoomType doesn't exist"}
     */
    @PutMapping("/update/{RoomType_id}")
    public ResponseEntity<ApiResponse> updateRoomType(
            @PathVariable("RoomType_id") Integer roomTypeId,
            @RequestBody RoomType updatedRoomType) {

        // Assume service returns true if updated, false if the id doesn't exist.
        boolean updated = roomTypeService.updateRoomType(roomTypeId, updatedRoomType);
        if (updated) {
            return ResponseEntity.ok(new ApiResponse("UPDATESUCCESS", "RoomType updated successfully"));
        } else {
            return ResponseEntity.ok(new ApiResponse("UPDTFAILS", "RoomType doesn't exist"));
        }
    }

    /**
     * GET /api/RoomType/{RoomType_id}
     * Get a RoomType by id.
     * Success: returns the RoomType object
     * Failure: {"code":"GETFAILS","message":"RoomType doesn't exist"}
     */
    @GetMapping("/{RoomType_id}")
    public ResponseEntity<?> getRoomTypeById(@PathVariable("RoomType_id") Integer roomTypeId) {
        RoomType roomType = roomTypeService.getRoomTypeById(roomTypeId);
        if (roomType == null) {
            return ResponseEntity.ok(new ApiResponse("GETFAILS", "RoomType doesn't exist"));
        }
        return ResponseEntity.ok(roomType);
    }
}