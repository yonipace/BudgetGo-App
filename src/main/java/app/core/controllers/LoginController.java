package app.core.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import app.core.auth.LoginCredentials;
import app.core.auth.LoginManager;
import app.core.exceptions.TravelBudgetException;

@CrossOrigin
@RestController
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private LoginManager loginManager;

	@PostMapping
	public String login(@RequestBody LoginCredentials credentials) throws TravelBudgetException {

		String email = credentials.getEmail();
		String password = credentials.getPassword();

		try {
			return loginManager.login(email, password);
		} catch (TravelBudgetException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
		}

	}

}
