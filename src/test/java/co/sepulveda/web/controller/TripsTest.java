package co.sepulveda.web.controller;

import co.sepulveda.core.trip.Trip;
import co.sepulveda.core.trip.TripManager;
import co.sepulveda.web.forms.TripResponse;
import com.elibom.jogger.http.Cookie;
import com.elibom.jogger.http.Http;
import com.elibom.jogger.http.Response;
import com.elibom.jogger.test.MockResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.dbunit.operation.DatabaseOperation;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 *
 * @author Carlos Sepulveda <ca.sepulveda.sanchez@gmail.com>
 */
public class TripsTest extends BaseTest {

    private static String dataset = "dbunit/employees-common.xml";

    @Test
    public void shouldAddExpense() throws Exception {
        databaseOperation(DatabaseOperation.INSERT, dataset);
        JSONObject json = new JSONObject();
        json.put("tag","eat");
        json.put("amount","12000");

        MockResponse response = post("/trip/1/expense")
                .setHeader(Http.Headers.ACCEPT, "text/json")
                .addCookie(new Cookie("expenses_session_id", "1234567812"))
                .setBodyAsString(json.toString())
                .run();

        Assert.assertEquals(response.getStatus(), Response.OK);
    }

    @Test
    public void shouldNotAddExpenseTripNotFound() throws Exception {
        databaseOperation(DatabaseOperation.INSERT, dataset);
        JSONObject json = new JSONObject();
        json.put("tag","eat");
        json.put("amount","12000");

        MockResponse response = post("/trip/22/expense")
                .setHeader(Http.Headers.ACCEPT, "text/json")
                .setBodyAsString(json.toString())
                .addCookie(new Cookie("expenses_session_id", "1234567812"))
                .run();

        Assert.assertEquals(response.getStatus(), Response.NOT_FOUND);
    }

    @Test
    public void shouldLoadTrip() throws Exception {
        databaseOperation(DatabaseOperation.INSERT, dataset);

        MockResponse response = get("/trip/1")
                .setHeader(Http.Headers.ACCEPT, "text/json")
                .addCookie(new Cookie("expenses_session_id", "1234567812"))
                .run();

        Assert.assertEquals(response.getStatus(), Response.OK);
        ObjectMapper mapper = new ObjectMapper();
        TripResponse trip = mapper.readValue(response.getOutputAsString(), TripResponse.class);
        Assert.assertNotNull(trip);
        Assert.assertEquals(trip.getName(), "Prague");
    }

    @Test
    public void shouldListTrip() throws Exception {
        databaseOperation(DatabaseOperation.INSERT, dataset);

        MockResponse response = get("/trip")
                .setHeader(Http.Headers.ACCEPT, "text/json")
                .addCookie(new Cookie("expenses_session_id", "1234567812"))
                .run();

        Assert.assertEquals(response.getStatus(), Response.OK);
        ObjectMapper mapper = new ObjectMapper();
        List<TripResponse> trips = mapper.readValue(response.getOutputAsString(), new TypeReference<List<TripResponse>>(){});
        Assert.assertNotNull(trips);
    }

    @Test
    public void shouldEndTrip() throws Exception {
        databaseOperation(DatabaseOperation.INSERT, dataset);

        MockResponse response = put("/trip/1")
                .setHeader(Http.Headers.ACCEPT, "text/json")
                .addCookie(new Cookie("expenses_session_id", "1234567812"))
                .run();

        Assert.assertEquals(response.getStatus(), Response.OK);
        TripManager tripManager = getSpringContext().getBean("tripManager", TripManager.class);
        Trip trip = tripManager.load(1, 5);
        Assert.assertNotNull(trip);
        Assert.assertEquals(trip.getStatus(), Trip.STATUS_CLOSED);
    }
}
