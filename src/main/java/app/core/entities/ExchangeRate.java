package app.core.entities;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "exchange_rates")
public class ExchangeRate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Enumerated(EnumType.STRING)
	private Currency baseCurrencyCode;
	private String baseCurrencyName;
	private LocalDate updatedDate;
	@Enumerated(EnumType.STRING)
	private Currency targetCurrencyCode;
	private String targetCurrencyName;
	private double amount;
	private double rate;
	private double rateForAmount;

	public enum Currency {

		ILS, USD, EUR, INR, GBP

	}

	public static Set<String> CurrencyCodes = Set.of("ILS", "USD", "EUR");

}
