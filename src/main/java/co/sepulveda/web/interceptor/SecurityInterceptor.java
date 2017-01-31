package co.sepulveda.web.interceptor;

import co.sepulveda.core.employee.Employee;
import co.sepulveda.core.session.Session;
import co.sepulveda.core.session.SessionManager;
import co.sepulveda.web.Secured;
import com.elibom.jogger.exception.ForbiddenException;
import com.elibom.jogger.exception.UnAuthorizedException;
import com.elibom.jogger.http.Cookie;
import com.elibom.jogger.http.Request;
import com.elibom.jogger.http.Response;
import com.elibom.jogger.middleware.router.interceptor.Interceptor;
import com.elibom.jogger.middleware.router.interceptor.InterceptorExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author Carlos Sepulveda
 */
public class SecurityInterceptor implements Interceptor {

    private final Logger log = LoggerFactory.getLogger(SecurityInterceptor.class);
    private SessionManager sessionManager;

    @Override
    public void intercept(Request request, Response response, InterceptorExecution execution) throws Exception {
        Session session = getSession(request);
        if (requiresAuthentication(execution) && session == null) throw new UnAuthorizedException();

        if (requiresAuthentication(execution)) {
            Employee employee = session.getEmployee();
            if (getRole(execution) != null && !getRole(execution).equals(employee.getRole())) throw new ForbiddenException();
        }

        if (session != null) response.setAttribute("session", session);
        execution.proceed();
    }

    private Session getSession(Request request) throws Exception {
        Cookie cookie = request.getCookie("expenses_session_id");
        if (cookie == null) return null;
        return sessionManager.loadByToken(cookie.getValue());
    }

    private boolean requiresAuthentication(InterceptorExecution execution) {
        boolean requiresAuth = execution.getController().getAnnotation(Secured.class) != null;
        if (!requiresAuth) {
            requiresAuth = execution.getAction().getAnnotation(Secured.class) != null;
        }

        return requiresAuth;
    }

    private String getRole(InterceptorExecution execution) {
        Secured actionAnnotation = execution.getAction().getAnnotation(Secured.class);
        if (actionAnnotation != null) {
            return actionAnnotation.role();
        }
        Secured controllerAnnotation = execution.getController().getAnnotation(Secured.class);
        if (controllerAnnotation != null) {
            return controllerAnnotation.role();
        }
        return null;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }
}
