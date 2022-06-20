package app.core.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import app.core.entities.ExchangeRate;
import app.core.entities.ExchangeRate.Currency;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Integer> {

	boolean existsByBaseCurrencyCodeAndTargetCurrencyCode(Currency base, Currency target);

	Optional<ExchangeRate> findByBaseCurrencyCodeAndTargetCurrencyCode(Currency base, Currency target);

	List<ExchangeRate> findAllByBaseCurrencyCode(Currency base);

}
