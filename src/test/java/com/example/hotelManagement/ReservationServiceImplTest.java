package com.example.hotelManagement;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.hotelManagement.model.Reservation;
import com.example.hotelManagement.model.Room;
import com.example.hotelManagement.repository.ReservationRepository;
import com.example.hotelManagement.repository.RoomRepository;
import com.example.hotelManagement.serviceImpl.ReservationServiceImpl;

class ReservationServiceImplTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateReservation_Success() {
        Room room = new Room();
        room.setRoomId(1);

        Reservation reservation = new Reservation();
        reservation.setRoom(room);

        when(roomRepository.findById(1)).thenReturn(Optional.of(room));
        when(reservationRepository.save(reservation)).thenReturn(reservation);

        Reservation result = reservationService.createReservation(reservation);

        assertNotNull(result);
        verify(reservationRepository).save(reservation);
    }

    @Test
    void testCreateReservation_RoomNotFound() {
        Room room = new Room();
        room.setRoomId(1);

        Reservation reservation = new Reservation();
        reservation.setRoom(room);

        when(roomRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reservationService.createReservation(reservation);
        });

        assertEquals("Room not found", exception.getMessage());
    }

    @Test
    void testGetAllReservations() {
        Reservation r1 = new Reservation(); r1.setGuestName("Alice");
        Reservation r2 = new Reservation(); r2.setGuestName("Bob");

        when(reservationRepository.findAll()).thenReturn(Arrays.asList(r1, r2));

        List<Reservation> result = reservationService.getAllReservations();

        assertEquals(2, result.size());
    }

    @Test
    void testGetReservationById_Found() {
        Reservation reservation = new Reservation();
        reservation.setGuestName("Alice");

        when(reservationRepository.findById(1)).thenReturn(Optional.of(reservation));

        Reservation result = reservationService.getReservationById(1);

        assertNotNull(result);
        assertEquals("Alice", result.getGuestName());
    }

    @Test
    void testGetReservationById_NotFound() {
        when(reservationRepository.findById(1)).thenReturn(Optional.empty());

        Reservation result = reservationService.getReservationById(1);

        assertNull(result);
    }

    @Test
    void testGetReservationsByDateRange() {
        Reservation r1 = new Reservation(); r1.setCheckInDate(LocalDate.of(2025, 10, 1));
        Reservation r2 = new Reservation(); r2.setCheckInDate(LocalDate.of(2025, 10, 5));

        when(reservationRepository.findByCheckInDateBetween(
                LocalDate.of(2025, 10, 1), LocalDate.of(2025, 10, 10)))
            .thenReturn(Arrays.asList(r1, r2));

        List<Reservation> result = reservationService.getReservationsByDateRange(
                LocalDate.of(2025, 10, 1), LocalDate.of(2025, 10, 10));

        assertEquals(2, result.size());
    }

    @Test
    void testUpdateReservation_Success() {
        Reservation existing = new Reservation();
        existing.setGuestName("Old Name");

        Reservation updated = new Reservation();
        updated.setGuestName("New Name");
        updated.setGuestEmail("new@example.com");
        updated.setGuestPhone("1234567890");
        updated.setCheckInDate(LocalDate.of(2025, 10, 1));
        updated.setCheckOutDate(LocalDate.of(2025, 10, 5));
        Room room = new Room(); room.setRoomId(1);
        updated.setRoom(room);

        when(reservationRepository.findById(1)).thenReturn(Optional.of(existing));
        when(roomRepository.findById(1)).thenReturn(Optional.of(room));
        when(reservationRepository.save(any())).thenReturn(existing);

        Reservation result = reservationService.updateReservation(1, updated);

        assertNotNull(result);
        assertEquals("New Name", result.getGuestName());
    }

    @Test
    void testUpdateReservation_NotFound() {
        Reservation updated = new Reservation();
        updated.setGuestName("New Name");

        when(reservationRepository.findById(1)).thenReturn(Optional.empty());

        Reservation result = reservationService.updateReservation(1, updated);

        assertNull(result);
    }

    @Test
    void testUpdateReservation_RoomNotFound() {
        Reservation existing = new Reservation();
        Reservation updated = new Reservation();
        Room room = new Room(); room.setRoomId(1);
        updated.setRoom(room);

        when(reservationRepository.findById(1)).thenReturn(Optional.of(existing));
        when(roomRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reservationService.updateReservation(1, updated);
        });

        assertEquals("Room not found", exception.getMessage());
    }

    @Test
    void testDeleteReservation() {
        reservationService.deleteReservation(1);
        verify(reservationRepository).deleteById(1);
    }
}
