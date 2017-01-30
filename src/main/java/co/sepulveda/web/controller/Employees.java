package co.sepulveda.web.controller;

import co.sepulveda.core.employee.Employee;
import co.sepulveda.core.employee.EmployeeManager;
import co.sepulveda.web.forms.EmployeeForm;
import co.sepulveda.web.forms.FormParser;
import com.elibom.jogger.http.Request;
import com.elibom.jogger.http.Response;

/**
 *
 * @author Carlos Sepulveda <ca.sepulveda.sanchez@gmail.com>
 */
public class Employees {

    private EmployeeManager employeeManager;

    public void create(Request request, Response response) throws Exception {
        String body = request.getBody().asString();
        EmployeeForm form = FormParser.parse(body, EmployeeForm.class);
        Employee employee =  buildEmployee(form);
        employeeManager.create(employee);
        response.status(201).write("{}");
    }

    private Employee buildEmployee(EmployeeForm form) {
        Employee employee = new Employee();
        employee.setEmail(form.getEmail());
        employee.setName(form.getName());
        employee.setPassword(form.getPassword());
        employee.setPersonalId(form.getPersonalId());
        employee.setPhone(form.getPhone());
        return employee;
    }

    public void setEmployeeManager(EmployeeManager employeeManager) {
        this.employeeManager = employeeManager;
    }
}
