package co.sepulveda.core.employee;

/**
 *
 * @author cas
 */
public interface EmployeeManager {
    public void create(Employee employee) throws Exception;
    public Employee loadByPersonalId(String personalId) throws Exception;
}
