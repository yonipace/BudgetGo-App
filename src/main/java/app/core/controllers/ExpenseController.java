package app.core.controllers;

import app.core.auth.JwtUtil;
import app.core.entities.Expense;
import app.core.exceptions.TravelBudgetException;
import app.core.services.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/expense")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    public Expense addExpense(@RequestBody Expense expense, @RequestParam int tripId, @RequestHeader String token) {

        try {
            return expenseService.addExpense(expense, tripId, jwtUtil.extractId(token));
        } catch (TravelBudgetException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping
    public Expense updateExpense(@RequestBody Expense expense, @RequestParam int tripId, @RequestHeader String token) {

        try {
            return expenseService.updateExpense(expense, tripId, jwtUtil.extractId(token));
        } catch (TravelBudgetException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping
    public void deleteExpense(@RequestParam int id, @RequestHeader String token) {

        try {
            expenseService.deleteExpense(id, jwtUtil.extractId(token));
        } catch (TravelBudgetException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("get-one")
    public Expense getOneExpense(@RequestParam int expenseId, @RequestHeader String token) {

        try {
            return expenseService.getOneExpense(expenseId, jwtUtil.extractId(token));
        } catch (TravelBudgetException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping
    public List<Expense> getallExpenses(@RequestParam int tripId, @RequestHeader String token) {
        try {
            List<Expense> expenses = expenseService.getAllExpenses(tripId, jwtUtil.extractId(token));
            return expenses;
        } catch (TravelBudgetException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }


}
