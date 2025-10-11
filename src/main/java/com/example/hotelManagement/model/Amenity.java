package com.example.hotelManagement.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "Amenity")
public class Amenity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "amenity_id")
    private Integer amenityId;

    @NotBlank(message = "Amenity name is required")
    @Size(max = 255, message = "Amenity name must be less than or equal to 255 characters")
    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Size(max = 10000, message = "Description is too long")
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    // Many-to-Many with Room
    @ManyToMany(mappedBy = "amenities")
    private List<Room> rooms = new ArrayList<>();

    // Many-to-Many with Hotel
    @ManyToMany(mappedBy = "amenities")
    private List<Hotel> hotels = new ArrayList<>();

    // Constructors
    public Amenity() {
    }

    public Amenity(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // Getters and Setters
    public Integer getAmenityId() {
        return amenityId;
    }

    public void setAmenityId(Integer amenityId) {
        this.amenityId = amenityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}