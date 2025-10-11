package com.example.hotelManagement.controller;

import com.example.hotelManagement.model.Room;
import com.example.hotelManagement.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    private Map<String, String> msg(String code, String message) {
        Map<String, String> m = new HashMap<>();
        m.put("code", code);
        m.put("message", message);
        return m;
    }

    @Operation(summary = "Create a new room")
    @PostMapping("/rooms/post")
    public ResponseEntity<?> createRoom(@RequestBody Room room) {
        try {
            roomService.createRoom(room);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(msg("POSTSUCCESS", "room added successfully"));
        } catch (IllegalStateException ex) {
            if ("DUPLICATE_ROOM_NUMBER".equals(ex.getMessage())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(msg("ADDFAILS", "Room already exist"));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(msg("ADDFAILS", "Unable to add room"));
        }
    }

    @Operation(summary = "Get all rooms")
    @GetMapping("/room/all")
    public ResponseEntity<?> getAllRooms() {
        List<Room> rooms = roomService.getAllRooms();
        if (rooms == null || rooms.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(msg("GETALLFAILS", "room list is empty"));
        }
        return ResponseEntity.ok(rooms);
    }

    @Operation(summary = "Get room by ID")
    @GetMapping("/room/{room_id}")
    public ResponseEntity<?> getRoom(@PathVariable("room_id") Integer roomId) {
        return roomService.getRoomById(roomId)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(msg("GETALLFAILS", "room doesn't exist")));
    }

    @Operation(summary = "Get available rooms by type")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Available rooms retrieved"),
        @ApiResponse(responseCode = "404", description = "No room found with given type")
    })
    @GetMapping("/rooms/available/{roomTypeId}")
    public ResponseEntity<?> getAvailableRoomsByType(@PathVariable Integer roomTypeId) {
        List<Room> rooms = roomService.getAvailableRoomsByType(roomTypeId);
        if (rooms == null || rooms.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(msg("GETALLFAILS", "No room found with given type"));
        }
        return ResponseEntity.ok(rooms);
    }

    @Operation(summary = "Get rooms by location")
    @GetMapping("/rooms/location/{location}")
    public ResponseEntity<?> getRoomsByLocation(@PathVariable String location) {
        List<Room> rooms = roomService.getRoomsByLocation(location);
        if (rooms == null || rooms.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(msg("GETALLFAILS", "No room found with given location"));
        }
        return ResponseEntity.ok(rooms);
    }

    @Operation(summary = "Get rooms by amenity ID")
    @GetMapping("/rooms/amenities/{amenityId}")
    public ResponseEntity<?> getRoomsByAmenity(@PathVariable Integer amenityId) {
        List<Room> rooms = roomService.getRoomsByAmenity(amenityId);
        if (rooms == null || rooms.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(msg("GETALLFAILS", "amenity doesn't exist with given id"));
        }
        return ResponseEntity.ok(rooms);
    }

    
//    @Operation(summary = "Get rooms by amenity ID (duplicate path)")
//    @GetMapping("/rooms/amenities/{amenity_id}")
//    public ResponseEntity<?> getRoomsByAmenityDuplicate(@PathVariable("amenity_id") Integer amenityId) {
//        return getRoomsByAmenity(amenityId); 
//    }

    @Operation(summary = "Update room details")
    @PutMapping("/room/update/{room_id}")
    public ResponseEntity<?> updateRoom(@PathVariable("room_id") Integer roomId, @RequestBody Room updated) {
        try {
            roomService.updateRoom(roomId, updated);
            return ResponseEntity.ok(msg("UPDATESUCCESS", "Room updated successfully"));
        } catch (IllegalStateException ex) {
            if ("ROOM_NOT_FOUND".equals(ex.getMessage())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(msg("UPDTFAILS", "Room doesn't exist"));
            }
            if ("DUPLICATE_ROOM_NUMBER".equals(ex.getMessage())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(msg("UPDTFAILS", "Room number already in use"));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(msg("UPDTFAILS", "Unable to update room"));
          
        }
    }
    
    @Operation(summary = "Delete room by ID")
    @DeleteMapping("/rooms/delete/{roomId}")
    public ResponseEntity<?> deleteRoom(@PathVariable("roomId") Integer roomId) {
        try {
            roomService.deleteRoom(roomId);
            return ResponseEntity.ok(msg("DELETESUCCESS", "Room deleted successfully"));
        } catch (IllegalStateException ex) {
            if ("ROOM_NOT_FOUND".equals(ex.getMessage())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(msg("DELETEFAILS", "Room doesn't exist"));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(msg("DELETEFAILS", "Unable to delete room"));
        }
    }
}