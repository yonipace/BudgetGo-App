package app.core.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import app.core.auth.JwtUtil.ClientType;
import app.core.exceptions.TravelBudgetException;
import app.core.services.AdminService;
import app.core.services.UserService;

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

		try {
			if (email.equals(adminEmail) && password.equals(adminPassword)) {

				return jwtUtil.generateToken(0, email, ClientType.ADMIN);
			}

			int userId = userService.login(email, password);

			if (userId == -1) {
				throw new TravelBudgetException("Login failed - email or password are incorrect");
			}
			return jwtUtil.generateToken(userId, email, ClientType.USER);

		} catch (Exception e) {
			throw new TravelBudgetException("login failed");
		}
	}

	public ClientType getClient(String email, String password) {

		if (email.equals(adminEmail) && password.equals(adminPassword)) {
			return ClientType.ADMIN;
		} else {
			return ClientType.USER;
		}

	}

}
