package co.sepulveda.core.employee;

import com.elibom.jogger.exception.ConflictException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Carlos Sepulveda <ca.sepulveda.sanchez@gmail.com>
 */
public class JPAEmployeeManager implements EmployeeManager {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public void create(Employee employee) throws Exception{
        if (loadByPersonalId(employee.getPersonalId()) != null) {
            throw new ConflictException("Employee '" + employee.getEmail() + "' already exists.");
        }

        try {
            entityManager.persist(employee);
        } catch (ConflictException ce) {
            throw ce;
        } catch (Exception e) {
            throw new Exception("Employee " + employee + " couldn't be created: " + e.getMessage(), e);
        }
    }

    @Override
    public Employee loadByPersonalId(String personalId) throws Exception {
        try {
            Query query = entityManager.createQuery("select e from Employee e where e.personalId = :personalId");
            query.setParameter("personalId", personalId);

            List<Employee> employees = query.getResultList();
            if (employees != null && !employees.isEmpty()) {
                return employees.get(0);
            }

            return null;
        } catch (Exception e) {
            throw new ConflictException();
        }
    }

    @Override
    public List<Employee> list() {
        Query query = entityManager.createQuery("select e from Employee e ");
        return query.getResultList();
    }
}
