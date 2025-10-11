package com.example.hotelManagement.repository;

import com.example.hotelManagement.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer>
{
    List<Reservation> findByCheckInDateBetween(LocalDate startDate, LocalDate endDate);
}
