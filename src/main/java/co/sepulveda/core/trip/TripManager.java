package co.sepulveda.core.trip;

import java.util.List;

/**
 *
 * @author Carlos Sepulveda <ca.sepulveda.sanchez@gmail.com>
 */
public interface TripManager {

    public void create(Trip trip) throws Exception;
    public Trip load(long id) throws Exception;
    public Trip load(long id, long employeeId) throws Exception;
    public void update(Trip trip) throws Exception;
    public List<Trip> list() throws Exception;
}
