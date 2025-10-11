package com.example.hotelManagement.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hotelManagement.model.ApiResponse;
import com.example.hotelManagement.model.Hotel;
import com.example.hotelManagement.repository.HotelRepository;
import com.example.hotelManagement.service.HotelService;

@Service
public class HotelServiceImpl implements HotelService {

	@Autowired
	private HotelRepository hotelRepository;

	@Override
	public ApiResponse addHotel(Hotel hotel) {
		boolean exists = hotelRepository.findByNameAndLocation(hotel.getName(), hotel.getLocation()).isPresent();
		if (exists) {
			return new ApiResponse("ADDFAILS", "Hotel already exists");
		}
		hotelRepository.save(hotel);
		return new ApiResponse("POSTSUCCESS", "Hotel added successfully");
	}

	@Override
	public List<Hotel> getAllHotels() {
		return hotelRepository.findAll();
	}

	@Override
	public Hotel getHotelById(Integer hotelId) {
		return hotelRepository.findById(hotelId).orElse(null);
	}

	@Override
	public List<Hotel> getHotelsByAmenityId(Integer amenityId) {
		return hotelRepository.findByAmenitiesAmenityId(amenityId);
	}

	@Override
	public ApiResponse updateHotel(Integer hotelId, Hotel updatedHotel) {
		return hotelRepository.findById(hotelId).map(hotel -> {
			hotel.setName(updatedHotel.getName());
			hotel.setLocation(updatedHotel.getLocation());
			hotel.setDescription(updatedHotel.getDescription());
			hotelRepository.save(hotel);
			return new ApiResponse("UPDATESUCCESS", "Hotel updated successfully");
		}).orElseGet(() -> new ApiResponse("UPDTFAILS", "Hotel doesn't exist"));
	}
	
	@Override
	public ApiResponse deleteHotel(Integer hotelId) {
	    if (!hotelRepository.existsById(hotelId)) {
	        return new ApiResponse("DELETEFAILS", "Hotel doesn't exist");
	    }
	    hotelRepository.deleteById(hotelId);
	    return new ApiResponse("DELETESUCCESS", "Hotel deleted successfully");
	}

}
