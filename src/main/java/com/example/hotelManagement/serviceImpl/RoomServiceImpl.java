package com.example.hotelManagement.serviceImpl;

import com.example.hotelManagement.model.Room;
import com.example.hotelManagement.repository.RoomRepository;
import com.example.hotelManagement.service.RoomService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public void createRoom(Room room) {
        if (room.getRoomNumber() != null && roomRepository.existsByRoomNumber(room.getRoomNumber())) {
            // Let the controller decide the message/HTTP code; we just signal a problem here.
            throw new IllegalStateException("DUPLICATE_ROOM_NUMBER");
        }
        roomRepository.save(room);
    }

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public Optional<Room> getRoomById(Integer roomId) {
        return roomRepository.findById(roomId);
    }

    @Override
    public List<Room> getAvailableRoomsByType(Integer roomTypeId) {
        return roomRepository.findByRoomType_RoomTypeIdAndIsAvailableTrue(roomTypeId);
    }

    @Override
    public List<Room> getRoomsByLocation(String location) {
        return roomRepository.findByLocationIgnoreCase(location);
    }

    @Override
    public List<Room> getRoomsByAmenity(Integer amenityId) {
        return roomRepository.findByAmenityId(amenityId);
    }

    @Override
    public Room updateRoom(Integer roomId, Room updated) {
        Room existing = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalStateException("ROOM_NOT_FOUND"));

        // Update fields only if provided (simple partial update)
        if (updated.getRoomNumber() != null && !updated.getRoomNumber().equals(existing.getRoomNumber())) {
            if (roomRepository.existsByRoomNumber(updated.getRoomNumber())) {
                throw new IllegalStateException("DUPLICATE_ROOM_NUMBER");
            }
            existing.setRoomNumber(updated.getRoomNumber());
        }

        if (updated.getRoomType() != null) {
            existing.setRoomType(updated.getRoomType());
        }
        if (updated.getIsAvailable() != null) {
            existing.setIsAvailable(updated.getIsAvailable());
        }
        if (updated.getDescription() != null) {
            existing.setDescription(updated.getDescription());
        }
        if (updated.getLocation() != null) {
            existing.setLocation(updated.getLocation());
        }
        if (updated.getPrice() != null) {
            existing.setPrice(updated.getPrice());
        }

        return roomRepository.save(existing);
    }
    
    @Override
    public void deleteRoom(Integer roomId) {
        if (!roomRepository.existsById(roomId)) {
            throw new IllegalStateException("ROOM_NOT_FOUND");
        }
        roomRepository.deleteById(roomId);
    }
}