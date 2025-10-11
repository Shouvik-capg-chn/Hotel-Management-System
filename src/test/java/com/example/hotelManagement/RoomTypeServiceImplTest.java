package com.example.hotelManagement;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.hotelManagement.model.RoomType;
import com.example.hotelManagement.repository.RoomTypeRepository;
import com.example.hotelManagement.serviceImpl.RoomTypeServiceImpl;

class RoomTypeServiceImplTest {

    @Mock
    private RoomTypeRepository roomTypeRepository;

    @InjectMocks
    private RoomTypeServiceImpl roomTypeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddRoomType_Success() {
        RoomType roomType = new RoomType();
        roomType.setTypeName("Deluxe");

        when(roomTypeRepository.existsByTypeNameIgnoreCase("Deluxe")).thenReturn(false);

        boolean result = roomTypeService.addRoomType(roomType);

        assertTrue(result);
        verify(roomTypeRepository).save(roomType);
    }

    @Test
    void testAddRoomType_Duplicate() {
        RoomType roomType = new RoomType();
        roomType.setTypeName("Deluxe");

        when(roomTypeRepository.existsByTypeNameIgnoreCase("Deluxe")).thenReturn(true);

        boolean result = roomTypeService.addRoomType(roomType);

        assertFalse(result);
        verify(roomTypeRepository, never()).save(any());
    }

    @Test
    void testAddRoomType_InvalidPayload() {
        RoomType roomType = new RoomType();
        roomType.setTypeName("  "); // blank

        boolean result = roomTypeService.addRoomType(roomType);

        assertFalse(result);
        verify(roomTypeRepository, never()).save(any());
    }

    @Test
    void testUpdateRoomType_Success() {
        RoomType existing = new RoomType();
        existing.setTypeName("Deluxe");

        RoomType updated = new RoomType();
        updated.setTypeName("Premium");
        updated.setDescription("Updated description");
        updated.setMax_occupancy(4);
        updated.setPricePerNight(new BigDecimal("5000"));

        when(roomTypeRepository.findById(1)).thenReturn(Optional.of(existing));
        when(roomTypeRepository.save(any())).thenReturn(existing);

        boolean result = roomTypeService.updateRoomType(1, updated);

        assertTrue(result);
        verify(roomTypeRepository).save(existing);
    }

    @Test
    void testUpdateRoomType_NotFound() {
        RoomType updated = new RoomType();
        updated.setTypeName("Premium");

        when(roomTypeRepository.findById(1)).thenReturn(Optional.empty());

        boolean result = roomTypeService.updateRoomType(1, updated);

        assertFalse(result);
        verify(roomTypeRepository, never()).save(any());
    }

    @Test
    void testDeleteRoomType_Success() {
        when(roomTypeRepository.existsById(1)).thenReturn(true);

        boolean result = roomTypeService.deleteRoomType(1);

        assertTrue(result);
        verify(roomTypeRepository).deleteById(1);
    }

    @Test
    void testDeleteRoomType_NotFound() {
        when(roomTypeRepository.existsById(1)).thenReturn(false);

        boolean result = roomTypeService.deleteRoomType(1);

        assertFalse(result);
        verify(roomTypeRepository, never()).deleteById(any());
    }

    @Test
    void testGetRoomTypeById_Found() {
        RoomType roomType = new RoomType();
        roomType.setTypeName("Deluxe");

        when(roomTypeRepository.findById(1)).thenReturn(Optional.of(roomType));

        RoomType result = roomTypeService.getRoomTypeById(1);

        assertNotNull(result);
        assertEquals("Deluxe", result.getTypeName());
    }

    @Test
    void testGetRoomTypeById_NotFound() {
        when(roomTypeRepository.findById(1)).thenReturn(Optional.empty());

        RoomType result = roomTypeService.getRoomTypeById(1);

        assertNull(result);
    }
}