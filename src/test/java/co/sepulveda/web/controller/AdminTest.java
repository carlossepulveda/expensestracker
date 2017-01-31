package co.sepulveda.web.controller;

import co.sepulveda.web.forms.TripResponse;
import com.elibom.jogger.http.Cookie;
import com.elibom.jogger.http.Http;
import com.elibom.jogger.http.Response;
import com.elibom.jogger.test.MockResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.dbunit.operation.DatabaseOperation;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 *
 * @author Carlos Sepulveda <ca.sepulveda.sanchez@gmail.com>
 */
public class AdminTest extends BaseTest {

    private static String dataset = "dbunit/employees-common.xml";

    @Test
    public void shouldCreateEmployee() throws Exception {
        databaseOperation(DatabaseOperation.INSERT, dataset);
        JSONObject json = new JSONObject();
        json.put("email","carlos@sepulveda.co");
        json.put("name","carlos S");
        json.put("password","password234");
        json.put("personalId","11111");
        json.put("phone","3000000000");
        json.put("role", "admin");

        MockResponse response = post("/admin/employee")
                .setHeader(Http.Headers.ACCEPT, "text/json")
                .setBodyAsString(json.toString())
                .addCookie(new Cookie("expenses_session_id", "1234567890"))
                .run();

        Assert.assertEquals(response.getStatus(), 201);
    }

    @Test
    public void shouldNotCreateEmployeeMissingFields() throws Exception {
        databaseOperation(DatabaseOperation.INSERT, dataset);
        JSONObject json = new JSONObject();
        json.put("email","carlos@sepulveda.co");
        json.put("name","carlos S");
        json.put("password","password234");
        json.put("personalId","11111");
        json.put("phone","3000000000");

        MockResponse response = post("/admin/employee")
                .setHeader(Http.Headers.ACCEPT, "text/json")
                .setBodyAsString(json.toString())
                .addCookie(new Cookie("expenses_session_id", "1234567890"))
                .run();

        Assert.assertEquals(response.getStatus(), Response.BAD_REQUEST);
    }

    @Test
    public void shouldNotCreateEmployeeConflictException() throws Exception {
        databaseOperation(DatabaseOperation.INSERT, dataset);

        JSONObject json = new JSONObject();
        json.put("email","carlos@sepulveda.co");
        json.put("name","carlos S");
        json.put("password","password234");
        json.put("personalId","123123123");
        json.put("phone","3000000000");
        json.put("role", "admin");

        MockResponse response = post("/admin/employee")
                .setHeader(Http.Headers.ACCEPT, "text/json")
                .setBodyAsString(json.toString())
                .addCookie(new Cookie("expenses_session_id", "1234567890"))
                .run();

        Assert.assertEquals(response.getStatus(), Response.CONFLICT);
    }

     @Test
    public void shouldCreateATrip() throws Exception {
        databaseOperation(DatabaseOperation.INSERT, dataset);
        JSONObject json = new JSONObject();
        json.put("name","carlos S");
        json.put("employeePersonalId","123123123");

        MockResponse response = post("/admin/trip")
                .setHeader(Http.Headers.ACCEPT, "text/json")
                .setBodyAsString(json.toString())
                .addCookie(new Cookie("expenses_session_id", "1234567890"))
                .run();

        Assert.assertEquals(response.getStatus(), 201);
    }

    @Test
    public void shouldNotCreateATripIfUserDontExist() throws Exception {
        databaseOperation(DatabaseOperation.INSERT, dataset);
        JSONObject json = new JSONObject();
        json.put("name","carlos S");
        json.put("employeePersonalId","12");

        MockResponse response = post("/admin/trip")
                .setHeader(Http.Headers.ACCEPT, "text/json")
                .setBodyAsString(json.toString())
                .addCookie(new Cookie("expenses_session_id", "1234567890"))
                .run();

        Assert.assertEquals(response.getStatus(), Response.NOT_FOUND);
    }

    @Test
    public void shouldLoadTrip() throws Exception {
        databaseOperation(DatabaseOperation.INSERT, dataset);

        MockResponse response = get("/admin/trip/1")
                .setHeader(Http.Headers.ACCEPT, "text/json")
                .addCookie(new Cookie("expenses_session_id", "1234567890"))
                .run();

        Assert.assertEquals(response.getStatus(), Response.OK);
        ObjectMapper mapper = new ObjectMapper();
        TripResponse trip = mapper.readValue(response.getOutputAsString(), TripResponse.class);
        Assert.assertNotNull(trip);
        Assert.assertEquals(trip.getName(), "Prague");
    }

    @Test
    public void shouldListTrips() throws Exception {
        databaseOperation(DatabaseOperation.INSERT, dataset);

        MockResponse response = get("/admin/trip")
                .setHeader(Http.Headers.ACCEPT, "text/json")
                .addCookie(new Cookie("expenses_session_id", "1234567890"))
                .run();

        Assert.assertEquals(response.getStatus(), Response.OK);
        ObjectMapper mapper = new ObjectMapper();
        List<TripResponse> trips = mapper.readValue(response.getOutputAsString(), new TypeReference<List<TripResponse>>(){});
        Assert.assertNotNull(trips);
    }

    @Test
    public void shouldListExpensesGroupByTag() throws Exception {
        databaseOperation(DatabaseOperation.INSERT, dataset);

        MockResponse response = get("/admin/expense")
                .setHeader(Http.Headers.ACCEPT, "text/json")
                .addCookie(new Cookie("expenses_session_id", "1234567890"))
                .run();

        Assert.assertEquals(response.getStatus(), Response.OK);
        JSONArray expenses = new JSONArray(response.getOutputAsString());
        Assert.assertNotNull(expenses);
        Assert.assertEquals(expenses.length(), 1);
    }
}
