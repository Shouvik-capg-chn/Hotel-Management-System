package com.example.hotelManagement.model;

import java.time.LocalDate;
import java.util.ArrayList;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.AssertFalse.List;

@Entity
@Table(name = "Reservation")
public class Reservation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "reservation_id")
	private Integer reservationId;

	@Column(name = "guest_name", nullable = false, length = 255)
	private String guestName;

	@Column(name = "guest_email", length = 255)
	private String guestEmail;

	@Column(name = "guest_phone", length = 20)
	private String guestPhone;

	@Column(name = "check_in_date", nullable = false)
	private LocalDate checkInDate;

	@Column(name = "check_out_date", nullable = false)
	private LocalDate checkOutDate;
	
	
//	@Column(name = "room_id", nullable = false)
//	private Integer roomId;
	
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
	
	// Many reservations belong to one room
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    // One reservation has many payments
    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
    private ArrayList<Payment> payments;

    // One-to-One with Review
    @OneToOne(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
    private Review review;

	// Getters and Setters
	public Integer getReservationId() {
		return reservationId;
	}

	public ArrayList<Payment> getPayments() {
		return payments;
	}

	public void setPayments(ArrayList<Payment> payments) {
		this.payments = payments;
	}

	public Review getReview() {
		return review;
	}

	public void setReview(Review review) {
		this.review = review;
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

	@Override
	public String toString()
	{
		return "Reservation [reservationId=" + reservationId + ", guestName=" + guestName + ", guestEmail=" + guestEmail
				+ ", guestPhone=" + guestPhone + ", checkInDate=" + checkInDate + ", checkOutDate=" + checkOutDate
				+ ", room=" + room + "]";
	}
	
	
}