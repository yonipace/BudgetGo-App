package app.core.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import app.core.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	public boolean existsByEmail(String email);

	public boolean existsByEmailAndPassword(String email, String password);

	public Optional<User> findByEmailAndPassword(String email, String password);

	public Optional<User> findByEmail(String email);

	public List<User> findAllUsersByTripsId(int tripId);

}
