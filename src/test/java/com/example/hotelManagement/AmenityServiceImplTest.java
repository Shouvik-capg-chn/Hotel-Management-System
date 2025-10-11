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

import com.example.hotelManagement.model.Amenity;
import com.example.hotelManagement.repository.AmenityRepository;
import com.example.hotelManagement.serviceImpl.AmenityServiceImpl;

class AmenityServiceImplTest {

    @Mock
    private AmenityRepository amenityRepository;

    @InjectMocks
    private AmenityServiceImpl amenityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddAmenity_Success() {
        Amenity amenity = new Amenity();
        amenity.setName("WiFi");

        when(amenityRepository.findByName("WiFi")).thenReturn(Optional.empty());
        when(amenityRepository.save(amenity)).thenReturn(amenity);

        Amenity result = amenityService.addAmenity(amenity);

        assertNotNull(result);
        assertEquals("WiFi", result.getName());
    }

    @Test
    void testAddAmenity_AlreadyExists() {
        Amenity amenity = new Amenity();
        amenity.setName("WiFi");

        when(amenityRepository.findByName("WiFi")).thenReturn(Optional.of(amenity));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            amenityService.addAmenity(amenity);
        });

        assertEquals("Amenity already exists", exception.getMessage());
    }

    @Test
    void testGetAllAmenities_Success() {
        Amenity a1 = new Amenity(); a1.setName("WiFi");
        Amenity a2 = new Amenity(); a2.setName("Pool");

        when(amenityRepository.findAll()).thenReturn(Arrays.asList(a1, a2));

        List<Amenity> result = amenityService.getAllAmenities();

        assertEquals(2, result.size());
    }

    @Test
    void testGetAllAmenities_EmptyList() {
        when(amenityRepository.findAll()).thenReturn(List.of());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            amenityService.getAllAmenities();
        });

        assertEquals("Amenity list is empty", exception.getMessage());
    }

    @Test
    void testGetAmenityById_Found() {
        Amenity amenity = new Amenity(); amenity.setName("WiFi");

        when(amenityRepository.findById(1)).thenReturn(Optional.of(amenity));

        Amenity result = amenityService.getAmenityById(1);

        assertNotNull(result);
        assertEquals("WiFi", result.getName());
    }

    @Test
    void testGetAmenityById_NotFound() {
        when(amenityRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            amenityService.getAmenityById(1);
        });

        assertEquals("Amenity doesn't exist", exception.getMessage());
    }

    @Test
    void testUpdateAmenity_Success() {
        Amenity existing = new Amenity(); existing.setName("WiFi");
        Amenity updated = new Amenity(); updated.setName("High-Speed WiFi");

        when(amenityRepository.findById(1)).thenReturn(Optional.of(existing));
        when(amenityRepository.save(any())).thenReturn(updated);

        Amenity result = amenityService.updateAmenity(1, updated);

        assertEquals("High-Speed WiFi", result.getName());
    }

    @Test
    void testDeleteAmenity_Success() {
        Amenity amenity = new Amenity(); amenity.setName("WiFi");

        when(amenityRepository.findById(1)).thenReturn(Optional.of(amenity));

        amenityService.deleteAmenity(1);

        verify(amenityRepository).delete(amenity);
    }
}