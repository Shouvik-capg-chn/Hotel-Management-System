package com.example.hotelManagement.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.hotelManagement.model.ApiResponse;
import com.example.hotelManagement.model.Hotel;

@Service
public interface HotelService {
	ApiResponse addHotel(Hotel hotel);

	List<Hotel> getAllHotels();

	Hotel getHotelById(Integer hotelId);

	List<Hotel> getHotelsByAmenityId(Integer amenityId);

	ApiResponse updateHotel(Integer hotelId, Hotel updatedHotel);
}
