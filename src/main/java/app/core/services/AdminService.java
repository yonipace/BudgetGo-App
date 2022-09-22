package app.core.services;

import app.core.entities.User;
import app.core.exceptions.TravelBudgetException;
import app.core.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

	@Autowired
	protected UserRepository userRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public User addUser(User user) throws TravelBudgetException {

		if (userRepo.existsById(user.getId())) {
			throw new TravelBudgetException("failed to add user: user " + user.getId() + " already exists");
		}
		if (userRepo.existsByEmail(user.getEmail())) {
			throw new TravelBudgetException("failed to add user: user " + user.getEmail() + " already exists");
		}

		user.setPassword(passwordEncoder.encode(user.getPassword()));

		return userRepo.save(user);
	}

	public User updateUser(User user) throws TravelBudgetException {

		Optional<User> opt = userRepo.findById(user.getId());
		if (opt.isEmpty()) {
			throw new TravelBudgetException("failed to update user: user " + user.getId() + " does not exist");
		}
		// user email cannot be updated
		user.setEmail(opt.get().getEmail());

		return userRepo.save(user);
	}

	public void deleteUser(int userId) throws TravelBudgetException {

		if (!userRepo.existsById(userId)) {
			throw new TravelBudgetException("failed to delete user: user " + userId + " does not exist");
		}
		userRepo.deleteById(userId);
	}

	public User getOneUser(int userId) throws TravelBudgetException {

		Optional<User> opt = userRepo.findById(userId);
		if (opt.isEmpty()) {
			throw new TravelBudgetException("failed to update user: user " + userId + " does not exist");
		}
		return opt.get();
	}

	public List<User> getAllUsers() {

		List<User> users = userRepo.findAll();
		if (users.isEmpty()) {
			System.out.println("no users found");
		}
		return users;
	}

	public void updateExchangeRates() {
		// TODO Auto-generated method stub
	}

	public void getStatistics() {
		// TODO Auto-generated method stub
	}

}
