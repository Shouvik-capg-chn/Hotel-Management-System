package com.example.hotelManagement.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Integer roomId;

    @NotNull(message = "Room number is required")
    @Min(value = 1, message = "Room number must be a positive integer")
    @Column(name = "room_number", nullable = false)
    private Integer roomNumber;

    // Many rooms belong to one hotel
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    // Many rooms belong to one room type
    @ManyToOne
    @JoinColumn(name = "room_type_id")
    private RoomType roomType;

    // One room can have many reservations
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations = new ArrayList<>();

    // Many-to-Many with Amenity
    @ManyToMany
    @JoinTable(
        name = "roomamenity",
        joinColumns = @JoinColumn(name = "room_id"),
        inverseJoinColumns = @JoinColumn(name = "amenity_id")
    )
    private Set<Amenity> amenities = new HashSet<>();

    @NotNull(message = "Availability status is required")
    @Column(name = "is_available")
    private Boolean isAvailable;

    @Size(max = 10000, message = "Description is too long")
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Size(max = 255, message = "Location must be less than or equal to 255 characters")
    @Column(name = "location", length = 255)
    private String location;

    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price must be zero or a positive value")
    @Column(name = "price")
    private Integer price;

    // Constructors
    public Room() {
    }

    public Room(Integer roomNumber, RoomType roomType, Boolean isAvailable) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.isAvailable = isAvailable;
    }

    // Getters and Setters
    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getPrice() {
        return this.price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Room [roomId=" + roomId + ", roomNumber=" + roomNumber + ", roomType=" + roomType + ", isAvailable="
                + isAvailable + ", description=" + description + ", location=" + location + ", price=" + price + "]";
    }
}