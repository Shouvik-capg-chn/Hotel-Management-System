package com.example.hotelManagement.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Integer reservationId;

    @NotBlank(message = "Guest name is required")
    @Size(max = 255, message = "Guest name must be less than or equal to 255 characters")
    @Column(name = "guest_name", nullable = false, length = 255)
    private String guestName;

    @Email(message = "Guest email must be valid")
    @Size(max = 255, message = "Guest email must be less than or equal to 255 characters")
    @Column(name = "guest_email", length = 255)
    private String guestEmail;

    @Size(max = 20, message = "Guest phone must be less than or equal to 20 characters")
    @Column(name = "guest_phone", length = 20)
    private String guestPhone;

    @NotNull(message = "Check-in date is required")
    @Column(name = "check_in_date", nullable = false)
    private LocalDate checkInDate;

    @NotNull(message = "Check-out date is required")
    @Column(name = "check_out_date", nullable = false)
    private LocalDate checkOutDate;

    // Many reservations belong to one room
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @OneToMany(mappedBy = "reservationId", cascade = CascadeType.ALL, orphanRemoval = true)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private List<Payment> payments;

    @OneToOne(mappedBy = "reservation", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Review review;

    // Constructors
    public Reservation() {
    }

    public Reservation(String guestName, String guestEmail, String guestPhone, LocalDate checkInDate,
                       LocalDate checkOutDate) {
        this.guestName = guestName;
        this.guestEmail = guestEmail;
        this.guestPhone = guestPhone;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    // Getters and Setters
    public Integer getReservationId() {
        return reservationId;
    }

    public void setReservationId(Integer reservationId) {
        this.reservationId = reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getGuestEmail() {
        return guestEmail;
    }

    public void setGuestEmail(String guestEmail) {
        this.guestEmail = guestEmail;
    }

    public String getGuestPhone() {
        return guestPhone;
    }

    public void setGuestPhone(String guestPhone) {
        this.guestPhone = guestPhone;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    @Override
    public String toString() {
        return "Reservation [reservationId=" + reservationId + ", guestName=" + guestName + ", guestEmail=" + guestEmail
                + ", guestPhone=" + guestPhone + ", checkInDate=" + checkInDate + ", checkOutDate=" + checkOutDate
                + ", room=" + room + "]";
    }
}