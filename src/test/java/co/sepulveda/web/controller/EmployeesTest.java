package co.sepulveda.web.controller;

import co.sepulveda.core.employee.EmployeeManager;
import co.sepulveda.web.forms.EmployeeForm;
import com.elibom.jogger.http.Cookie;
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
public class EmployeesTest extends BaseTest {

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

        MockResponse response = post("/employee")
                .setHeader(Http.Headers.ACCEPT, "text/json")
                .setBodyAsString(json.toString())
                .run();

        Assert.assertEquals(response.getStatus(), 201);
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

        MockResponse response = post("/employee")
                .setHeader(Http.Headers.ACCEPT, "text/json")
                .setBodyAsString(json.toString())
                .run();

        Assert.assertEquals(response.getStatus(), Response.CONFLICT);
    }
}
