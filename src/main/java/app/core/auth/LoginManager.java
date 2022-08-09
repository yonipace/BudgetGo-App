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

	public Auth login(String email, String password) throws TravelBudgetException {

		Auth auth = new Auth();

		if (email.equals(adminEmail) && password.equals(adminPassword)) {

			auth.setClient(ClientType.ADMIN);
			auth.setFirstName("Admin");
			auth.setToken(jwtUtil.generateToken(0, email, ClientType.ADMIN));

			return auth;

		}

		User user = userRepo.findByEmail(email).orElseThrow(() -> new TravelBudgetException(email + " does not exist"));

		if (passwordEncoder.matches(password, user.getPassword())) {

			auth.setClient(ClientType.USER);
			auth.setFirstName(user.getFirstName());
			auth.setLastName(user.getLastName());
			auth.setEmail(email);
			auth.setToken(jwtUtil.generateToken(user.getId(), email, ClientType.USER));

			return auth;
		}

		throw new TravelBudgetException("Login failed - email or password are incorrect");

	}

}
