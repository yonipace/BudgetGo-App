package app.core.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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
	public String signUp(@RequestBody User newUser) {

		User user = addNewUser(newUser);

		return jwtUtil.generateToken(user.getId(), user.getEmail(), user.getFirstName(), user.getLastName(),
				ClientType.USER);

	}

	private User addNewUser(User user) {

		try {
			return adminService.addUser(user);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

}
