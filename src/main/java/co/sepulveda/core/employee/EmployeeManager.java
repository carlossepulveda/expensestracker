package co.sepulveda.core.employee;

import java.util.List;

/**
 *
 * @author cas
 */
public interface EmployeeManager {
    public void create(Employee employee) throws Exception;
    public Employee loadByPersonalId(String personalId) throws Exception;
    public List<Employee> list();
}
