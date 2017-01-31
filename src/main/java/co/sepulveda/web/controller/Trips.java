package co.sepulveda.web.controller;

import co.sepulveda.core.employee.Employee;
import co.sepulveda.core.employee.EmployeeManager;
import co.sepulveda.core.trip.Expense;
import co.sepulveda.core.trip.Trip;
import co.sepulveda.core.trip.TripManager;
import co.sepulveda.web.forms.ExpenseForm;
import co.sepulveda.web.forms.FormParser;
import co.sepulveda.web.forms.TripForm;
import com.elibom.jogger.exception.BadRequestException;
import com.elibom.jogger.exception.NotFoundException;
import com.elibom.jogger.http.Request;
import com.elibom.jogger.http.Response;
import java.util.Date;

/**
 *
 * @author Carlos Sepulveda <ca.sepulveda.sanchez@gmail.com>
 */
public class Trips {

    private TripManager tripManager;
    private EmployeeManager employeeManager;

    public void create(Request request, Response response) throws Exception {
        String body = request.getBody().asString();
        TripForm form = FormParser.parse(body, TripForm.class);
        Employee employee = employeeManager.loadByPersonalId(form.getEmployeePersonalId());
        if (employee == null) {
            throw new NotFoundException();
        }

        Trip trip = new Trip();
        trip.setCreationTime(new Date());
        trip.setEmployee(employee);
        trip.setName(form.getName());
        trip.setStatus(Trip.STATUS_OPEN);

        tripManager.create(trip);
        response.status(201).write("{}");
    }

    public void addExpense(Request request, Response response) throws Exception {
        Employee employee = getEmployeeFromSession(response);

        String body = request.getBody().asString();
        ExpenseForm form = FormParser.parse(body, ExpenseForm.class);

        long tripId = getTripId(request);
        Trip trip = tripManager.load(tripId, employee.getId());
        if (trip == null) throw new NotFoundException();

        Expense expense = new Expense();
        expense.setAmount(form.getAmount());
        expense.setTag(form.getTag());

        trip.addExpense(expense);
        tripManager.update(trip);
        response.status(200).write("{}");
    }

    private Employee getEmployeeFromSession(Response response) {
        Employee employee = new Employee();
        employee.setId(4);
        employee.setPersonalId("1090420131");
        return employee;
    }

    private long getTripId(Request request) {
        try {
            return Long.parseLong(request.getPathVariable("id"));
        } catch (Exception e) {
            throw new BadRequestException();
        }
    }

    public void setTripManager(TripManager tripManager) {
        this.tripManager = tripManager;
    }

    public void setEmployeeManager(EmployeeManager employeeManager) {
        this.employeeManager = employeeManager;
    }
}
