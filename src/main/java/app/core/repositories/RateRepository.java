package app.core.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import app.core.entities.ExchangeRate.Currency;
import app.core.entities.Rate;

public interface RateRepository extends JpaRepository<Rate, Integer> {

	boolean existsByCode(Currency currencyCode);

	Optional<Rate> findByCode(Currency currencyCode);

}
