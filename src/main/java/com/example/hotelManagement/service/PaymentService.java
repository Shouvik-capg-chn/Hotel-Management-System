package com.example.hotelManagement.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.example.hotelManagement.model.Payment;

public interface PaymentService {
    List<Payment> getAllPayments();
    Optional<Payment> getPaymentById(Integer paymentId);
    List<Payment> getPaymentsByStatus(String status);
    BigDecimal getTotalRevenue();
    Payment addPayment(Payment payment);
    boolean deletePayment(Integer paymentId);
}