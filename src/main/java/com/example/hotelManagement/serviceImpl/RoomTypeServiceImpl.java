package com.example.hotelManagement.serviceImpl;

import com.example.hotelManagement.model.RoomType;
import com.example.hotelManagement.repository.RoomTypeRepository;
import com.example.hotelManagement.service.RoomTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * Concrete implementation of RoomTypeService.
 */
@Service
public class RoomTypeServiceImpl implements RoomTypeService {

    private final RoomTypeRepository roomTypeRepository;

    public RoomTypeServiceImpl(RoomTypeRepository roomTypeRepository) {
        this.roomTypeRepository = roomTypeRepository;
    }

    /**
     * Create a new RoomType if typeName is unique (case-insensitive).
     * Returns true if saved, false if duplicate.
     */
    @Override
    @Transactional
    public boolean addRoomType(RoomType roomType) {
        if (roomType == null || isBlank(roomType.getTypeName())) {
            // Treat invalid payloads as non-created (controller can decide message shape).
            return false;
        }

        boolean duplicate = roomTypeRepository.existsByTypeNameIgnoreCase(roomType.getTypeName());
        if (duplicate) {
            return false;
        }

        roomTypeRepository.save(roomType);
        return true;
    }

    /**
     * Update existing RoomType by id. If not found, returns false.
     * This does a "partial update": only non-null fields from payload are applied.
     */
    @Override
    @Transactional
    public boolean updateRoomType(Integer roomTypeId, RoomType updatedRoomType) {
        return roomTypeRepository.findById(roomTypeId).map(existing -> {
            // Apply only non-null values from the request to avoid clobbering fields
            if (!isBlank(updatedRoomType.getTypeName())) {
                existing.setTypeName(updatedRoomType.getTypeName());
            }
            if (updatedRoomType.getDescription() != null) {
                existing.setDescription(updatedRoomType.getDescription());
            }
            if (updatedRoomType.getMax_occupancy() != null) {
                existing.setMax_occupancy(updatedRoomType.getMax_occupancy());
            }
            if (updatedRoomType.getPricePerNight() != null) {
                existing.setPricePerNight(updatedRoomType.getPricePerNight());
            }

            roomTypeRepository.save(existing);
            return true;
        }).orElse(false);
    }

    /**
     * Return entity or null to let the controller decide the response shape.
     */
    @Override
    @Transactional(readOnly = true)
    public RoomType getRoomTypeById(Integer roomTypeId) {
        return roomTypeRepository.findById(roomTypeId).orElse(null);
    }

    // ---- helpers ----
    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
