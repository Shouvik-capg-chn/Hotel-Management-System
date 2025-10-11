package com.example.hotelManagement.repository;

import com.example.hotelManagement.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

    boolean existsByRoomNumber(Integer roomNumber);

    // Available rooms for a specific type
    List<Room> findByRoomType_RoomTypeIdAndIsAvailableTrue(Integer roomTypeId);

    // Rooms by location (case-insensitive)
    List<Room> findByLocationIgnoreCase(String location);

    // Rooms by amenity (via join table room_amenity)
    @Query(value = """
    	    SELECT r.* 
    	    FROM Room r 
    	    JOIN roomamenity ra ON r.room_id = ra.room_id 
    	    WHERE ra.amenity_id = :amenityId
    	    """, nativeQuery = true)
    	List<Room> findByAmenityId(@Param("amenityId") Integer amenityId);
}
