package co.sepulveda.web.controller;

import co.sepulveda.core.employee.Employee;
import co.sepulveda.core.employee.EmployeeManager;
import co.sepulveda.core.trip.Expense;
import co.sepulveda.core.trip.ExpenseManager;
import co.sepulveda.core.trip.Trip;
import co.sepulveda.core.trip.TripManager;
import co.sepulveda.web.Secured;
import co.sepulveda.web.forms.EmployeeForm;
import co.sepulveda.web.forms.FormParser;
import co.sepulveda.web.forms.TripForm;
import co.sepulveda.web.forms.TripResponse;
import com.elibom.jogger.exception.BadRequestException;
import com.elibom.jogger.exception.NotFoundException;
import com.elibom.jogger.http.Http;
import com.elibom.jogger.http.Request;
import com.elibom.jogger.http.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Carlos Sepulveda <ca.sepulveda.sanchez@gmail.com>
 */
@Secured(role=Employee.ROLE_ADMIN)
public class Admin {

    private EmployeeManager employeeManager;
    private TripManager tripManager;
    private ExpenseManager expenseManager;

    public void createEmployee(Request request, Response response) throws Exception {
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
        employee.setRole(form.getRole());
        return employee;
    }

    public void createTrip(Request request, Response response) throws Exception {
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

    public void loadTrip(Request request, Response response) throws Exception {
        long tripId = getTripId(request);
        Trip trip = tripManager.load(tripId);
        if (trip == null) throw new NotFoundException();
        TripResponse tripR = buildTripResponse(trip);
        String json = new ObjectMapper().writeValueAsString(tripR);
        response.contentType(Http.ContentType.APPLICATION_JSON).write(json);
    }

    public void listTrips(Request request, Response response) throws Exception {
        List<Trip> trips = tripManager.list();
        List<TripResponse> tripsResponse = new ArrayList();
        for (Trip trip : trips) {
            TripResponse tr = buildTripResponse(trip);
            tripsResponse.add(tr);
        }
        String json = new ObjectMapper().writeValueAsString(tripsResponse);
        response.contentType(Http.ContentType.APPLICATION_JSON).write(json);
    }

    public void groupExpenses(Request request, Response response) throws Exception {
        List<Expense> expenses = expenseManager.groupByTag();
        String json = new ObjectMapper().writeValueAsString(expenses);
        response.contentType(Http.ContentType.APPLICATION_JSON).write(json);
    }

    private TripResponse buildTripResponse(Trip trip) {
        TripResponse tripR = new TripResponse();
        tripR.setCreationTime(trip.getCreationTime());
        tripR.setEmployeeId(trip.getEmployee().getId());
        tripR.setEmployeePersonId(trip.getEmployee().getPersonalId());
        tripR.setEmployeeName(trip.getEmployee().getName());
        tripR.setEndTime(trip.getEndTime());
        tripR.setExpenses(trip.getExpenses());
        tripR.setId(trip.getId());
        tripR.setName(trip.getName());
        tripR.setStatus(trip.getStatus());
        return tripR;
    }

    private long getTripId(Request request) {
        try {
            return Long.parseLong(request.getPathVariable("id"));
        } catch (Exception e) {
            throw new BadRequestException();
        }
    }
    public void setEmployeeManager(EmployeeManager employeeManager) {
        this.employeeManager = employeeManager;
    }

    public void setTripManager(TripManager tripManager) {
        this.tripManager = tripManager;
    }

    public void setExpenseManager(ExpenseManager expenseManager) {
        this.expenseManager = expenseManager;
    }
}
