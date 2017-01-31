package co.sepulveda.core.trip;

/**
 *
 * @author Carlos Sepulveda <ca.sepulveda.sanchez@gmail.com>
 */
public interface TripManager {

    public void create(Trip trip) throws Exception;
    public Trip load(long id, long employeeId) throws Exception;
    public void update(Trip trip) throws Exception;
}
