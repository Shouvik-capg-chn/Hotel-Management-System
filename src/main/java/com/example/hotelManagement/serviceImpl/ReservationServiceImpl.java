package com.example.hotelManagement.serviceImpl;

import com.example.hotelManagement.model.Reservation;
import com.example.hotelManagement.model.Room;
import com.example.hotelManagement.repository.ReservationRepository;
import com.example.hotelManagement.repository.RoomRepository;
import com.example.hotelManagement.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;
    
    @Autowired
    private RoomRepository roomRepository;

    @Override
    public Reservation createReservation(Reservation reservation) {
        if (reservation.getRoom() != null && reservation.getRoom().getRoomId() != null) {
            Room room = roomRepository.findById(reservation.getRoom().getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found"));
            reservation.setRoom(room);
        }
        return reservationRepository.save(reservation);
    }

    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation getReservationById(Integer id) {
        return reservationRepository.findById(id).orElse(null);
    }

    @Override
    public List<Reservation> getReservationsByDateRange(LocalDate startDate, LocalDate endDate) {
        return reservationRepository.findByCheckInDateBetween(startDate, endDate);
    }

    @Override
    public Reservation updateReservation(Integer id, Reservation updatedReservation) {
        Optional<Reservation> existing = reservationRepository.findById(id);
        if (existing.isPresent()) {
            Reservation reservation = existing.get();
            reservation.setGuestName(updatedReservation.getGuestName());
            reservation.setGuestEmail(updatedReservation.getGuestEmail());
            reservation.setGuestPhone(updatedReservation.getGuestPhone());
            reservation.setCheckInDate(updatedReservation.getCheckInDate());
            reservation.setCheckOutDate(updatedReservation.getCheckOutDate());

            if (updatedReservation.getRoom() != null && updatedReservation.getRoom().getRoomId() != null) {
                Room room = roomRepository.findById(updatedReservation.getRoom().getRoomId())
                    .orElseThrow(() -> new RuntimeException("Room not found"));
                reservation.setRoom(room);
            }

            return reservationRepository.save(reservation);
        }
        return null;
    }

    @Override
    public void deleteReservation(Integer id) {
        reservationRepository.deleteById(id);
    }
}