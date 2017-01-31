package co.sepulveda.core.trip;

import co.sepulveda.core.employee.Employee;
import com.elibom.jogger.exception.ConflictException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Carlos Sepulveda <ca.sepulveda.sanchez@gmail.com>
 */
public class JPATripManager implements TripManager {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public void create(Trip trip) throws Exception{
        try {
            entityManager.persist(trip);
        } catch (Exception e) {
            throw new Exception("Trip couldn't be created: " + e.getMessage(), e);
        }
    }

}
