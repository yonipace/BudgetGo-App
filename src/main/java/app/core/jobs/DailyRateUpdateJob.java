package app.core.jobs;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import app.core.api.currency.CurrencyConverter;
import app.core.entities.ExchangeRate;
import app.core.entities.ExchangeRate.Currency;
import app.core.exceptions.TravelBudgetException;
import app.core.repositories.ExchangeRateRepository;

//@Component
public class DailyRateUpdateJob {

	@Autowired
	private ExchangeRateRepository exchangeRateRepo;
	@Autowired
	private CurrencyConverter currencyConverter;

	@Transactional
	@Scheduled(timeUnit = TimeUnit.HOURS, fixedRate = 24)
	public void updateExchangeRates() {

		// loop over active currency codes and update exchange rates in the DB
		for (Currency currency : Currency.values()) {

			try {
				Thread.sleep(2000);

				List<ExchangeRate> rates = currencyConverter.getAllExchangeRatesFromAPI(currency);

				for (ExchangeRate exchangeRate : rates) {

					// sets the id of the exchange rate from the API to the corresponding rate in
					// the DB
					exchangeRateRepo
							.findByBaseCurrencyCodeAndTargetCurrencyCode(exchangeRate.getBaseCurrencyCode(),
									exchangeRate.getTargetCurrencyCode())
							.ifPresent((e) -> exchangeRate.setId(e.getId()));

					exchangeRateRepo.save(exchangeRate);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (TravelBudgetException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}

		}

	}

}
