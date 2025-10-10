package com.example.hotelManagement.service;

import com.example.hotelManagement.model.RoomType;

/**
 * Service contract used by the RoomTypeController.
 * Methods are intentionally simple to keep controller logic clean.
 */
public interface RoomTypeService {

    /**
     * Create a new RoomType.
     * @param roomType payload to save
     * @return true if created, false if a RoomType with the same name already exists
     */
    boolean addRoomType(RoomType roomType);

    /**
     * Update an existing RoomType by id.
     * @param roomTypeId id of the record to update
     * @param updatedRoomType fields to update (nulls are ignored for partial updates)
     * @return true if updated, false if the id does not exist
     */
    boolean updateRoomType(Integer roomTypeId, RoomType updatedRoomType);

    /**
     * Fetch a RoomType by id.
     * @param roomTypeId id to lookup
     * @return RoomType if found; otherwise null
     */
    RoomType getRoomTypeById(Integer roomTypeId);
}