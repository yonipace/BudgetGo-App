package app.core.entities;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import app.core.entities.ExchangeRate.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "rates")
public class Rate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Enumerated(EnumType.STRING)
	private Currency code;
	private String name;
	private double rate;
	private LocalDate updated;
	private String symbol;

	public static Map<Currency, String> map = new HashMap<ExchangeRate.Currency, String>();

//			Map.of(
//
//			Currency.ILS, "₪", Currency.ARS, "$", Currency.AUD, "$", Currency.BRL, "R$", Currency.CAD, "$",
//			Currency.CHF, "CHF", Currency.CZK, "Kč", Currency.EGP, "£", Currency.ETB, "ETB", Currency.EUR, "€",
//			Currency.GBP, "£", Currency.GEL, "GEL", Currency.HUF, "Ft", Currency.INR, "₹", Currency.ISK, "kr",
//			Currency.JOD, "JOD", Currency.JPY, "¥", Currency.MXN, "$", Currency.NPR, "₨", Currency.NZD, "$",
//			Currency.PLN, "zł", Currency.RUB, "₽", Currency.SGD, "$", Currency.THB, "฿", Currency.TRY, "₺",
//			Currency.USD, "$", Currency.VND, "₫");
}
