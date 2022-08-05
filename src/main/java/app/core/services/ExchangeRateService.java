package app.core.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.core.entities.ExchangeRate.Currency;
import app.core.entities.Rate;
import app.core.repositories.RateRepository;

@Service
@Transactional
public class ExchangeRateService {

	@Autowired
	private RateRepository rateRepository;

	public List<Rate> getAllRates() {

		return rateRepository.findAll();
	}

	public Rate getOneRate(Currency code) throws Exception {

		return rateRepository.findByCode(code)
				.orElseThrow(() -> new Exception("Failed to get " + code + " exchange rate"));
	}

}
