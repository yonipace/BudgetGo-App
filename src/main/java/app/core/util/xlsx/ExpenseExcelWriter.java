package app.core.util.xlsx;

import app.core.entities.Expense;

import java.util.List;

//@Component
public class ExpenseExcelWriter extends AbstractExcelWriter<Expense> {

    public ExpenseExcelWriter(List<Expense> list) {
        super(list);
    }
}
