package com.example.hotelManagement;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.List;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.hotelManagement.model.ApiResponse;
import com.example.hotelManagement.model.Hotel;
import com.example.hotelManagement.repository.HotelRepository;
import com.example.hotelManagement.serviceImpl.HotelServiceImpl;

class HotelServiceImplTest {

    @Mock
    private HotelRepository hotelRepository;

    @InjectMocks
    private HotelServiceImpl hotelService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddHotel_HotelAlreadyExists() {
        Hotel hotel = new Hotel();
        hotel.setName("Taj");
        hotel.setLocation("Chennai");

        when(hotelRepository.findByNameAndLocation("Taj", "Chennai"))
            .thenReturn(Optional.of(hotel));

        ApiResponse response = hotelService.addHotel(hotel);

        assertEquals("ADDFAILS", response.getCode());
        assertEquals("Hotel already exists", response.getMessage());
    }

    @Test
    void testAddHotel_Success() {
        Hotel hotel = new Hotel();
        hotel.setName("Taj");
        hotel.setLocation("Chennai");

        when(hotelRepository.findByNameAndLocation("Taj", "Chennai"))
            .thenReturn(Optional.empty());

        ApiResponse response = hotelService.addHotel(hotel);

        assertEquals("POSTSUCCESS", response.getCode());
        assertEquals("Hotel added successfully", response.getMessage());
        verify(hotelRepository).save(hotel);
    }

    @Test
    void testGetAllHotels() {
        Hotel h1 = new Hotel(); h1.setName("Taj");
        Hotel h2 = new Hotel(); h2.setName("Oberoi");

        when(hotelRepository.findAll()).thenReturn(Arrays.asList(h1, h2));

        List<Hotel> hotels = hotelService.getAllHotels();

        assertEquals(2, hotels.size());
    }

    @Test
    void testGetHotelById_Found() {
        Hotel hotel = new Hotel(); hotel.setName("Taj");

        when(hotelRepository.findById(1)).thenReturn(Optional.of(hotel));

        Hotel result = hotelService.getHotelById(1);

        assertNotNull(result);
        assertEquals("Taj", result.getName());
    }

    @Test
    void testGetHotelById_NotFound() {
        when(hotelRepository.findById(1)).thenReturn(Optional.empty());

        Hotel result = hotelService.getHotelById(1);

        assertNull(result);
    }

    @Test
    void testDeleteHotel_Success() {
        when(hotelRepository.existsById(1)).thenReturn(true);

        ApiResponse response = hotelService.deleteHotel(1);

        assertEquals("DELETESUCCESS", response.getCode());
        verify(hotelRepository).deleteById(1);
    }

    @Test
    void testDeleteHotel_NotFound() {
        when(hotelRepository.existsById(1)).thenReturn(false);

        ApiResponse response = hotelService.deleteHotel(1);

        assertEquals("DELETEFAILS", response.getCode());
    }
}
