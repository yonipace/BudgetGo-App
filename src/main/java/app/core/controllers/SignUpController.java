package app.core.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import app.core.auth.Auth;
import app.core.auth.JwtUtil;
import app.core.auth.JwtUtil.ClientType;
import app.core.entities.User;
import app.core.services.AdminService;

@CrossOrigin
@RestController
@RequestMapping("/signup")
public class SignUpController {

	@Autowired
	private AdminService adminService;
	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping
	public Auth signUp(@RequestBody User newUser) {

		User user = addNewUser(newUser);
		String email = user.getEmail();
		String firstName = user.getFirstName();
		String lastName = user.getLastName();
		ClientType client = ClientType.USER;
		String token = jwtUtil.generateToken(user.getId(), email, client);

		return new Auth(email, client, token, firstName, lastName);

	}

	private User addNewUser(User user) {

		try {
			return adminService.addUser(user);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

}
