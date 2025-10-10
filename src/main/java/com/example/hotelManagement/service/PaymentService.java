package com.example.hotelManagement.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.hotelManagement.model.ApiResponse;
import com.example.hotelManagement.model.Payment;

@Service
public interface PaymentService
{

    ApiResponse addPayment(Payment payment);

    List<Payment> getAllPayments();

    Payment getPaymentById(Integer paymentId);

    ApiResponse deletePaymentById(Integer paymentId);

    List<Payment> getPaymentsByStatus(String status);

    java.math.BigDecimal getTotalRevenue();
}