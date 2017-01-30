package co.sepulveda.web.middleware;

import com.elibom.jogger.Middleware;
import com.elibom.jogger.MiddlewareChain;
import com.elibom.jogger.http.Request;
import com.elibom.jogger.http.Response;

/**
 *
 * @author Carlos Sepulveda <ca.sepulveda.sanchez@gmail.com>
 */
public class ExceptionMiddleware implements Middleware {

    @Override
    public void handle(Request rqst, Response rspns, MiddlewareChain mc) throws Exception {
        try {
            mc.next();
        } catch (Exception e) {
            rspns.status(500).write("UnExpected Error");
        }
    }

}
