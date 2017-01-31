package co.sepulveda.web.forms;

import com.elibom.jogger.exception.BadRequestException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

/**
 *
 * @author Carlos Sepulveda
 */
public class FormParser {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static <T> T parse(String body, Class c) {
        try {
            Object jsonObject = mapper.readValue(body, c);
            Form form = (Form) jsonObject;
            if (!form.validate()) throw new BadRequestException(); 
            return (T) jsonObject;
        } catch (IOException e) {
            throw new BadRequestException();
        } catch (BadRequestException e) {
            throw new BadRequestException();
        }
    }
}
