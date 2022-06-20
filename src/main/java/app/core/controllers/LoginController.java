package app.core.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.core.auth.Auth;
import app.core.auth.JwtUtil.ClientType;
import app.core.auth.LoginCredentials;
import app.core.auth.LoginManager;
import app.core.exceptions.TravelBudgetException;

@RestController
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private LoginManager loginManager;

	public Auth login(LoginCredentials credentials) throws TravelBudgetException {

		String email = credentials.getEmail();
		String password = credentials.getPassword();

		String token = loginManager.login(email, password);
		ClientType client = loginManager.getClient(email, password);

		return new Auth(email, client, token);

	}

}
