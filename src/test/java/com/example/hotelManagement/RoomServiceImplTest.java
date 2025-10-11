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

import com.example.hotelManagement.model.Room;
import com.example.hotelManagement.repository.RoomRepository;
import com.example.hotelManagement.serviceImpl.RoomServiceImpl;

class RoomServiceImplTest {

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomServiceImpl roomService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateRoom_DuplicateRoomNumber() {
        Room room = new Room();
        room.setRoomNumber(101);

        when(roomRepository.existsByRoomNumber(101)).thenReturn(true);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            roomService.createRoom(room);
        });

        assertEquals("DUPLICATE_ROOM_NUMBER", exception.getMessage());
    }

    @Test
    void testCreateRoom_Success() {
        Room room = new Room();
        room.setRoomNumber(102);

        when(roomRepository.existsByRoomNumber(102)).thenReturn(false);

        roomService.createRoom(room);

        verify(roomRepository).save(room);
    }

    @Test
    void testGetAllRooms() {
        Room r1 = new Room(); r1.setRoomNumber(101);
        Room r2 = new Room(); r2.setRoomNumber(102);

        when(roomRepository.findAll()).thenReturn(Arrays.asList(r1, r2));

        List<Room> rooms = roomService.getAllRooms();

        assertEquals(2, rooms.size());
    }

    @Test
    void testGetRoomById_Found() {
        Room room = new Room(); room.setRoomNumber(101);

        when(roomRepository.findById(1)).thenReturn(Optional.of(room));

        Optional<Room> result = roomService.getRoomById(1);

        assertTrue(result.isPresent());
        assertEquals(101, result.get().getRoomNumber());
    }

    @Test
    void testGetRoomById_NotFound() {
        when(roomRepository.findById(1)).thenReturn(Optional.empty());

        Optional<Room> result = roomService.getRoomById(1);

        assertFalse(result.isPresent());
    }

    @Test
    void testGetAvailableRoomsByType() {
        Room r1 = new Room(); r1.setRoomNumber(101);

        when(roomRepository.findByRoomType_RoomTypeIdAndIsAvailableTrue(1)).thenReturn(List.of(r1));

        List<Room> result = roomService.getAvailableRoomsByType(1);

        assertEquals(1, result.size());
    }

    @Test
    void testGetRoomsByLocation() {
        Room r1 = new Room(); r1.setLocation("Chennai");

        when(roomRepository.findByLocationIgnoreCase("Chennai")).thenReturn(List.of(r1));

        List<Room> result = roomService.getRoomsByLocation("Chennai");

        assertEquals(1, result.size());
    }

    @Test
    void testGetRoomsByAmenity() {
        Room r1 = new Room(); r1.setRoomNumber(101);

        when(roomRepository.findByAmenityId(1)).thenReturn(List.of(r1));

        List<Room> result = roomService.getRoomsByAmenity(1);

        assertEquals(1, result.size());
    }

    @Test
    void testUpdateRoom_Success() {
        Room existing = new Room(); existing.setRoomNumber(101);
        Room updated = new Room(); updated.setRoomNumber(102);

        when(roomRepository.findById(1)).thenReturn(Optional.of(existing));
        when(roomRepository.existsByRoomNumber(102)).thenReturn(false);
        when(roomRepository.save(any())).thenReturn(updated);

        Room result = roomService.updateRoom(1, updated);

        assertEquals(102, result.getRoomNumber());
    }

    @Test
    void testUpdateRoom_NotFound() {
        Room updated = new Room(); updated.setRoomNumber(102);

        when(roomRepository.findById(1)).thenReturn(Optional.empty());

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            roomService.updateRoom(1, updated);
        });

        assertEquals("ROOM_NOT_FOUND", exception.getMessage());
    }

    @Test
    void testUpdateRoom_DuplicateRoomNumber() {
        Room existing = new Room(); existing.setRoomNumber(101);
        Room updated = new Room(); updated.setRoomNumber(102);

        when(roomRepository.findById(1)).thenReturn(Optional.of(existing));
        when(roomRepository.existsByRoomNumber(102)).thenReturn(true);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            roomService.updateRoom(1, updated);
        });

        assertEquals("DUPLICATE_ROOM_NUMBER", exception.getMessage());
    }

    @Test
    void testDeleteRoom_Success() {
        when(roomRepository.existsById(1)).thenReturn(true);

        roomService.deleteRoom(1);

        verify(roomRepository).deleteById(1);
    }

    @Test
    void testDeleteRoom_NotFound() {
        when(roomRepository.existsById(1)).thenReturn(false);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            roomService.deleteRoom(1);
        });

        assertEquals("ROOM_NOT_FOUND", exception.getMessage());
    }
}