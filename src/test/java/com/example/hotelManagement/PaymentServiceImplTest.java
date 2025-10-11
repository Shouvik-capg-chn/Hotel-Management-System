package com.example.hotelManagement;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.List;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.hotelManagement.model.Payment;
import com.example.hotelManagement.model.Reservation;
import com.example.hotelManagement.repository.PaymentRepository;
import com.example.hotelManagement.repository.ReservationRepository;
import com.example.hotelManagement.serviceImpl.PaymentServiceImpl;

class PaymentServiceImplTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllPayments() {
        Payment p1 = new Payment(); p1.setAmount(new BigDecimal("1000"));
        Payment p2 = new Payment(); p2.setAmount(new BigDecimal("2000"));

        when(paymentRepository.findAll()).thenReturn(Arrays.asList(p1, p2));

        List<Payment> result = paymentService.getAllPayments();

        assertEquals(2, result.size());
    }

    @Test
    void testGetPaymentById_Found() {
        Payment payment = new Payment(); payment.setAmount(new BigDecimal("1500"));

        when(paymentRepository.findById(1)).thenReturn(Optional.of(payment));

        Optional<Payment> result = paymentService.getPaymentById(1);

        assertTrue(result.isPresent());
        assertEquals(new BigDecimal("1500"), result.get().getAmount());
    }

    @Test
    void testGetPaymentById_NotFound() {
        when(paymentRepository.findById(1)).thenReturn(Optional.empty());

        Optional<Payment> result = paymentService.getPaymentById(1);

        assertFalse(result.isPresent());
    }

    @Test
    void testGetPaymentsByStatus() {
        Payment p1 = new Payment(); p1.setPaymentStatus("PAID");

        when(paymentRepository.findByPaymentStatus("PAID")).thenReturn(List.of(p1));

        List<Payment> result = paymentService.getPaymentsByStatus("PAID");

        assertEquals(1, result.size());
        assertEquals("PAID", result.get(0).getPaymentStatus());
    }

    @Test
    void testGetTotalRevenue() {
        Payment p1 = new Payment(); p1.setAmount(new BigDecimal("1000"));
        Payment p2 = new Payment(); p2.setAmount(new BigDecimal("2000"));

        when(paymentRepository.findAll()).thenReturn(Arrays.asList(p1, p2));

        BigDecimal total = paymentService.getTotalRevenue();

        assertEquals(new BigDecimal("3000"), total);
    }

    @Test
    void testAddPayment_Success() {
        Reservation reservation = new Reservation(); reservation.setReservationId(1);
        Payment payment = new Payment(); payment.setReservation(reservation);

        when(reservationRepository.findById(1)).thenReturn(Optional.of(reservation));
        when(paymentRepository.save(payment)).thenReturn(payment);

        Payment result = paymentService.addPayment(payment);

        assertNotNull(result);
        verify(paymentRepository).save(payment);
    }

    @Test
    void testAddPayment_ReservationNotFound() {
        Reservation reservation = new Reservation(); reservation.setReservationId(1);
        Payment payment = new Payment(); payment.setReservation(reservation);

        when(reservationRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            paymentService.addPayment(payment);
        });

        assertEquals("Reservation not found", exception.getMessage());
    }

    @Test
    void testAddPayment_ReservationIdMissing() {
        Payment payment = new Payment();
        payment.setReservation(new Reservation()); // no ID

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            paymentService.addPayment(payment);
        });

        assertEquals("Reservation ID is required", exception.getMessage());
    }

    @Test
    void testDeletePayment_Success() {
        when(paymentRepository.existsById(1)).thenReturn(true);

        boolean result = paymentService.deletePayment(1);

        assertTrue(result);
        verify(paymentRepository).deleteById(1);
    }

    @Test
    void testDeletePayment_NotFound() {
        when(paymentRepository.existsById(1)).thenReturn(false);

        boolean result = paymentService.deletePayment(1);

        assertFalse(result);
        verify(paymentRepository, never()).deleteById(any());
    }
}