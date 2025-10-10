package com.example.hotelManagement.controller;

import com.example.hotelManagement.model.Room;
import com.example.hotelManagement.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    // Utility: build {code, message} response
    private Map<String, String> msg(String code, String message) {
        Map<String, String> m = new HashMap<>();
        m.put("code", code);
        m.put("message", message);
        return m;
    }

    /**
     * /api/rooms/post  POST  Create a new room.
     * Success:
     *  {
     *    "code": "POSTSUCCESS",
     *    "message": "room added successfully"
     *  }
     * Failure (room already exist):
     *  {
     *    "code": "ADDFAILS",
     *    "message": "Room already exist"
     *  }
     */
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

    /**
     * /api/room/all  GET  Get all rooms
     * Success: returns List<Room>
     * Failure (empty):
     *  {
     *    "code": "GETALLFAILS",
     *    "message": "room list is empty"
     *  }
     */
    @GetMapping("/room/all")
    public ResponseEntity<?> getAllRooms() {
        List<Room> rooms = roomService.getAllRooms();
        if (rooms == null || rooms.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(msg("GETALLFAILS", "room list is empty"));
        }
        return ResponseEntity.ok(rooms);
        // (intentionally returning the list, as per your spec comment)
    }

    /**
     * /api/room/{room_id}  GET  Get a room
     * Success: returns Room
     * Failure:
     *  {
     *    "code": "GETALLFAILS",
     *    "message": "room doesn't exist"
     *  }
     */
    @GetMapping("/room/{room_id}")
    public ResponseEntity<?> getRoom(@PathVariable("room_id") Integer roomId) {
        return roomService.getRoomById(roomId)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(msg("GETALLFAILS", "room doesn't exist")));
    }

    /**
     * /api/rooms/available/{roomTypeId}  GET  Available rooms of a specific type
     * Success: returns List<Room>
     * Failure:
     *  {
     *    "code": "GETALLFAILS",
     *    "message": "No room found with given type"
     *  }
     */
    @GetMapping("/rooms/available/{roomTypeId}")
    public ResponseEntity<?> getAvailableRoomsByType(@PathVariable Integer roomTypeId) {
        List<Room> rooms = roomService.getAvailableRoomsByType(roomTypeId);
        if (rooms == null || rooms.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(msg("GETALLFAILS", "No room found with given type"));
        }
        return ResponseEntity.ok(rooms);
    }

    /**
     * /api/rooms/location/{location}  GET  Rooms by location
     * Success: returns List<Room>
     * Failure (not specified in your examples): returning GETALLFAILS for consistency
     */
    @GetMapping("/rooms/location/{location}")
    public ResponseEntity<?> getRoomsByLocation(@PathVariable String location) {
        List<Room> rooms = roomService.getRoomsByLocation(location);
        if (rooms == null || rooms.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(msg("GETALLFAILS", "No room found with given location"));
        }
        return ResponseEntity.ok(rooms);
    }

    /**
     * /api/rooms/amenities/{amenityId}  GET  Rooms that have a specific amenity
     * Success: returns List<Room>
     * Failure:
     *  {
     *    "code": "GETALLFAILS",
     *    "message": "amenity doesn't exist with given id"
     *  }
     */
    @GetMapping("/rooms/amenities/{amenityId}")
    public ResponseEntity<?> getRoomsByAmenity(@PathVariable Integer amenityId) {
        List<Room> rooms = roomService.getRoomsByAmenity(amenityId);
        if (rooms == null || rooms.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(msg("GETALLFAILS", "amenity doesn't exist with given id"));
        }
        return ResponseEntity.ok(rooms);
    }

    /**
     * /api/rooms/{amenity_id}  GET  (Duplicate path per your spec)
     * We'll wire it to the same logic as above.
     */
    @GetMapping("/rooms/{amenity_id}")
    public ResponseEntity<?> getRoomsByAmenityDuplicate(@PathVariable("amenity_id") Integer amenityId) {
        return getRoomsByAmenity(amenityId);
    }

    /**
     * /api/room/update/{room_id}  PUT  Update room details
     * Success:
     *  {
     *    "code": "UPDATESUCCESS",
     *    "message": "Room updated successfully"
     *  }
     * Failure:
     *  {
     *    "code": "UPDTFAILS",
     *    "message": "Room doesn't exist"
     *  }
     */
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
}