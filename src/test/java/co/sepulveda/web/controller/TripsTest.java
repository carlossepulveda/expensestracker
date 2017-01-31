package co.sepulveda.web.controller;

import com.elibom.jogger.http.Http;
import com.elibom.jogger.http.Response;
import com.elibom.jogger.test.MockResponse;
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
    public void shouldCreateATrip() throws Exception {
        databaseOperation(DatabaseOperation.INSERT, dataset);
        JSONObject json = new JSONObject();
        json.put("name","carlos S");
        json.put("employeePersonalId","123123123");

        MockResponse response = post("/trip")
                .setHeader(Http.Headers.ACCEPT, "text/json")
                .setBodyAsString(json.toString())
                .run();

        Assert.assertEquals(response.getStatus(), 201);
    }

    @Test
    public void shouldNotCreateATripIfUserDontExist() throws Exception {
        databaseOperation(DatabaseOperation.INSERT, dataset);
        JSONObject json = new JSONObject();
        json.put("name","carlos S");
        json.put("employeePersonalId","12");

        MockResponse response = post("/trip")
                .setHeader(Http.Headers.ACCEPT, "text/json")
                .setBodyAsString(json.toString())
                .run();

        Assert.assertEquals(response.getStatus(), Response.NOT_FOUND);
    }

}
