package owuor91.io.transactions.exceptions;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
  @ExceptionHandler(ConstraintViolationException.class)
  public final ResponseEntity<ErrorResponse> handleConstraintViolation(
      ConstraintViolationException ex, WebRequest request) {
    List<String> details =
        ex.getConstraintViolations().parallelStream().map(e -> e.getMessage()).collect(
            Collectors.toList());
    ErrorResponse errorResponse = ErrorResponse.builder()
        .errors(true)
        .message("Invalid request")
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .details(details)
        .build();
    return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ValidationException.class)
  public final ResponseEntity<ErrorResponse> handleExceptions(Exception e) {
    ErrorResponse errorResponse = ErrorResponse.builder()
        .errors(true)
        .message(e.getMessage())
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .build();
    return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
  }
}
