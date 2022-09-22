package app.core.services;

import app.core.entities.Expense;
import app.core.repositories.ExpenseRepository;
import app.core.util.xlsx.ExpenseExcelWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelService {

    @Autowired
    ExpenseRepository expenseRepository;


    public ByteArrayInputStream load(int tripId) throws IOException {

        List<Expense> expenses = expenseRepository.findAllByTripId(tripId);

        ExpenseExcelWriter expenseExcelWriter = new ExpenseExcelWriter(expenses);

        try (ByteArrayInputStream in = expenseExcelWriter.loadWorkBook()) {
            return in;
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }

    }

    public Resource getExpenseExcel(List<Expense> expenses) {


        return null;
    }
}
