package app.core.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import app.core.api.currency.CurrencyConverter;
import app.core.entities.ExchangeRate;
import app.core.entities.ExchangeRate.Currency;
import app.core.entities.Expense;
import app.core.entities.Trip;
import app.core.exceptions.TravelBudgetException;

@Service
@Transactional
public class TripService extends ClientService {

	@Autowired
	private CurrencyConverter currencyConverter;

	public Expense addExpense(Expense expense, int tripId) throws TravelBudgetException {

		if (expenseRepo.existsById(expense.getId())) {
			throw new TravelBudgetException("Failed to add expense - expense already exists");
		}
		// set currency exchange
		expense.setAmount(calculateAmount(expense, tripId));

		return expenseRepo.save(expense);

	}

	public void updateExpense(Expense expense, int tripId) throws TravelBudgetException {

		// set currency exchange
		expense.setAmount(calculateAmount(expense, tripId));

		expenseRepo.findById(expense.getId()).ifPresentOrElse((e) -> expenseRepo.save(expense),
				() -> new TravelBudgetException(
						"Failed to update expense - expense " + expense.getId() + " does not exist"));

	}

	public void deleteExpense(int expenseId) throws TravelBudgetException {

		expenseRepo.findById(expenseId).ifPresentOrElse((e) -> expenseRepo.deleteById(e.getId()),
				() -> new TravelBudgetException("Failed to delete expense - expense " + expenseId + " does not exist"));

	}

	public Expense getOneExpense(int expenseId) throws TravelBudgetException {

		return expenseRepo.findById(expenseId).orElseThrow(
				() -> new TravelBudgetException("Failed to get expense - expense " + expenseId + " does not exist"));
	}

	public List<Expense> getAllExpenses(int tripId) throws TravelBudgetException {

		return expenseRepo.findAllByTripId(tripId);
	}

	public Trip getDetails(int tripId) throws TravelBudgetException {

		return tripRepo.findById(tripId)
				.orElseThrow(() -> new TravelBudgetException("Failed to get trip - trip " + tripId + " not found"));
	}

	private double calculateAmount(Expense expense, int tripId) throws TravelBudgetException {

		double localAmount = expense.getAmount();
		Currency myLocalCurrency = getDetails(tripId).getMyLocalCurrency();
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
