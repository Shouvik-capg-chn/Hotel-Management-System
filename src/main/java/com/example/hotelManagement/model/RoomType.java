package com.example.hotelManagement.model;

import java.math.BigDecimal;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "RoomType")
public class RoomType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_type_id")
    private Integer roomTypeId;

    @NotBlank(message = "Room type name is required")
    @Size(max = 255, message = "Room type name must be less than or equal to 255 characters")
    @Column(name = "type_name", nullable = false, length = 255)
    private String typeName;

    @Size(max = 10000, message = "Description is too long")
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @NotNull(message = "Max occupancy is required")
    @Min(value = 1, message = "Max occupancy must be at least 1")
    @Column(name = "max_occupancy")
    private Integer maxOccupancy;

    @NotNull(message = "Price per night is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price per night must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Price per night must be a valid monetary amount")
    @Column(name = "price_per_night", precision = 10, scale = 2)
    private BigDecimal pricePerNight;

    public RoomType() {
    }

    public RoomType(Integer roomTypeId, String typeName, String description, Integer max_occupancy,
                    BigDecimal pricePerNight) {
        super();
        this.roomTypeId = roomTypeId;
        this.typeName = typeName;
        this.description = description;
        this.maxOccupancy = max_occupancy;
        this.pricePerNight = pricePerNight;
    }

    public Integer getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(Integer roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getMax_occupancy() {
        return maxOccupancy;
    }

    public void setMax_occupancy(Integer max_occupancy) {
        this.maxOccupancy = max_occupancy;
    }

    public BigDecimal getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(BigDecimal pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    @Override
    public String toString() {
        return "RoomType [roomTypeId=" + roomTypeId + ", typeName=" + typeName + ", description=" + description
                + ", max_occupancy=" + maxOccupancy + ", pricePerNight=" + pricePerNight + "]";
    }
}