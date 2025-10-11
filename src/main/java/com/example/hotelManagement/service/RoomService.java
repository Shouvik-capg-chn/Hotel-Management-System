package com.example.hotelManagement.service;

import com.example.hotelManagement.model.Room;

import java.util.List;
import java.util.Optional;

public interface RoomService {

    // POST /api/rooms/post
    void createRoom(Room room);

    // GET /api/room/all
    List<Room> getAllRooms();

    // GET /api/room/{room_id}
    Optional<Room> getRoomById(Integer roomId);

    // GET /api/rooms/available/{roomTypeId}
    List<Room> getAvailableRoomsByType(Integer roomTypeId);

    // GET /api/rooms/location/{location}
    List<Room> getRoomsByLocation(String location);

    // GET /api/rooms/amenities/{amenityId} and GET /api/rooms/{amenity_id}
    List<Room> getRoomsByAmenity(Integer amenityId);

    // PUT /api/room/update/{room_id}
    Room updateRoom(Integer roomId, Room updated);
    
    // DELETE /api/rooms/delete/{roomId}
    void deleteRoom(Integer roomId);
}
