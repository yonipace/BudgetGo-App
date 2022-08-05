package app.core.jobs;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import app.core.api.currency.CurrencyConverter;
import app.core.api.currency.TargetCurrency;
import app.core.entities.ExchangeRate;
import app.core.entities.ExchangeRate.Currency;
import app.core.entities.Rate;
import app.core.repositories.RateRepository;

@Component
public class CurrencyJob {

	@Autowired
	private CurrencyConverter currencyConverter;
	@Autowired
	private RateRepository rateRepository;

	@Transactional
	@Scheduled(cron = "0 0 6 * * *")
	public void updateRates() throws Exception {
		try {
			Thread.sleep(2000);

			Map<Currency, String> symbols = new HashMap<ExchangeRate.Currency, String>();
			symbols.putAll(map1);
			symbols.putAll(map2);
			symbols.putAll(map3);

			Map<String, TargetCurrency> map = currencyConverter.getRatesMap(Currency.USD);

			Currency[] currencies = Currency.values();

//			Set<String> keys = map.keySet();

			for (Currency currency : currencies) {

				Rate rate = new Rate();

				rate.setCode(currency);
				rate.setUpdated(LocalDate.now());
				rate.setSymbol(symbols.get(currency));

				map.keySet().stream()

						.filter(k -> k.equals(currency.toString()))

						.peek(e -> rate.setName(map.get(e).getCurrencyName()))

						.forEach(e -> rate.setRate(Double.parseDouble(map.get(e).getRate())));

				System.out.println(rate);

				saveRateToDB(rate);

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void saveRateToDB(Rate rate) {

		// if rate exists in the DB, set the id to the same so it will update and not
		// create a new row
		rateRepository.findByCode(rate.getCode()).ifPresent(e -> rate.setId(e.getId()));

		rateRepository.save(rate);

	}

	public static Map<Currency, String> map1 =

			Map.of(Currency.ILS, "₪", Currency.ARS, "$", Currency.AUD, "$", Currency.BRL, "R$", Currency.CAD, "$",
					Currency.CHF, "CHF", Currency.CZK, "Kč", Currency.EGP, "£", Currency.ETB, "ETB", Currency.EUR, "€");

	public static Map<Currency, String> map2 =

			Map.of(

					Currency.GBP, "£", Currency.GEL, "GEL", Currency.HUF, "Ft", Currency.INR, "₹", Currency.ISK, "kr",
					Currency.JOD, "JOD", Currency.JPY, "¥", Currency.MXN, "$", Currency.NPR, "₨", Currency.NZD, "$");

	public static Map<Currency, String> map3 =

			Map.of(

					Currency.PLN, "zł", Currency.RUB, "₽", Currency.SGD, "$", Currency.THB, "฿", Currency.TRY, "₺",
					Currency.USD, "$", Currency.VND, "₫");

}
