package app.core.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import app.core.entities.Expense;
import app.core.exceptions.TravelBudgetException;
import app.core.services.TripService;

@RestController
@RequestMapping("/trip")
public class TripController {

	@Autowired
	private TripService tripService;

	@PostMapping
	public Expense addExpense(@RequestBody Expense expense, @RequestParam int tripId) {

		try {
			return tripService.addExpense(expense, tripId);
		} catch (TravelBudgetException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PutMapping
	public void updateExpense(@RequestBody Expense expense, @RequestParam int tripId) {

		try {
			tripService.updateExpense(expense, tripId);
		} catch (TravelBudgetException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@DeleteMapping
	public void deleteExpense(@RequestParam int id) {

		try {
			tripService.deleteExpense(id);
		} catch (TravelBudgetException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/{id}")
	public Expense getOneExpense(@PathVariable int id) {

		try {
			return tripService.getOneExpense(id);
		} catch (TravelBudgetException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping
	public List<Expense> getallExpenses(int tripId) {
		try {
			return tripService.getAllExpenses(tripId);
		} catch (TravelBudgetException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

}
