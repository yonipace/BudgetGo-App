package app.core.api.currency;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import app.core.entities.ExchangeRate;
import app.core.entities.ExchangeRate.Currency;
import app.core.exceptions.TravelBudgetException;
import kong.unirest.GetRequest;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

@Component
public class CurrencyConverter {

	private String baseUrl = "https://currency-converter5.p.rapidapi.com/currency/convert";
	@Value("${currencyConverter.api.key}")
	private String apiKey;
	@Value("${currencyConverter.api.host}")
	private String apiHost;
	private ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * 
	 * get an exchange rate entity from the currency converter API
	 * 
	 * @param base   base currency code
	 * @param target target currency code
	 * @param amount
	 * @return exchange rate entity of the base-target currency pair
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 * @throws TravelBudgetException
	 */
	public ExchangeRate getOneExchangeRateFromAPI(Currency base, Currency target, double amount)
			throws JsonMappingException, JsonProcessingException, TravelBudgetException {

		// get http response from api
		HttpResponse<String> response = currencyConverterResponse(base, target, amount).asString();
		if (response.getStatus() != 200) {
			throw new TravelBudgetException(String.valueOf(response.getStatus()));
		}

		// get base currency from objectMapper
		BaseCurrency currency = objectMapper.readValue(response.getBody(), BaseCurrency.class);

		// get target currency rates map
		Map<String, TargetCurrency> map = currency.getRates().getAdditionalProperties();

		return createExchangeRate(base, amount, currency, map, target);

	}

	/**
	 * 
	 * get a list of exchange rate entities from the currency converter API
	 * 
	 * @param base base currency code
	 * @return list of exchange rate entities from the base currency (list is
	 *         limited to currencies specified in the CurrencyCode ENUM)
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 * @throws TravelBudgetException
	 */
	public List<ExchangeRate> getAllExchangeRatesFromAPI(Currency base)
			throws JsonMappingException, JsonProcessingException, TravelBudgetException {

		// get http response from api
		HttpResponse<String> response = currencyConverterResponse(base).asString();
		if (response.getStatus() != 200) {
			throw new TravelBudgetException(String.valueOf(response.getStatus()));
		}
		// get base currency from objectMapper
		BaseCurrency currency = objectMapper.readValue(response.getBody(), BaseCurrency.class);

		// get target currencies from the rates list
		Map<String, TargetCurrency> map = currency.getRates().getAdditionalProperties();

		// create list of exchange rates (to be returned at the end)
		List<ExchangeRate> exchangeRates = new ArrayList<ExchangeRate>();
		// loop over all currencies in the ENUM and get their values
		for (Currency targetCurrencyCode : Currency.values()) {

			if (targetCurrencyCode.equals(base)) {
				continue;
			}
			ExchangeRate exchangeRate = createExchangeRate(base, 1, currency, map, targetCurrencyCode);
			exchangeRates.add(exchangeRate);
		}
		return exchangeRates;
	}

	public Map<String, TargetCurrency> getRatesMap(Currency base) throws Exception {
		// get http response from api
		HttpResponse<String> response = currencyConverterResponse(base).asString();
		if (response.getStatus() != 200) {
			throw new TravelBudgetException(String.valueOf(response.getStatus()));
		}
		try {

			// get base currency from objectMapper
			BaseCurrency currency = objectMapper.readValue(response.getBody(), BaseCurrency.class);

			// get target currencies from the rates list
			return currency.getRates().getAdditionalProperties();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	// get one exchange rate for specified currency pair
	public GetRequest currencyConverterResponse(Currency baseCurrency, Currency targetCurrency, double amount) {

		return currencyConverterResponse(baseCurrency).queryString("to", targetCurrency).queryString("amount", amount);

	}

	// get all exchange rates from specified currency
	public GetRequest currencyConverterResponse(Currency baseCurrency) {

		return Unirest.get(this.baseUrl).queryString("from", baseCurrency).header("x-rapidapi-host", apiHost)
				.header("x-rapidapi-key", apiKey);

	}

	private ExchangeRate createExchangeRate(Currency base, double amount, BaseCurrency currency,
			Map<String, TargetCurrency> map, Currency target) {

		String baseCurrencyName = currency.getBaseCurrencyName();
		LocalDate updatedDate = LocalDate.parse(currency.getUpdatedDate());
		TargetCurrency targetCurrency = map.get(target.toString());
		String targetCurrencyName = targetCurrency.getCurrencyName();
		double rate = Double.parseDouble(targetCurrency.getRate());
		double rateForAmount = Double.parseDouble(targetCurrency.getRateForAmount());
		// create new exchange rate
		ExchangeRate exchangeRate = new ExchangeRate(0, base, baseCurrencyName, updatedDate, target, targetCurrencyName,
				amount, rate, rateForAmount);

		return exchangeRate;
	}

}