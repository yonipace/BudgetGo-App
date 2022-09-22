package app.core.services;

import app.core.entities.Trip;
import app.core.entities.User;
import app.core.exceptions.NotFoundException;
import app.core.exceptions.TravelBudgetException;
import app.core.repositories.ExchangeRateRepository;
import app.core.repositories.ExpenseRepository;
import app.core.repositories.TripRepository;
import app.core.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TripService {

    @Autowired
    protected UserRepository userRepo;
    @Autowired
    protected TripRepository tripRepo;
    @Autowired
    protected ExpenseRepository expenseRepo;
    @Autowired
    protected ExchangeRateRepository exchangeRateRepo;

    public int login(String email, String password) throws TravelBudgetException {

        Optional<User> opt = userRepo.findByEmailAndPassword(email, password);
        return opt.isPresent() ? opt.get().getId() : -1;
    }

    public Trip addTrip(Trip trip, int userId) throws TravelBudgetException {

        if (tripRepo.existsById(trip.getId())) {
            throw new TravelBudgetException("Failed to add trip - trip already exists");
        }

        trip.addUser(getUserDetails(userId));

        return tripRepo.save(trip);

    }

    public Trip updateTrip(Trip trip, int userId) throws TravelBudgetException {

        if (tripRepo.existsByIdAndUsersId(trip.getId(), userId)) {

            return tripRepo.save(trip);
        } else {
            throw new TravelBudgetException("Failed to update - trip does not exist");
        }
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

    public Trip addUserToTrip(int tripId, int userId, String userEmail) throws TravelBudgetException {

        Trip trip = tripRepo.findByIdAndUsersId(tripId, userId)
                .orElseThrow(() -> new TravelBudgetException("trip does not exist"));

        User newUser = userRepo.findByEmail(userEmail)
                .orElseThrow(() -> new TravelBudgetException("could not find " + userEmail));

        trip.addUser(newUser);

        return tripRepo.save(trip);

    }

    public User getUserDetails(int id) throws TravelBudgetException {
        return userRepo.findById(id).orElseThrow(() -> new TravelBudgetException("Failed to get user " + id));
    }

}
