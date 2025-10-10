package com.example.hotelManagement.service;

import com.example.hotelManagement.model.Reservation;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

	public interface ReservationService {

	    /**
	     * POST /api/reservation/
	     * On success -> controller should return:
	     *   { "code": "POSTSUCCESS", "message": "Reservation added successfully" }
	     * On failure (overlap/duplicate for same room & dates) -> controller should return:
	     *   { "code": "ADDFAILS", "message": "reservation already exist" }
	     */
	    Reservation createReservation(Reservation reservation);

	    /**
	     * GET /api/reservation/all
	     * On non-empty -> return list.
	     * On empty -> controller should return:
	     *   { "code": "GETALLFAILS", "message": "reservation list is empty" }
	     */
	    List<Reservation> getAllReservations();

	    /**
	     * GET /api/reservation/{reservation_id}
	     * On not found -> controller should return:
	     *   { "code": "GETFAILS", "message": "Reservation doesn't exist" }
	     */
	    Optional<Reservation> getReservationById(Integer reservationId);

	    /**
	     * GET /api/reservations/date-range?startDate=YYYY-MM-DD&endDate=YYYY-MM-DD
	     * Returns reservations intersecting the date range (inclusive).
	     * (No explicit failure contract provided; return empty list if none)
	     */
	    List<Reservation> getReservationsWithinDateRange(LocalDate startDate, LocalDate endDate);

	    /**
	     * PUT /api/reservation/update/{reservation_id}
	     * On success -> controller should return:
	     *   { "code": "UPDATESUCCESS", "message": "Reservation updated successfully" }
	     * On failure (not found/overlap) -> controller should return:
	     *   { "code": "UPDTFAILS", "message": "Reservation doesn't updated successfully" }
	     * (Text preserved per your spec; feel free to tweak message grammar in controller.)
	     */
	    Optional<Reservation> updateReservation(Integer reservationId, Reservation updated);

	    /**
	     * DELETE /api/reservation/{reservation_id}
	     * On success -> controller should return:
	     *   { "code": "DELETESUCCESS", "message": "reservation deleted successfully" }
	     * On failure (not found) -> controller should return:
	     *   { "code": "DLTFAILS", "message": "Reservation doesn't exist" }
	     */
	    boolean deleteReservation(Integer reservationId);
	}



