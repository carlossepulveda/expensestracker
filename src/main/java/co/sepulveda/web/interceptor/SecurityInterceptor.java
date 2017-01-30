package co.sepulveda.web.interceptor;

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

    @Override
    public void intercept(Request request, Response response, InterceptorExecution execution) throws Exception {
        execution.proceed();
    }
}
