package app.core.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import app.core.entities.ExchangeRate.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = { "users", "expenses" })
@Table(name = "trips")
@Entity
public class Trip {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String destination;
	private double budget;
	private double totalSpent;
	private boolean isOverBudget;
	private Currency myLocalCurrency;

	@ManyToMany
	@JoinTable(name = "users_trips", joinColumns = @JoinColumn(name = "trip_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<User> users;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "trip")
	private List<Expense> expenses;

	public Trip(String name, String destination, double budget, Currency myLocalCurrency) {

		this.name = name;
		this.destination = destination;
		this.budget = budget;
		this.myLocalCurrency = myLocalCurrency;
	}

	public void addUser(User user) {

		if (this.users == null) {
			this.users = new HashSet<User>();
		}

		this.users.add(user);
	}

	public void addExpense(Expense expense) {

		if (this.expenses == null) {

			this.expenses = new ArrayList<Expense>();

		}
		expense.setTrip(this);
		this.expenses.add(expense);

	}

}
