package co.sepulveda.core.trip;

import com.elibom.jogger.exception.ConflictException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Carlos Sepulveda <ca.sepulveda.sanchez@gmail.com>
 */
public class JPAExpenseManager implements ExpenseManager {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Expense> groupByTag() throws Exception {
        Query query = entityManager.createQuery("select e.tag, SUM(e.amount) from Expense e group by e.tag");
        return query.getResultList();
    }

}
