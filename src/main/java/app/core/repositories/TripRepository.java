package app.core.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import app.core.entities.Trip;

public interface TripRepository extends JpaRepository<Trip, Integer> {

	boolean existsByNameAndUsersId(String name, int userId);

	boolean existsByIdAndUsersId(int tripId, int userId);

	Optional<Trip> findByIdAndUsersId(int tripId, int userId);

	List<Trip> findAllTripsByUsersId(int userId);

}
