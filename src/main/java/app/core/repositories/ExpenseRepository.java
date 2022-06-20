package app.core.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import app.core.entities.Expense;
import app.core.entities.Expense.Category;

public interface ExpenseRepository extends JpaRepository<Expense, Integer> {

	boolean existsByIdAndTripId(int id, int tripId);

	Optional<Expense> findByIdAndTripId(int id, int tripId);

	List<Expense> findAllByTripId(int tripId);

	List<Expense> findAllByTripIdAndCategory(int tripId, Category category);

	List<Expense> findAllByTripIdAndAmountLessThanEqual(int tripId, double amount);

	List<Expense> findAllByTripIdAndDateBetween(int id, LocalDate startDate, LocalDate endDate);

}
