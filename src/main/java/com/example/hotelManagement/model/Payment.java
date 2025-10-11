package com.example.hotelManagement.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Integer paymentId;

    // Many payments belong to one reservation
    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservationId;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Amount must be a valid monetary value")
    @Column(name = "amount", precision = 10, scale = 2, nullable = false)
    private BigDecimal amount;

    @NotNull(message = "Payment date is required")
    @PastOrPresent(message = "Payment date cannot be in the future")
    @Column(name = "payment_date", nullable = false)
    private LocalDate paymentDate;

    @Size(max = 50, message = "Payment status must be less than or equal to 50 characters")
    @Column(name = "payment_status", length = 50)
    private String paymentStatus;

    // Constructors
    public Payment() {
    }

    public Payment(Reservation reservation, BigDecimal amount, LocalDate paymentDate, String paymentStatus) {
        this.reservationId = reservation;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentStatus = paymentStatus;
    }

    // Getters and Setters
    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public Reservation getReservation() {
        return reservationId;
    }

    public void setReservation(Reservation reservation) {
        this.reservationId = reservation;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    @Override
    public String toString() {
        return "Payment [paymentId=" + paymentId + ", reservation=" + reservationId + ", amount=" + amount
                + ", paymentDate=" + paymentDate + ", paymentStatus=" + paymentStatus + "]";
    }
}