package app.core.test;

import java.util.HashMap;
import java.util.Map;

import app.core.entities.ExchangeRate;
import app.core.entities.ExchangeRate.Currency;

public class CurrencyCalc {

	public static void main(String[] args) {

		double usd = 1;
		double ils = 3.3717;
		double eur = 0.9772;

		// amount from USD to ILS

		System.out.println("USD to ILS: " + fromBaseToTarget(usd, ils) * 100);

		// amount from ILS to USD

		System.out.println("ILS to USD: " + fromBaseToTarget(ils, usd) * 100);

		// amount from ILS to EUR

		System.out.println("ILS to EUR: " + fromBaseToTarget(ils, eur) * 100);

		System.out.println(map.get(Currency.ILS));

		Map<Currency, String> symbols = new HashMap<ExchangeRate.Currency, String>();
		symbols.putAll(map1);
		symbols.putAll(map2);
		symbols.putAll(map3);

		symbols.keySet().forEach(k -> System.out.println(symbols.get(k)));
//		symbols.keySet().forEach(k -> System.out.println(k));

	}

	public static Map<Currency, String> map = new HashMap<ExchangeRate.Currency, String>();

	private static double fromBaseToTarget(double base, double target) {

		if (base == 1) {
			return target;
		}
		if (target == 1) {
			return target / base;
		}
		if (base != 1 && target != 1) {
			double baseUpdate = 1 / base;
			double targetUpdate = 1 / target;

			return baseUpdate / targetUpdate;
		} else {
			return 0;
		}

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
