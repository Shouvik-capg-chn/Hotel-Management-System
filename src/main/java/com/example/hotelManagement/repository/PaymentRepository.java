package com.example.hotelManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.hotelManagement.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer>
{
    List<Payment> findByPaymentStatus(String paymentStatus);
}
