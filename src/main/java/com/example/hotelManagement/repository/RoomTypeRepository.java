package com.example.hotelManagement.repository;

import com.example.hotelManagement.model.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Spring Data JPA repository for RoomType.
 * Provides CRUD operations and a couple of helpful finder methods.
 */
public interface RoomTypeRepository extends JpaRepository<RoomType, Integer> {

    // Used to quickly check duplicates on create (case-insensitive by typeName)
    boolean existsByTypeNameIgnoreCase(String typeName);

    // Optional finder if you ever need to fetch by type name
    Optional<RoomType> findByTypeNameIgnoreCase(String typeName);
}
















/*package com.example.hotelManagement.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.hotelManagement.model.RoomType;

	@Repository
	public interface RoomTypeRepository extends JpaRepository<RoomType, Integer>
	{

	    // Find RoomType by name
	    Optional<RoomType> findByName(String name);

	    // You can add more custom queries if needed
	    // Example: List<RoomType> findByCategory(String category);
	}
*/

