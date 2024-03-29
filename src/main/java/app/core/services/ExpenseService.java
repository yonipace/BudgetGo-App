package app.core.services;

import app.core.api.currency.CurrencyConverter;
import app.core.entities.ExchangeRate;
import app.core.entities.ExchangeRate.Currency;
import app.core.entities.Expense;
import app.core.entities.Trip;
import app.core.entities.User;
import app.core.exceptions.TravelBudgetException;
import app.core.repositories.ExchangeRateRepository;
import app.core.repositories.ExpenseRepository;
import app.core.repositories.TripRepository;
import app.core.repositories.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ExpenseService {

    @Autowired
    protected UserRepository userRepo;
    @Autowired
    protected TripRepository tripRepo;
    @Autowired
    protected ExpenseRepository expenseRepo;
    @Autowired
    protected ExchangeRateRepository exchangeRateRepo;
    @Autowired
    private CurrencyConverter currencyConverter;

    private boolean validateTripUser(Trip trip, int userId) throws TravelBudgetException {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new TravelBudgetException("user " + userId + " not found"));

        return trip.getUsers().contains(user);
    }

    public Expense addExpense(Expense expense, int tripId, int userId) throws TravelBudgetException {

        Trip trip = getTripDetails(tripId);

        if (!validateTripUser(trip, userId)) {
            throw new TravelBudgetException("failed to execute method - trip does not belong to user");
        }

        if (expenseRepo.existsById(expense.getId())) {
            throw new TravelBudgetException("Failed to add expense - expense already exists");
        }
        // set trip
        expense.setTrip(trip);
        // set currency exchange
        expense.setAmount(calculateAmount(expense, tripId));
        // update total amount
        updateTotalAmount(trip);

        return expenseRepo.save(expense);

    }

    public Expense updateExpense(Expense expense, int tripId, int userId) throws TravelBudgetException {

        Trip trip = getTripDetails(tripId);

        if (!validateTripUser(trip, userId)) {
            throw new TravelBudgetException("failed to execute method - trip does not belong to user");
        }
        // set trip
        expense.setTrip(trip);

        // set currency exchange
        expense.setAmount(calculateAmount(expense, tripId));

        Expense updatedExpense = null;

        if (expenseRepo.existsById(expense.getId())) {
            updatedExpense = expenseRepo.save(expense);
        } else {
            throw new TravelBudgetException(
                    "Failed to update expense - expense " + expense.getId() + " does not exist");
        }
        // update total amount
        updateTotalAmount(trip);

        // after everything is updated - return the updated expense
        return updatedExpense;
    }

    public void deleteExpense(int expenseId, int userId) throws TravelBudgetException {

        Expense e = expenseRepo.findById(expenseId).orElseThrow(
                () -> new TravelBudgetException("Failed to delete expense - expense " + expenseId + " does not exist"));

        Trip trip = e.getTrip();

        if (!validateTripUser(trip, userId)) {
            throw new TravelBudgetException("failed to execute method - trip does not belong to user");
        }

        expenseRepo.deleteById(e.getId());

        // update total amount
        updateTotalAmount(trip);

    }

    public Expense getOneExpense(int expenseId, int userId) throws TravelBudgetException {

        Expense e = expenseRepo.findById(expenseId).orElseThrow(
                () -> new TravelBudgetException("Failed to delete expense - expense " + expenseId + " does not exist"));

        if (!validateTripUser(e.getTrip(), userId)) {
            throw new TravelBudgetException("failed to execute method - trip does not belong to user");
        }

        return e;
    }

    public List<Expense> getAllExpenses(int tripId, int userId) throws TravelBudgetException {

        if (!validateTripUser(getTripDetails(tripId), userId)) {
            throw new TravelBudgetException("failed to execute method - trip does not belong to user");
        }

        return expenseRepo.findAllByTripId(tripId);
    }

    public Trip getTripDetails(int tripId) throws TravelBudgetException {

        return tripRepo.findById(tripId)
                .orElseThrow(() -> new TravelBudgetException("Failed to get trip - trip " + tripId + " not found"));
    }

    private void updateTotalAmount(Trip trip) {

        double sum = expenseRepo.findAllByTripId(trip.getId())

                .stream().map(e -> e.getAmount())

                .collect(Collectors.summingDouble(Double::doubleValue));

        trip.setTotalSpent(sum);
        if (trip.getBudget() < sum) {
            trip.setOverBudget(true);
        }
        tripRepo.save(trip);

    }

    private double calculateAmount(Expense expense, int tripId) throws TravelBudgetException {

        double localAmount = expense.getLocalAmount();
        Currency myLocalCurrency = getTripDetails(tripId).getCurrency();
        ExchangeRate exchangeRate = getOneExchangeRate(expense.getLocalCurrencyCode(), myLocalCurrency);
        return localAmount * exchangeRate.getRate();

    }

    /**
     * method to get an exchange rate for base/target currency pair. if the rate
     * does not exist in the DB, the function will try to call the currency
     * converter restAPI
     *
     * @param base   currencyCode
     * @param target currencyCode
     * @return ExchangeRate
     * @throws TravelBudgetException
     */
    private ExchangeRate getOneExchangeRate(Currency base, Currency target) throws TravelBudgetException {

        Optional<ExchangeRate> opt = exchangeRateRepo.findByBaseCurrencyCodeAndTargetCurrencyCode(base, target);

        if (opt.isPresent()) {
            return opt.get();
        }
        try {
            return currencyConverter.getOneExchangeRateFromAPI(base, target, 1);
        } catch (JsonProcessingException | TravelBudgetException e) {
//			e.printStackTrace();
            throw new TravelBudgetException("failed to get exchange rate: " + e.getMessage());
        }
    }

}
