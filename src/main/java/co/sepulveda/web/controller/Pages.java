package co.sepulveda.web.controller;

import com.elibom.jogger.http.Http.ContentType;
import com.elibom.jogger.http.Request;
import com.elibom.jogger.http.Response;

/**
 *
 * @author Carlos Sepulveda <ca.sepulveda.sanchez@gmail.com>
 */
public class Pages {

    public void index(Request request, Response response) {
        response.contentType(ContentType.TEXT_HTML).write("ok");
    }
}
