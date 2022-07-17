package app.core.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import app.core.auth.JwtUtil.ClientType;
import app.core.exceptions.TravelBudgetException;
import app.core.services.AdminService;
import app.core.services.UserService;

@Component
public class LoginManager {

	@Autowired
	AdminService adminService;
	@Autowired
	UserService userService;
	@Autowired
	JwtUtil jwtUtil;

	@Value("${admin.email}")
	private String adminEmail;
	@Value("${admin.password}")
	private String adminPassword;

	public String login(String email, String password) throws TravelBudgetException {

		if (email.equals(adminEmail) && password.equals(adminPassword)) {

			return jwtUtil.generateToken(0, email, ClientType.ADMIN);
		}

		int userId = userService.login(email, password);

		if (userId == -1) {
			throw new TravelBudgetException("Login failed - email or password are incorrect");
		}
		String token = jwtUtil.generateToken(userId, email, ClientType.USER);

		return token;

	}

	public ClientType getClient(String email, String password) {

		if (email.equals(adminEmail) && password.equals(adminPassword)) {
			return ClientType.ADMIN;
		} else {
			return ClientType.USER;
		}

	}

	public Auth getAuth(String email, String password) throws TravelBudgetException {

		String token;
		ClientType client;
		String firstName;
		String lastName;

		if (email.equals(adminEmail) && password.equals(adminPassword)) {

			token = jwtUtil.generateToken(0, email, ClientType.ADMIN);
			client = ClientType.ADMIN;
			firstName = "Admin";
			lastName = "";

		} else {

			int userId = userService.login(email, password);

			if (userId == -1) {
				throw new TravelBudgetException("Login failed - email or password are incorrect");
			}
			token = jwtUtil.generateToken(userId, email, ClientType.USER);
			client = ClientType.USER;
			firstName = userService.getDetails(userId).getFirstName();
			lastName = userService.getDetails(userId).getLastName();

		}
		return new Auth(email, client, token, firstName, lastName);

	}
}
