package app.core.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import app.core.auth.JwtUtil.ClientType;
import app.core.entities.User;
import app.core.exceptions.TravelBudgetException;
import app.core.repositories.UserRepository;

@Component
public class LoginManager {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Value("${admin.email}")
	private String adminEmail;
	@Value("${admin.password}")
	private String adminPassword;

	public String login(String email, String password) throws TravelBudgetException {

		Auth auth = new Auth();

		if (email.equals(adminEmail) && password.equals(adminPassword)) {

			return jwtUtil.generateToken(0, email, "Admin", "", ClientType.ADMIN);

		}

		User user = userRepo.findByEmail(email).orElseThrow(() -> new TravelBudgetException(email + " does not exist"));

		if (passwordEncoder.matches(password, user.getPassword())) {

			return jwtUtil.generateToken(user.getId(), email, user.getFirstName(), user.getLastName(), ClientType.USER);

		}

		throw new TravelBudgetException("Login failed - email or password are incorrect");

	}

}
