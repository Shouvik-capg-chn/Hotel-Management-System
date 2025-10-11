package com.example.hotelManagement.controller;

import com.example.hotelManagement.model.ApiResponse;
import com.example.hotelManagement.model.RoomType;
import com.example.hotelManagement.service.RoomTypeService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for RoomType endpoints.
 * Base path: /api/RoomType
 */
@RestController
@RequestMapping("/api/RoomType")
public class RoomTypeController {

    @Autowired
    private RoomTypeService roomTypeService;

    @Operation(summary = "Create a new RoomType")
    @PostMapping("/post")
    public ResponseEntity<ApiResponse> createRoomType(@RequestBody RoomType roomType) {
        System.out.println(roomType);
        boolean created = roomTypeService.addRoomType(roomType);
        if (created) {
            return ResponseEntity.ok(new ApiResponse("POSTSUCCESS", "RoomType added successfully"));
        } else {
            return ResponseEntity.ok(new ApiResponse("ADDFAILS", "RoomType already exist"));
        }
    }

    @Operation(summary = "Update an existing RoomType by ID")
    @PutMapping("/update/{RoomType_id}")
    public ResponseEntity<ApiResponse> updateRoomType(
            @PathVariable("RoomType_id") Integer roomTypeId,
            @RequestBody RoomType updatedRoomType) {

        boolean updated = roomTypeService.updateRoomType(roomTypeId, updatedRoomType);
        if (updated) {
            return ResponseEntity.ok(new ApiResponse("UPDATESUCCESS", "RoomType updated successfully"));
        } else {
            return ResponseEntity.ok(new ApiResponse("UPDTFAILS", "RoomType doesn't exist"));
        }
    }

    @Operation(summary = "Get a RoomType by ID")
    @GetMapping("/{RoomType_id}")
    public ResponseEntity<?> getRoomTypeById(@PathVariable("RoomType_id") Integer roomTypeId) {
        RoomType roomType = roomTypeService.getRoomTypeById(roomTypeId);
        if (roomType == null) {
            return ResponseEntity.ok(new ApiResponse("GETFAILS", "RoomType doesn't exist"));
        }
        return ResponseEntity.ok(roomType);
    }
    
    @Operation(summary = "Delete a RoomType by ID")
    @DeleteMapping("/delete/{RoomTypeId}")
    public ResponseEntity<ApiResponse> deleteRoomType(@PathVariable("RoomTypeId") Integer roomTypeId) {
        boolean deleted = roomTypeService.deleteRoomType(roomTypeId);
        if (deleted) {
            return ResponseEntity.ok(new ApiResponse("DELETESUCCESS", "RoomType deleted successfully"));
        } else {
            return ResponseEntity.ok(new ApiResponse("DELETEFAILS", "RoomType doesn't exist"));
        }
    }
}