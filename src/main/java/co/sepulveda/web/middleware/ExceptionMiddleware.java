package co.sepulveda.web.middleware;

import com.elibom.jogger.Middleware;
import com.elibom.jogger.MiddlewareChain;
import com.elibom.jogger.exception.BadRequestException;
import com.elibom.jogger.exception.ConflictException;
import com.elibom.jogger.exception.UnAuthorizedException;
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
            processException(rspns, e);
        }
    }

    private void processException(Response response, Exception e) {
        if(e instanceof UnAuthorizedException) {
            response.status(401).write("{\"code\":\"unauthorized\"}");
            return;
        }else if(e instanceof BadRequestException) {
            response.status(400).write("{\"code\":\"invalid_data\"}");
        }else if(e instanceof ConflictException) {
            response.status(409).write("{\"code\":\"duplicated_entry\"}");
        } else {
            response.status(500).write("{\"code\":\"error\"}");
        }
    }
}
