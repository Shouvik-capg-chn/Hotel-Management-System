package com.example.hotelManagement.service;

import com.example.hotelManagement.model.Reservation;

import java.time.LocalDate;
import java.util.List;

public interface ReservationService {
    Reservation createReservation(Reservation reservation);
    List<Reservation> getAllReservations();
    Reservation getReservationById(Integer id);
    List<Reservation> getReservationsByDateRange(LocalDate startDate, LocalDate endDate);
    Reservation updateReservation(Integer id, Reservation reservation);
    void deleteReservation(Integer id);
}
