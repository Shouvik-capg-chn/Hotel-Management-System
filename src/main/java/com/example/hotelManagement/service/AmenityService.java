package com.example.hotelManagement.service;

import com.example.hotelManagement.model.Amenity;
import java.util.List;

public interface AmenityService
{
    Amenity addAmenity(Amenity amenity);
    List<Amenity> getAllAmenities();
    Amenity getAmenityById(Integer id);
    Amenity updateAmenity(Integer id, Amenity amenity);
    void deleteAmenity(Integer id);
}
