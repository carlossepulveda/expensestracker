package co.sepulveda.web.controller;

import co.sepulveda.core.employee.Employee;
import co.sepulveda.core.session.Session;
import co.sepulveda.core.trip.Expense;
import co.sepulveda.core.trip.Trip;
import co.sepulveda.core.trip.TripManager;
import co.sepulveda.web.Secured;
import co.sepulveda.web.forms.ExpenseForm;
import co.sepulveda.web.forms.FormParser;
import co.sepulveda.web.forms.TripResponse;
import com.elibom.jogger.exception.BadRequestException;
import com.elibom.jogger.exception.ConflictException;
import com.elibom.jogger.exception.NotFoundException;
import com.elibom.jogger.http.Http;
import com.elibom.jogger.http.Http.ContentType;
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
@Secured(role=Employee.ROLE_EMPLOYEE)
public class Trips {

    private TripManager tripManager;

    public void addExpense(Request request, Response response) throws Exception {
        Employee employee = getEmployeeFromSession(response);

        String body = request.getBody().asString();
        ExpenseForm form = FormParser.parse(body, ExpenseForm.class);

        long tripId = getTripId(request);
        Trip trip = tripManager.load(tripId, employee.getId());
        if (trip == null) throw new NotFoundException();
        if (trip.getStatus().equals("closed")) throw new ConflictException();

        Expense expense = new Expense();
        expense.setAmount(form.getAmount());
        expense.setTag(form.getTag());

        trip.addExpense(expense);
        tripManager.update(trip);
        response.status(200).write("{}");
    }

    public void load(Request request, Response response) throws Exception {
        String accept = request.getHeader(Http.Headers.ACCEPT);
        if (accept != null && accept.contains("html")) {
            response.contentType(ContentType.TEXT_HTML).render("trip.ftl");
            return;
        }

        Employee employee = getEmployeeFromSession(response);
        long tripId = getTripId(request);
        Trip trip = tripManager.load(tripId, employee.getId());
        if (trip == null) throw new NotFoundException();

        TripResponse tripR = new TripResponse();
        tripR.setCreationTime(trip.getCreationTime());
        tripR.setEmployeeId(trip.getEmployee().getId());
        tripR.setEmployeePersonId(trip.getEmployee().getPersonalId());
        tripR.setEndTime(trip.getEndTime());
        tripR.setExpenses(trip.getExpenses());
        tripR.setId(trip.getId());
        tripR.setName(trip.getName());
        tripR.setStatus(trip.getStatus());

        String json = new ObjectMapper().writeValueAsString(tripR);
        response.contentType(ContentType.APPLICATION_JSON).write(json);
    }

    public void endTrip(Request request, Response response) throws Exception {
        Employee employee = getEmployeeFromSession(response);
        long tripId = getTripId(request);
        Trip trip = tripManager.load(tripId, employee.getId());
        if (trip == null) throw new NotFoundException();

        trip.setStatus(Trip.STATUS_CLOSED);
        trip.setEndTime(new Date());
        tripManager.update(trip);
        response.status(200).write("{}");
    }

    public void list(Request request, Response response) throws Exception {
        String accept = request.getHeader(Http.Headers.ACCEPT);
        if (accept != null && accept.contains("html")) {
            response.contentType(ContentType.TEXT_HTML).render("trips.ftl");
            return;
        }

        Employee employee = getEmployeeFromSession(response);
        List<Trip> trips = tripManager.listByEmployee(employee.getId());
        List<TripResponse> tripsResponse = new ArrayList();
        for (Trip trip : trips) {
            tripsResponse.add(buildTripResponse(trip));
        }

        String json = new ObjectMapper().writeValueAsString(tripsResponse);
        response.contentType(ContentType.APPLICATION_JSON).write(json);
    }

    private TripResponse buildTripResponse(Trip trip) {
        TripResponse tripR = new TripResponse();
        tripR.setCreationTime(trip.getCreationTime());
        tripR.setEmployeeId(trip.getEmployee().getId());
        tripR.setEmployeePersonId(trip.getEmployee().getPersonalId());
        tripR.setEndTime(trip.getEndTime());
        tripR.setExpenses(trip.getExpenses());
        tripR.setId(trip.getId());
        tripR.setName(trip.getName());
        tripR.setStatus(trip.getStatus());
        return tripR;
    }

    private Employee getEmployeeFromSession(Response response) {
        Session session = (Session) response.getAttributes().get("session");
        return session.getEmployee();
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
}
