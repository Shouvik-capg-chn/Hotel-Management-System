package com.example.hotelManagement.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="Hotel")
public class Hotel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "hotel_id")
	private Integer hotel_id;

	@Column(name = "name", nullable = false, length = 255)
	private String name;

	@Column(name = "location", length = 255)
	private String location;

	@Column(name = "description", columnDefinition = "TEXT")
	private String description;
	
	 // One hotel has many rooms
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Room> rooms = new ArrayList<>();

    // Many-to-Many with Amenity
    @ManyToMany
    @JoinTable(
        name = "hotelamenity",
        joinColumns = @JoinColumn(name = "hotel_id"),
        inverseJoinColumns = @JoinColumn(name = "amenity_id")
    )
    private Set<Amenity> amenities = new HashSet<>();


	public Hotel() {

	}

	public Hotel(Integer hotel_id, String name, String location, String description) {
		super();
		this.hotel_id = hotel_id;
		this.name = name;
		this.location = location;
		this.description = description;
	}

	public Integer getHotel_id() {
		return hotel_id;
	}

	public void setHotel_id(Integer hotel_id) {
		this.hotel_id = hotel_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Hotel [hotel_id=" + hotel_id + ", name=" + name + ", location=" + location + ", description="
				+ description + "]";
	}

}
