package organs.organs.Configurations.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RequestParamExceptionHandler {

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<String> handleMissingParams(MissingServletRequestParameterException ex) {

        String name = ex.getParameterName();
        String type = ex.getParameterType();

        String msg = String.format("Required %s parameter '%s' is not present", type, name);

        return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
    }
}