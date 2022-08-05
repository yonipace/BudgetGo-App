package app.core.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import app.core.entities.ExchangeRate.Currency;
import app.core.entities.Rate;
import app.core.services.ExchangeRateService;

@CrossOrigin
@RestController
@RequestMapping("/currency")
public class ExchangeRateController {

	@Autowired
	private ExchangeRateService service;

	@GetMapping
	public List<Rate> getAllRates() {

		return service.getAllRates();
	}

	@GetMapping("/{code}")
	public Rate getOneRate(@PathVariable Currency code) {

		try {
			return service.getOneRate(code);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

}
