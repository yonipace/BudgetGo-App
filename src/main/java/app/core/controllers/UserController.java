package app.core.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import app.core.auth.JwtUtil;
import app.core.entities.Trip;
import app.core.exceptions.TravelBudgetException;
import app.core.services.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping
	public Trip addTrip(@RequestBody Trip trip, @RequestHeader String token) throws TravelBudgetException {

		try {
			return userService.addTrip(trip, jwtUtil.extractId(token));
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PutMapping
	public void updateTrip(@RequestBody Trip trip, @RequestHeader String token) throws TravelBudgetException {
		try {
			userService.updateTrip(trip, jwtUtil.extractId(token));
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@DeleteMapping
	public void deleteTrip(@RequestParam int tripId, @RequestHeader String token) throws TravelBudgetException {
		try {
			userService.deleteTrip(tripId, jwtUtil.extractId(token));
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/get-trip")
	public Trip getOneTrip(@RequestParam int tripId, @RequestHeader String token) throws TravelBudgetException {
		try {
			return userService.getOneTrip(tripId, jwtUtil.extractId(token));
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping
	public List<Trip> getAllTrips(@RequestHeader String token) {

		try {
			return userService.getAllTrips(jwtUtil.extractId(token));
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

}
