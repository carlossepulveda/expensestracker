package co.sepulveda.core.employee;

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
        if (getByEmail(employee.getEmail()) != null) {
            throw new Exception("Employee '" + employee.getEmail() + "' already exists.");
        }

        try {
            entityManager.persist(employee);
        } catch (Exception e) {
            throw new Exception("Employee " + employee + " couldn't be created: " + e.getMessage(), e);
        }
    }

    private Employee getByEmail(String email) throws Exception {
        try {
            Query query = entityManager.createQuery("select e from Employee e where e.email = :email");
            query.setParameter("email", email);

            List<Employee> employees = query.getResultList();
            if (employees != null && !employees.isEmpty()) {
                return employees.get(0);
            }

            return null;
        } catch (Exception e) {
            throw new Exception("Exception loading user by employee: " + e.getMessage(), e);
        }
    }
}
