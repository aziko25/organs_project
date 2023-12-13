package organs.organs.Configurations.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class AccessDeniedExceptionHandler {

    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<Object> exception(AccessDeniedException exception) {

        return new ResponseEntity<>(exception.getMessage(), HttpStatus.FORBIDDEN);
    }
}