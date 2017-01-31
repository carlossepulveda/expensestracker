package co.sepulveda.core.session;

import co.sepulveda.core.employee.Employee;
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
public class JPASessionManager implements SessionManager {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void create(Session session) throws Exception {
        if (loadByToken(session.getToken()) != null) {
            throw new Exception("Session already exists.");
        }

        try {
            entityManager.persist(session);
        } catch (Exception e) {
            throw new Exception("Session couldn't be created: " + e.getMessage(), e);
        }
    }

    @Override
    public Session loadByToken(String token) throws Exception {
        Query query = entityManager.createQuery("select s from Session s where s.token = :token");
        query.setParameter("token", token);

        List<Session> sessions = query.getResultList();
        if (sessions != null && !sessions.isEmpty()) {
            return sessions.get(0);
        }

        return null;
    }

    @Override
    public void deleteByToken(String token) throws Exception {
        Session temp = loadByToken(token);
        if (temp == null) {
            throw new Exception("Session does not exists");
        }

        entityManager.remove(temp);
    }

}
