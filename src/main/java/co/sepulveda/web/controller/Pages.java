package co.sepulveda.web.controller;

import co.sepulveda.core.employee.Employee;
import co.sepulveda.core.employee.EmployeeManager;
import co.sepulveda.core.session.Session;
import co.sepulveda.core.session.SessionManager;
import co.sepulveda.web.forms.FormParser;
import co.sepulveda.web.forms.LoginForm;
import com.elibom.jogger.exception.UnAuthorizedException;
import com.elibom.jogger.http.Cookie;
import com.elibom.jogger.http.Http.ContentType;
import com.elibom.jogger.http.Request;
import com.elibom.jogger.http.Response;
import java.util.UUID;

/**
 *
 * @author Carlos Sepulveda <ca.sepulveda.sanchez@gmail.com>
 */
public class Pages {

    private EmployeeManager employeeManager;
    private SessionManager sessionManager;

    public void index(Request request, Response response) {
        response.contentType(ContentType.TEXT_HTML).render("index.ftl");
    }

    public void login(Request request, Response response) throws Exception {
        String body = request.getBody().asString();
        LoginForm form = FormParser.parse(body, LoginForm.class);
        Employee employee = employeeManager.loadByPersonalId(form.getUsername());
        if (employee == null) throw new UnAuthorizedException();
        if (!employee.getPassword().equals(form.getPassword())) throw new UnAuthorizedException();

        Session session = new Session();
        session.setEmployee(employee);
        session.setToken(UUID.randomUUID().toString());
        sessionManager.create(session);

        Cookie cookie = new Cookie("expenses_session_id",  session.getToken(), Integer.MAX_VALUE, "/");
        response.setCookie(cookie);

        response.contentType(ContentType.APPLICATION_JSON).write("{}");
    }

   public void logout(Request request, Response response) throws Exception {
        Session session = (Session) response.getAttributes().get("session");
        if (session != null) {
            sessionManager.deleteByToken(session.getToken());
            response.removeCookie(new Cookie("expenses_session_id",session.getToken(), Integer.MAX_VALUE, "/"));
        }

        response.redirect("/");
    }

    public void home(Request request, Response response) throws Exception {
        Session session = (Session) response.getAttributes().get("session");
        if (Employee.ROLE_EMPLOYEE.equals(session.getEmployee().getRole())) {
            response.redirect("/trip");
        } else {
            response.redirect("/admin/trip");
        }
    }

    public void setEmployeeManager(EmployeeManager employeeManager) {
        this.employeeManager = employeeManager;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }
}
