package co.sepulveda.core.session;

/**
 *
 * @author cas
 */
public interface SessionManager {

    public void create(Session session) throws Exception;
    public Session loadByToken(String token) throws Exception;
    public void deleteByToken(String token) throws Exception;
}
