package app.core.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import app.core.entities.Trip;
import app.core.entities.User;
import app.core.exceptions.NotFoundException;
import app.core.exceptions.TravelBudgetException;

@Service
@Transactional
public class UserService extends ClientService {

	public int login(String email, String password) throws TravelBudgetException {

		Optional<User> opt = userRepo.findByEmailAndPassword(email, password);
		return opt.isPresent() ? opt.get().getId() : -1;
	}

	public Trip addTrip(Trip trip, int userId) throws TravelBudgetException {

		if (tripRepo.existsById(trip.getId())) {
			throw new TravelBudgetException("Failed to add trip - trip already exists");
		}

		trip.addUser(getDetails(userId));

		return tripRepo.save(trip);

	}

	public void updateTrip(Trip trip, int userId) throws TravelBudgetException {

		tripRepo.findByIdAndUsersId(trip.getId(), userId).ifPresentOrElse((t) -> tripRepo.save(trip),
				() -> new TravelBudgetException("Failed to update - trip does not exist"));

	}

	public Trip getOneTrip(int tripId, int userId) throws TravelBudgetException {

		return tripRepo.findByIdAndUsersId(tripId, userId)
				.orElseThrow(() -> new TravelBudgetException("Failed to get trip - trip " + tripId + " not found"));

	}

	public List<Trip> getAllTrips(int userId) {

		return tripRepo.findAllTripsByUsersId(userId);

	}

	public void deleteTrip(int tripId, int userId) throws NotFoundException {

		tripRepo.findByIdAndUsersId(tripId, userId).ifPresentOrElse((t) -> tripRepo.deleteById(t.getId()),
				() -> new TravelBudgetException("Failed to delete - trip does not exist"));

	}

	public User getDetails(int id) throws TravelBudgetException {
		return userRepo.findById(id).orElseThrow(() -> new TravelBudgetException("Failed to get user " + id));
	}

}
