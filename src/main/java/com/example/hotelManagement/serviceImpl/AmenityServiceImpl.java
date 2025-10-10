package com.example.hotelManagement.serviceImpl;

import com.example.hotelManagement.model.Amenity;
import com.example.hotelManagement.repository.AmenityRepository;
import com.example.hotelManagement.service.AmenityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AmenityServiceImpl implements AmenityService {

    @Autowired
    private AmenityRepository amenityRepository;

    @Override
    public Amenity addAmenity(Amenity amenity) {
        Optional<Amenity> existing = amenityRepository.findByName(amenity.getName());
        if (existing.isPresent()) {
            throw new RuntimeException("Amenity already exists");
        }
        return amenityRepository.save(amenity);
    }

    @Override
    public List<Amenity> getAllAmenities() {
        List<Amenity> amenities = amenityRepository.findAll();
        if (amenities.isEmpty()) {
            throw new RuntimeException("Amenity list is empty");
        }
        return amenities;
    }

    @Override
    public Amenity getAmenityById(Integer id) {
        return amenityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Amenity doesn't exist"));
    }

    @Override
    public Amenity updateAmenity(Integer id, Amenity updatedAmenity) {
        Amenity existing = getAmenityById(id);
        existing.setName(updatedAmenity.getName());
        existing.setDescription(updatedAmenity.getDescription());
        return amenityRepository.save(existing);
    }

    @Override
    public void deleteAmenity(Integer id) {
        Amenity existing = getAmenityById(id);
        amenityRepository.delete(existing);
    }
}