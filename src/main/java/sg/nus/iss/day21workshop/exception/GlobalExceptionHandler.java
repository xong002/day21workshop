package sg.nus.iss.day21workshop.exception;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleResourceNotFoundException(
            ResourceNotFoundException rnfe,
            WebRequest request) {

        ErrorMessage errMsg = new ErrorMessage(HttpStatus.NOT_FOUND.value(), new Date(), rnfe.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<ErrorMessage>(errMsg, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleException(
            Exception e,
            WebRequest request) {

        ErrorMessage errMsg = new ErrorMessage(HttpStatus.NOT_FOUND.value(), new Date(),e.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<ErrorMessage>(errMsg, HttpStatus.NOT_FOUND);
    }
}
