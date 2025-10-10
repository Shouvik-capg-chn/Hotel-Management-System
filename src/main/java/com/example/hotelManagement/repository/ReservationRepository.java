package com.example.hotelManagement.repository;

import com.example.hotelManagement.model.Reservation;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    /**
     * Check if there is any overlapping reservation for a room.
     * Overlap rule: (existing.checkIn < new.checkOut) AND (existing.checkOut > new.checkIn)
     */
    @Query("""
        SELECT CASE WHEN COUNT(r) > 0 THEN TRUE ELSE FALSE END
        FROM Reservation r
        WHERE r.room.roomId = :roomId
          AND r.checkInDate < :newCheckOut
          AND r.checkOutDate > :newCheckIn
    """)
    boolean existsOverlapping(@Param("roomId") Integer roomId,
                              @Param("newCheckIn") LocalDate newCheckIn,
                              @Param("newCheckOut") LocalDate newCheckOut);

    /**
     * Same as existsOverlapping, but excludes current reservation id (for updates).
     */
    @Query("""
        SELECT CASE WHEN COUNT(r) > 0 THEN TRUE ELSE FALSE END
        FROM Reservation r
        WHERE r.room.roomId = :roomId
          AND r.checkInDate < :newCheckOut
          AND r.checkOutDate > :newCheckIn
          AND r.reservationId <> :excludeId
    """)
    boolean existsOverlappingExcludingId(@Param("roomId") Integer roomId,
                                         @Param("newCheckIn") LocalDate newCheckIn,
                                         @Param("newCheckOut") LocalDate newCheckOut,
                                         @Param("excludeId") Integer excludeId);

    /**
     * Find reservations that intersect the given date range.
     * Intersection rule: (checkIn <= end) AND (checkOut >= start)
     */
    @Query("""
        SELECT r FROM Reservation r
        WHERE r.checkInDate <= :endDate
          AND r.checkOutDate >= :startDate
        ORDER BY r.checkInDate ASC, r.room.roomId ASC
    """)
    List<Reservation> findIntersectingDateRange(@Param("startDate") LocalDate startDate,
                                                @Param("endDate") LocalDate endDate);
}

