package com.example.hotelManagement.serviceImpl;

import com.example.hotelManagement.model.Reservation;
import com.example.hotelManagement.repository.ReservationRepository;
import com.example.hotelManagement.service.ReservationService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class ReservationServiceImpl implements ReservationService
{

    private final ReservationRepository reservationRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    // ---------------------------
    // Helpers / Validations
    // ---------------------------

    private void validateReservationRequiredFields(Reservation r) {
        if (r == null) {
            throw new IllegalArgumentException("Reservation payload is required");
        }
        if (r.getGuestName() == null || r.getGuestName().trim().isEmpty()) {
            throw new IllegalArgumentException("guest_name is required");
        }
        if (r.getRoom() == null || r.getRoom().getRoomId() == null) {
            throw new IllegalArgumentException("room_id is required");
        }
        if (r.getCheckInDate() == null || r.getCheckOutDate() == null) {
            throw new IllegalArgumentException("check_in_date and check_out_date are required");
        }
        if (!r.getCheckInDate().isBefore(r.getCheckOutDate())) {
            throw new IllegalArgumentException("check_in_date must be before check_out_date");
        }
    }

    private void ensureNoOverlap(Integer roomId, LocalDate in, LocalDate out) {
        boolean overlap = reservationRepository.existsOverlapping(roomId, in, out);
        if (overlap) {
            // Controller should map this to code/message:
            // { "code": "ADDFAILS", "message": "reservation already exist" }
            throw new IllegalStateException("reservation already exist");
        }
    }

    private void ensureNoOverlapExcluding(Integer reservationId, Integer roomId, LocalDate in, LocalDate out) {
        boolean overlap = reservationRepository.existsOverlappingExcludingId(roomId, in, out, reservationId);
        if (overlap) {
            // Controller should map this to code/message:
            // { "code": "UPDTFAILS", "message": "Reservation doesn't updated successfully" }
            throw new IllegalStateException("overlapping reservation exists for the given room and dates");
        }
    }

    // ---------------------------
    // Service methods
    // ---------------------------

    public Reservation createReservation(Reservation reservation) {
        validateReservationRequiredFields(reservation);

        Integer roomId = reservation.getRoom().getRoomId();
        ensureNoOverlap(roomId, reservation.getCheckInDate(), reservation.getCheckOutDate());

        return reservationRepository.save(reservation);
    }

    @Transactional(readOnly = true)
    public List<Reservation> getAllReservations() {
        // Controller should return GETALLFAILS if this is empty.
        return reservationRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Reservation> getReservationById(Integer reservationId) {
        // Controller should return GETFAILS if not present.
        return reservationRepository.findById(reservationId);
    }

    @Transactional(readOnly = true)
    public List<Reservation> getReservationsWithinDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("startDate and endDate are required");
        }
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("startDate must be on/before endDate");
        }
        return reservationRepository.findIntersectingDateRange(startDate, endDate);
    }

    public Optional<Reservation> updateReservation(Integer reservationId, Reservation updated) {
        if (reservationId == null) {
            throw new IllegalArgumentException("reservation_id is required");
        }
        return reservationRepository.findById(reservationId).map(existing -> {
            // If incoming contains nulls, keep existing values (basic patch behavior)
            if (updated.getGuestName() != null) existing.setGuestName(updated.getGuestName());
            if (updated.getGuestEmail() != null) existing.setGuestEmail(updated.getGuestEmail());
            if (updated.getGuestPhone() != null) existing.setGuestPhone(updated.getGuestPhone());
            if (updated.getCheckInDate() != null) existing.setCheckInDate(updated.getCheckInDate());
            if (updated.getCheckOutDate() != null) existing.setCheckOutDate(updated.getCheckOutDate());
            if (updated.getRoom() != null && updated.getRoom().getRoomId() != null) {
                existing.setRoom(updated.getRoom());
            }

            // Re-validate dates and required fields after merge
            validateReservationRequiredFields(existing);

            // Ensure no overlap excluding this reservation id
            ensureNoOverlapExcluding(
                existing.getReservationId(),
                existing.getRoom().getRoomId(),
                existing.getCheckInDate(),
                existing.getCheckOutDate()
            );

            return reservationRepository.save(existing);
        });
    }

    public boolean deleteReservation(Integer reservationId) {
        if (reservationId == null) {
            throw new IllegalArgumentException("reservation_id is required");
        }
        if (!reservationRepository.existsById(reservationId)) {
            // Controller should map this to:
            // { "code": "DLTFAILS", "message": "Reservation doesn't exist" }
            return false;
        }
        reservationRepository.deleteById(reservationId);
        return true;
    }
}
