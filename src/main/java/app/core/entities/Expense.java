package app.core.entities;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import app.core.entities.ExchangeRate.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = { "trip" })
@Table(name = "expenses")
@Entity
public class Expense {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String title;
	@Enumerated(EnumType.STRING)
	private Category category;
	private double localAmount;
	@Enumerated(EnumType.STRING)
	private Currency localCurrencyCode;
	private LocalDate date;
	private String note;
	private double amount;

	@ManyToOne
	@JoinColumn(name = "trip_id")
	private Trip trip;

	public Expense(String name, Category category, double localAmount, Currency localCurrencyCode, LocalDate date,
			String note) {

		this.title = name;
		this.category = category;
		this.localAmount = localAmount;
		this.localCurrencyCode = localCurrencyCode;
		this.date = date;
		this.note = note;
	}

	// this CTOR is used for sending an updated expense
	public Expense(int id, String name, Category category, double localAmount, Currency localCurrencyCode,
			LocalDate date, String note) {

		this.title = name;
		this.category = category;
		this.localAmount = localAmount;
		this.localCurrencyCode = localCurrencyCode;
		this.date = date;
		this.note = note;
	}

	public enum Category {

		FOOD, FLIGHT, TRANSPORTATION, HOTEL, SHOPPING, RESTAURANT, ATTRATCION, INSURANCE, MEDICAL, FEES, TICKETS,
		MUSEUM, OTHER

	}

}
