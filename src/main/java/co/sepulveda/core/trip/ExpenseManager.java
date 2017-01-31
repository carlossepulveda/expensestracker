package co.sepulveda.core.trip;

import java.util.List;

/**
 *
 * @author cas
 */
public interface ExpenseManager {

    public List<Expense> groupByTag() throws Exception ;
}
