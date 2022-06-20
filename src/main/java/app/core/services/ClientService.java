package app.core.services;

import org.springframework.beans.factory.annotation.Autowired;

import app.core.repositories.ExchangeRateRepository;
import app.core.repositories.ExpenseRepository;
import app.core.repositories.TripRepository;
import app.core.repositories.UserRepository;

public abstract class ClientService {

	@Autowired
	protected UserRepository userRepo;
	@Autowired
	protected TripRepository tripRepo;
	@Autowired
	protected ExpenseRepository expenseRepo;
	@Autowired
	protected ExchangeRateRepository exchangeRateRepo;

}
