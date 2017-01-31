package co.sepulveda.core.trip;

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
public class JPATripManager implements TripManager {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public void create(Trip trip) throws Exception {
        try {
            entityManager.persist(trip);
        } catch (Exception e) {
            throw new Exception("Trip couldn't be created: " + e.getMessage(), e);
        }
    }

    @Override
    public Trip load(long id, long employeeId) throws Exception {
        try {
            Query query = entityManager.createQuery("select t from Trip t where t.employee.id = :employeeId and t.id = :id");
            query.setParameter("employeeId", employeeId);
            query.setParameter("id", id);

            List<Trip> trip = query.getResultList();
            if (trip != null && !trip.isEmpty()) {
                return trip.get(0);
            }

            return null;
        } catch (Exception e) {
            throw new ConflictException();
        }
    }

    @Override
    @Transactional
    public void update(Trip trip) throws Exception {
        entityManager.merge(trip);
        entityManager.flush();
    }

    @Override
    public Trip load(long id) throws Exception {
        return entityManager.find(Trip.class, id);
    }

    @Override
    public List<Trip> list() throws Exception {
        try {
            Query query = entityManager.createQuery("select t from Trip t");
            return query.getResultList();
        } catch (Exception e) {
            throw new ConflictException();
        }
    }
}
