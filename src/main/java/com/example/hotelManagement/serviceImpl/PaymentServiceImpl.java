package com.example.hotelManagement.serviceImpl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hotelManagement.model.Payment;
import com.example.hotelManagement.model.Reservation;
import com.example.hotelManagement.repository.PaymentRepository;
import com.example.hotelManagement.repository.ReservationRepository;
import com.example.hotelManagement.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    
    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public Optional<Payment> getPaymentById(Integer paymentId) {
        return paymentRepository.findById(paymentId);
    }

    @Override
    public List<Payment> getPaymentsByStatus(String status) {
        return paymentRepository.findByPaymentStatus(status);
    }

    @Override
    public BigDecimal getTotalRevenue() {
        return paymentRepository.findAll()
                .stream()
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public Payment addPayment(Payment payment) {
        if (payment.getReservation() != null && payment.getReservation().getReservationId() != null) {
            Reservation reservation = reservationRepository.findById(payment.getReservation().getReservationId())
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
            payment.setReservation(reservation);
        } else {
            throw new RuntimeException("Reservation ID is required");
        }
        return paymentRepository.save(payment);
    }

    @Override
    public boolean deletePayment(Integer paymentId) {
        if (paymentRepository.existsById(paymentId)) {
            paymentRepository.deleteById(paymentId);
            return true;
        }
        return false;
    }
}
