package app.core.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import app.core.entities.Trip;

public interface TripRepository extends JpaRepository<Trip, Integer> {

	boolean existsByNameAndUsersId(String name, int userId);

	List<Trip> findAllTripsByUsersId(int userId);

}
