package foot_court.users.infrastructure.exceptionhandler;

import foot_court.users.domain.exception.InvalidCredentialsException;
import foot_court.users.domain.exception.InvalidTokenException;
import foot_court.users.domain.exception.MultipleUserValidationExceptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor {
    private static final String MESSAGE = "errors";

    @ExceptionHandler(MultipleUserValidationExceptions.class)
    public ResponseEntity<Map<String, List<String>>> handleMultipleUserValidationExceptions(
            MultipleUserValidationExceptions multipleUserValidationExceptions) {
        Map<String, List<String>> response = new HashMap<>();
        response.put(MESSAGE, multipleUserValidationExceptions.getErrors());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<String> handleInvalidCredentials(InvalidCredentialsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<String> handleInvalidToken(InvalidTokenException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }
}
