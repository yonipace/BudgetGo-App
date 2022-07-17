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
import app.core.entities.Expense;
import app.core.exceptions.TravelBudgetException;
import app.core.services.TripService;

@CrossOrigin
@RestController
@RequestMapping("/trip")
public class TripController {

	@Autowired
	private TripService tripService;
	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping
	public Expense addExpense(@RequestBody Expense expense, @RequestParam int tripId, @RequestHeader String token) {

		try {
			return tripService.addExpense(expense, tripId, jwtUtil.extractId(token));
		} catch (TravelBudgetException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PutMapping
	public void updateExpense(@RequestBody Expense expense, @RequestParam int tripId, @RequestHeader String token) {

		try {
			tripService.updateExpense(expense, tripId, jwtUtil.extractId(token));
		} catch (TravelBudgetException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@DeleteMapping
	public void deleteExpense(@RequestParam int id, @RequestHeader String token) {

		try {
			tripService.deleteExpense(id, jwtUtil.extractId(token));
		} catch (TravelBudgetException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("get-one")
	public Expense getOneExpense(@RequestParam int expenseId, @RequestHeader String token) {

		try {
			return tripService.getOneExpense(expenseId, jwtUtil.extractId(token));
		} catch (TravelBudgetException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping
	public List<Expense> getallExpenses(@RequestParam int tripId, @RequestHeader String token) {
		try {
			return tripService.getAllExpenses(tripId, jwtUtil.extractId(token));
		} catch (TravelBudgetException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

}
