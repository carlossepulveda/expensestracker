package co.sepulveda.web.controller;

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
public class LoginTest extends BaseTest {

    private static String dataset = "dbunit/employees-common.xml";

    @Test
    public void shouldLogin() throws Exception{
        databaseOperation(DatabaseOperation.INSERT, dataset);
        JSONObject json = new JSONObject();
        json.put("username","123123123");
        json.put("password","qwas");

        MockResponse response = post("/login")
                .setHeader(Http.Headers.ACCEPT, "text/json")
                .setBodyAsString(json.toString())
                .run();

        Assert.assertEquals(response.getStatus(), Response.OK);
        Cookie cookie = response.getAddedCookies().get("expenses_session_id");
        Assert.assertNotNull(cookie);
    }

    @Test
    public void shouldLogout() throws Exception{
        databaseOperation(DatabaseOperation.INSERT, dataset);

        MockResponse response = get("/logout")
                .setHeader(Http.Headers.ACCEPT, "text/json")
                .addCookie(new Cookie("expenses_session_id", "1234567890"))
                .run();

        Assert.assertEquals(response.getStatus(), Response.FOUND);
        Cookie cookie = response.getAddedCookies().get("expenses_session_id");
        Assert.assertNull(cookie);
    }
}
